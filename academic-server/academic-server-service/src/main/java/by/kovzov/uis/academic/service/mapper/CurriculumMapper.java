package by.kovzov.uis.academic.service.mapper;

import by.kovzov.uis.academic.dto.CurriculumDto;
import by.kovzov.uis.academic.repository.entity.Curriculum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface CurriculumMapper {

    @Mapping(target = "specializationId", source = "specialization.id")
    CurriculumDto toDto(Curriculum entity);

    @Mapping(target = "specialization.id", source = "specializationId")
    Curriculum toEntity(CurriculumDto dto);
}
