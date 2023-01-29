package by.kovzov.uis.specialization.service.impl;

import static by.kovzov.uis.specialization.repository.specification.SpecializationSpecifications.hasCipherLike;
import static by.kovzov.uis.specialization.repository.specification.SpecializationSpecifications.hasNameLike;
import static by.kovzov.uis.specialization.repository.specification.SpecializationSpecifications.hasShortNameLike;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import by.kovzov.uis.specialization.dto.SpecializationDto;
import by.kovzov.uis.specialization.repository.api.SpecializationRepository;
import by.kovzov.uis.specialization.repository.entity.Specialization;
import by.kovzov.uis.specialization.repository.specification.SpecializationSpecifications;
import by.kovzov.uis.specialization.service.api.SpecializationService;
import by.kovzov.uis.specialization.service.mapper.SpecializationMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository specializationRepository;
    private final SpecializationMapper specializationMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<SpecializationDto> getAllParents(Pageable pageable) {
        Page<Long> parentIds = specializationRepository.findAllParentIds(pageableWithoutSort(pageable));
        List<Specialization> content = specializationRepository.findAllByIds(parentIds.toSet(), pageable.getSort());
        return PageableExecutionUtils.getPage(content, pageable, parentIds::getTotalElements)
            .map(this::mapToParentDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpecializationDto> getAllChildrenByParentId(Long parentId, Sort sort) {
        // TODO add check if parent with id exists
        return specializationRepository.findAllChildrenByParentId(parentId, sort).stream()
            .map(this::mapToParentDto)
            .collect(Collectors.toList());
    }

    @Override
    public Page<SpecializationDto> search(String query, Pageable pageable) {
        Specification<Specialization> specification = hasNameLike(query)
            .or(hasShortNameLike(query))
            .or(hasCipherLike(query));
        //TODO: use entity graph or projection
        Page<Long> ids = specializationRepository.findAll(specification, pageableWithoutSort(pageable))
            .map(Specialization::getId);
        List<Specialization> content = specializationRepository.findAllByIds(ids.toSet(), pageable.getSort());
        return PageableExecutionUtils.getPage(content, pageable, ids::getTotalElements)
            .map(this::mapToParentDto);
    }

    private SpecializationDto mapToParentDto(Specialization entity) {
        return specializationMapper.toParentDto(entity).toBuilder()
            .hasChildren(!entity.getChildren().isEmpty())
            .build();
    }

    private Pageable pageableWithoutSort(Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.unsorted());
    }
}
