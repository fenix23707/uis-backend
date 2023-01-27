package by.kovzov.uis.specialization.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import by.kovzov.uis.specialization.domain.dto.SpecializationParentDto;

public interface SpecializationService {

    Page<SpecializationParentDto> getAllParents(Pageable pageable);
}
