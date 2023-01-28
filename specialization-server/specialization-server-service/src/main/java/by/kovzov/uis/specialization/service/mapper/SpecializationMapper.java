package by.kovzov.uis.specialization.service.mapper;

import org.mapstruct.Mapper;

import by.kovzov.uis.specialization.dto.SpecializationDto;
import by.kovzov.uis.specialization.repository.entity.Specialization;

@Mapper(componentModel = "spring")
public interface SpecializationMapper {

    SpecializationDto toParentDto(Specialization specialization);
}
