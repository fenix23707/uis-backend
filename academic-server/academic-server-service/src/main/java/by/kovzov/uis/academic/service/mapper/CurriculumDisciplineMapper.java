package by.kovzov.uis.academic.service.mapper;

import java.util.List;

import by.kovzov.uis.academic.dto.CurriculumDisciplineDto;
import by.kovzov.uis.academic.repository.entity.CurriculumDiscipline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CurriculumDisciplineMapper {

    @Mapping(target = "curriculumId", source = "id.curriculumId")
    @Mapping(target = "disciplineId", source = "id.disciplineId")
    CurriculumDisciplineDto toDto(CurriculumDiscipline entity);

    @Mapping(target = "curriculumId", source = "id.curriculumId")
    @Mapping(target = "disciplineId", source = "id.disciplineId")
    List<CurriculumDisciplineDto> toDtos(List<CurriculumDiscipline> entities);

    @Mapping(target = "id.curriculumId", ignore = true)
    @Mapping(target = "id.disciplineId", ignore = true)
    CurriculumDiscipline toEntity(CurriculumDisciplineDto dto);
}
