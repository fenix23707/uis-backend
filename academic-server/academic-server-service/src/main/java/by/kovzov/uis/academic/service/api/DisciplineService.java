package by.kovzov.uis.academic.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import by.kovzov.uis.academic.dto.DisciplineDto;

public interface DisciplineService {

    DisciplineDto getById(Long id);

    Page<DisciplineDto> search(String query, Pageable pageable);

    DisciplineDto create(DisciplineDto disciplineDto);

    DisciplineDto update(Long id, DisciplineDto disciplineDto);

    void verifyThatExistsById(Long id);

    void deleteById(Long id);
}
