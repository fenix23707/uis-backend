package by.kovzov.uis.academic.service.mapper;

import org.mapstruct.Mapper;

import by.kovzov.uis.academic.dto.SpecializationDto;
import by.kovzov.uis.academic.dto.SpecializationRequestDto;
import by.kovzov.uis.academic.repository.entity.Specialization;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SpecializationMapper {

    @Mappings({
        @Mapping(source = "parent.id", target = "parentId"),
    })
    SpecializationDto toDto(Specialization specialization);

    Specialization toEntity(SpecializationRequestDto specializationRequestDto);
}
