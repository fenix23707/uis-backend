package by.kovzov.uis.specialization.service.impl;

import static java.text.MessageFormat.format;

import static by.kovzov.uis.specialization.repository.specification.SpecializationSpecifications.cipherEquals;
import static by.kovzov.uis.specialization.repository.specification.SpecializationSpecifications.cipherLike;
import static by.kovzov.uis.specialization.repository.specification.SpecializationSpecifications.mameLike;
import static by.kovzov.uis.specialization.repository.specification.SpecializationSpecifications.nameEquals;
import static by.kovzov.uis.specialization.repository.specification.SpecializationSpecifications.shortNameEquals;
import static by.kovzov.uis.specialization.repository.specification.SpecializationSpecifications.shortNameLike;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import by.kovzov.uis.common.exception.AlreadyExistsException;
import by.kovzov.uis.common.exception.NotFoundException;
import by.kovzov.uis.specialization.dto.SpecializationDto;
import by.kovzov.uis.specialization.dto.SpecializationRequestDto;
import by.kovzov.uis.specialization.repository.api.SpecializationRepository;
import by.kovzov.uis.specialization.repository.entity.Specialization;
import by.kovzov.uis.specialization.service.api.SpecializationService;
import by.kovzov.uis.specialization.service.mapper.SpecializationMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {

    private static final String NOT_FOUND_MESSAGE = "Specialization with id = {0} not found.";

    private final SpecializationRepository specializationRepository;
    private final SpecializationMapper specializationMapper;

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
        checkUniqueFields(entity);
        if (Objects.nonNull(requestDto.getParentId())) {
            Specialization parent = specializationRepository.findById(requestDto.getParentId())
                .orElseThrow(() -> new NotFoundException(format(NOT_FOUND_MESSAGE, requestDto.getParentId())));
            entity.setParent(parent);
        }
        specializationRepository.save(entity);
        return specializationMapper.toDto(entity).toBuilder()
            .hasChildren(false)
            .build();
    }

    private SpecializationDto mapToDto(Specialization entity) {
        return specializationMapper.toDto(entity).toBuilder()
            .hasChildren(!entity.getChildren().isEmpty())
            .build();
    }

    private void checkUniqueFields(Specialization specialization) {
        Specification<Specialization> specification = nameEquals(specialization.getName())
            .or(shortNameEquals(specialization.getShortName()))
            .or(cipherEquals(specialization.getCipher()));

        if (specializationRepository.findOne(specification).isPresent()) {
            throw new AlreadyExistsException(format("Specialization with name = {0} or short name = {1} or cipher = {2} already exists.",
                specialization.getName(), specialization.getShortName(), specialization.getCipher()));
        }
    }

    private Pageable pageableWithoutSort(Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.unsorted());
    }
}
