package by.kovzov.uis.academic.service.api;

import java.util.List;

import by.kovzov.uis.academic.dto.CurriculumDisciplineDto;
import by.kovzov.uis.academic.repository.entity.CurriculumDiscipline.CurriculumDisciplineId;
import org.springframework.data.domain.Sort;

public interface CurriculumDisciplineService {

    List<CurriculumDisciplineDto> getAllByCurriculumId(Long curriculumId, Sort sort);

    CurriculumDisciplineDto create(CurriculumDisciplineDto dto);

    void delete(CurriculumDisciplineId id);
}
