package by.kovzov.uis.service.api;

import org.springframework.data.domain.Pageable;

import by.kovzov.uis.domain.dto.pageable.SpecializationPageableDto;

public interface SpecializationService {

    SpecializationPageableDto getAllParents(Pageable pageable);
}
