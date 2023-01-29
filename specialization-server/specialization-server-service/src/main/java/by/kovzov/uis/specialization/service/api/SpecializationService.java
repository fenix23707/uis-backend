package by.kovzov.uis.specialization.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import by.kovzov.uis.specialization.dto.SpecializationDto;

public interface SpecializationService {

    Page<SpecializationDto> getAllParents(Pageable pageable);

    List<SpecializationDto> getAllChildrenByParentId(Long id, Sort sort);

    /**
     * Case-insensitive search by all available fields using or operator
     * @param query any string
     * @return List of Specializations which has at least one field that contains query
     */
    Page<SpecializationDto> search(String query, Pageable pageable);
}
