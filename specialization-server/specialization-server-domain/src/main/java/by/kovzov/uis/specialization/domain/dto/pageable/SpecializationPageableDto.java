package by.kovzov.uis.specialization.domain.dto.pageable;

import org.springframework.data.domain.Page;

import by.kovzov.uis.specialization.domain.entity.Specialization;

public class SpecializationPageableDto extends PageableDto<Specialization> {

    public SpecializationPageableDto(Page<Specialization> specializations) {
        super(specializations);
    }
}
