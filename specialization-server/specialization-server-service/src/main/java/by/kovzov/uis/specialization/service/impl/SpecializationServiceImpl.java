package by.kovzov.uis.specialization.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.kovzov.uis.specialization.domain.dto.SpecializationParentDto;
import by.kovzov.uis.specialization.repository.api.SpecializationRepository;
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
        return specializationRepository.findAllParents(pageable)
            .map(specializationMapper::toParentDto)
            .map(dto -> dto.toBuilder()
                .hasChildren(specializationRepository.countByParentId(dto.getId()) > 0)
                .build());
    }
}
