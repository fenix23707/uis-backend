package by.kovzov.uis.specialization.service.api;

import org.springframework.data.domain.Pageable;

import by.kovzov.uis.specialization.domain.dto.pageable.SpecializationPageableDto;

public interface SpecializationService {

    SpecializationPageableDto getAllParents(Pageable pageable);
}
