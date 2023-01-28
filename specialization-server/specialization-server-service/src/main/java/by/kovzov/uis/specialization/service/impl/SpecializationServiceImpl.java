package by.kovzov.uis.specialization.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

import by.kovzov.uis.specialization.dto.SpecializationParentDto;
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
    public Page<SpecializationParentDto> getAllParents(Pageable pageable) {
        Function<Specialization, SpecializationParentDto> mapToParentDto = entity -> {
            return specializationMapper.toParentDto(entity).toBuilder()
                .hasChildren(!entity.getChildren().isEmpty())
                .build();
        };

        Page<Long> parentIds = specializationRepository.findAllParentIds(pageableWithoutSort(pageable));
        List<Specialization> content = specializationRepository.findAllWithChildrenByIds(parentIds.toSet(), pageable.getSort());
        return PageableExecutionUtils.getPage(content, pageable, () -> parentIds.getTotalElements())
            .map(mapToParentDto);
    }

    private Pageable pageableWithoutSort(Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.unsorted());
    }
}
