package by.kovzov.uis.academic.service.impl;

import static java.text.MessageFormat.format;

import static by.kovzov.uis.academic.repository.specification.SpecializationSpecifications.cipherLike;
import static by.kovzov.uis.academic.repository.specification.SpecializationSpecifications.mameLike;
import static by.kovzov.uis.academic.repository.specification.SpecializationSpecifications.shortNameLike;
import static by.kovzov.uis.academic.service.util.PageableUtils.pageableWithoutSort;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import by.kovzov.uis.academic.dto.SpecializationDto;
import by.kovzov.uis.academic.dto.SpecializationRequestDto;
import by.kovzov.uis.academic.repository.api.SpecializationRepository;
import by.kovzov.uis.academic.repository.entity.Specialization;
import by.kovzov.uis.academic.service.api.SpecializationService;
import by.kovzov.uis.academic.service.mapper.SpecializationMapper;
import by.kovzov.uis.common.exception.DependencyException;
import by.kovzov.uis.common.exception.NotFoundException;
import by.kovzov.uis.common.exception.TreeStructureException;
import by.kovzov.uis.common.validator.unique.UniqueValidationService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {

    private static final String NOT_FOUND_MESSAGE = "Specialization with id = %s not found.";

    private final SpecializationRepository specializationRepository;
    private final SpecializationMapper specializationMapper;

    private final UniqueValidationService uniqueValidationService;

    @Override
    @Transactional(readOnly = true)
    public Page<SpecializationDto> getAllParents(Pageable pageable) {
        Page<Long> parentIds = specializationRepository.findAllParentIds(pageableWithoutSort(pageable));
        List<Specialization> content = specializationRepository.findAllByIds(parentIds.toSet(), pageable.getSort());
        return PageableExecutionUtils.getPage(content, pageable, parentIds::getTotalElements)
            .map(this::mapToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpecializationDto> getAllChildrenByParentId(Long parentId, Sort sort) {
        if (!specializationRepository.existsById(parentId)) {
            throw new NotFoundException(format(NOT_FOUND_MESSAGE, parentId));
        }
        return specializationRepository.findAllChildrenByParentId(parentId, sort).stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SpecializationDto> search(String query, Pageable pageable) {
        Specification<Specialization> specification = mameLike(query)
            .or(shortNameLike(query))
            .or(cipherLike(query));
        //TODO: use entity graph or projection
        Page<Long> ids = specializationRepository.findAll(specification, pageableWithoutSort(pageable))
            .map(Specialization::getId);
        List<Specialization> content = specializationRepository.findAllByIds(ids.toSet(), pageable.getSort());
        return PageableExecutionUtils.getPage(content, pageable, ids::getTotalElements)
            .map(this::mapToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public SpecializationDto getById(Long id) {
        return specializationRepository.findWithChildrenById(id)
            .map(this::mapToDto)
            .orElseThrow(() -> new NotFoundException(format(NOT_FOUND_MESSAGE, id)));
    }

    @Override
    @Transactional
    public SpecializationDto create(SpecializationRequestDto requestDto) {
        Specialization entity = specializationMapper.toEntity(requestDto);
        entity.setId(null);

        uniqueValidationService.checkEntity(entity, specializationRepository);

        updateParent(entity, requestDto.getParentId());
        specializationRepository.save(entity);

        return specializationMapper.toDto(entity).toBuilder()
            .hasChildren(false)
            .build();
    }

    @Override
    public SpecializationDto update(Long id, SpecializationRequestDto requestDto) {
        SpecializationDto oldDto = getById(id);

        Specialization updatedEntity = specializationMapper.toEntity(requestDto);
        updatedEntity.setId(id);

        uniqueValidationService.checkEntity(updatedEntity, specializationRepository);
        verifyTreeNotContainsNode(id, requestDto.getParentId());

        updateParent(updatedEntity, requestDto.getParentId());

        specializationRepository.save(updatedEntity);

        return specializationMapper.toDto(updatedEntity).toBuilder()
            .hasChildren(oldDto.isHasChildren())
            .build();
    }

    @Override
    public void deleteById(Long id) {
        var dto = getById(id);
        if (dto.isHasChildren()) {
            throw new DependencyException("Specialization with id = %s has children and con not be deleted.".formatted(id));
        }
        specializationRepository.deleteById(id);
    }

    private Specialization findById(Long id) {
        return specializationRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(format(NOT_FOUND_MESSAGE, id)));
    }

    private void updateParent(Specialization entity, Long parentId) {
        if (Objects.nonNull(parentId)) {
            Specialization parent = findById(parentId);
            entity.setParent(parent);
        }
    }

    private SpecializationDto mapToDto(Specialization entity) {
        return specializationMapper.toDto(entity).toBuilder()
            .hasChildren(!entity.getChildren().isEmpty())
            .build();
    }

    private void verifyTreeNotContainsNode(Long rootId, Long nodeId) {
        if (Objects.isNull(rootId) || Objects.isNull(nodeId)) {
            return;
        }
        var parent = findById(rootId);
        var node = findById(nodeId);
        if (isTreeHasNode(parent, node)) {
            throw new TreeStructureException("Tree with parent id = %d already has node with id = %d".formatted(rootId, nodeId));
        }
    }

    private boolean isTreeHasNode(Specialization root, Specialization node) {
        if (root == null) {
            return false;
        }

        if (EqualsBuilder.reflectionEquals(root, node, "id", "children", "parent")) {
            return true;
        }
        var children = specializationRepository.findWithChildrenById(root.getId())
            .map(Specialization::getChildren)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE.formatted(root.getId())));

        for (Specialization child : children) {
            if (isTreeHasNode(child, node)) {
                return true;
            }
        }

        return false;
    }

}
