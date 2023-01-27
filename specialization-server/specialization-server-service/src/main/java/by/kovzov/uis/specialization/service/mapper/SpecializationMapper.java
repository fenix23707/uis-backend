package by.kovzov.uis.specialization.service.mapper;

import org.mapstruct.Mapper;

import by.kovzov.uis.specialization.domain.dto.SpecializationParentDto;
import by.kovzov.uis.specialization.repository.entity.Specialization;

@Mapper(componentModel = "spring")
public interface SpecializationMapper {

    SpecializationParentDto toParentDto(Specialization specialization);
}
