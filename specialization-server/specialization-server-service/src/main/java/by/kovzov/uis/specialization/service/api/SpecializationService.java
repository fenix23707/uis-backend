package by.kovzov.uis.specialization.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import by.kovzov.uis.specialization.dto.SpecializationDto;

public interface SpecializationService {

    Page<SpecializationDto> getAllParents(Pageable pageable);

    List<SpecializationDto> getAllChildrenByParentId(Long id, Sort sort);
}
