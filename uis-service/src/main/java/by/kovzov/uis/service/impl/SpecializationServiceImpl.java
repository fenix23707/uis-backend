package by.kovzov.uis.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import by.kovzov.uis.domain.dto.pageable.SpecializationPageableDto;
import by.kovzov.uis.repository.api.SpecializationRepository;
import by.kovzov.uis.service.api.SpecializationService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository specializationRepository;

    @Override
    public SpecializationPageableDto getAllParents(Pageable pageable) {
        return new SpecializationPageableDto(specializationRepository.findAllParents(pageable));
    }
}
