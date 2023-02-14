package by.kovzov.uis.specialization.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import by.kovzov.uis.specialization.dto.DisciplineDto;

public interface DisciplineService {

    DisciplineDto getById(Long id);

    Page<DisciplineDto> search(String query, Pageable pageable);
}
