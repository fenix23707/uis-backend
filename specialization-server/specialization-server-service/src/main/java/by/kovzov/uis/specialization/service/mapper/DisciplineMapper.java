package by.kovzov.uis.specialization.service.mapper;

import org.mapstruct.Mapper;

import by.kovzov.uis.specialization.dto.DisciplineDto;
import by.kovzov.uis.specialization.repository.entity.Discipline;

@Mapper(componentModel = "spring")
public interface DisciplineMapper {

    DisciplineDto toDto(Discipline entity);

    Discipline toEntity(DisciplineDto disciplineDto);
}
