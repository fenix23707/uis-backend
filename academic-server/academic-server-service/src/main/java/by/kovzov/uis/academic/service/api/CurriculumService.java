package by.kovzov.uis.academic.service.api;

import by.kovzov.uis.academic.dto.CurriculumDto;
import by.kovzov.uis.academic.dto.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CurriculumService {

    Page<CurriculumDto> getAll(Pageable pageable);

    CurriculumDto create(CurriculumDto curriculumDto);

    CurriculumDto update(Long id, CurriculumDto curriculumDto);

    Page<CurriculumDto> search(SearchDto searchDto, Pageable pageable);

    CurriculumDto getById(Long id);
}
