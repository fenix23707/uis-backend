package by.kovzov.uis.specialization.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import by.kovzov.uis.specialization.dto.SpecializationDto;
import by.kovzov.uis.specialization.repository.api.SpecializationRepository;
import by.kovzov.uis.specialization.repository.entity.Specialization;
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
        return PageableExecutionUtils.getPage(content, pageable, () -> parentIds.getTotalElements())
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

    private SpecializationDto mapToParentDto(Specialization entity) {
        return specializationMapper.toParentDto(entity).toBuilder()
            .hasChildren(!entity.getChildren().isEmpty())
            .build();
    }

    private Pageable pageableWithoutSort(Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.unsorted());
    }
}
