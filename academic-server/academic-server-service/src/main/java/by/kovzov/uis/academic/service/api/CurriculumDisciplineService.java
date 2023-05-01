package by.kovzov.uis.academic.service.api;

import java.util.List;

import by.kovzov.uis.academic.dto.CurriculumDisciplineDto;

public interface CurriculumDisciplineService {

    List<CurriculumDisciplineDto> getAllByCurriculumId(Long curriculumId);
}
