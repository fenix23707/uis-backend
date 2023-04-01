package by.kovzov.uis.academic.service.mapper;

import org.mapstruct.Mapper;

import by.kovzov.uis.academic.dto.DisciplineDto;
import by.kovzov.uis.academic.repository.entity.Discipline;

@Mapper(componentModel = "spring")
public interface DisciplineMapper {

    DisciplineDto toDto(Discipline entity);

    Discipline toEntity(DisciplineDto disciplineDto);
}
