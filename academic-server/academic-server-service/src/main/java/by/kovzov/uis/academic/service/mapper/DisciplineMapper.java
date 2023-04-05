package by.kovzov.uis.academic.service.mapper;

import by.kovzov.uis.academic.dto.DisciplineDto;
import by.kovzov.uis.academic.repository.entity.Discipline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface DisciplineMapper {

    DisciplineDto toDto(Discipline entity);

    @Mapping(target = "tags", ignore = true)
    Discipline toEntity(DisciplineDto disciplineDto);
}
