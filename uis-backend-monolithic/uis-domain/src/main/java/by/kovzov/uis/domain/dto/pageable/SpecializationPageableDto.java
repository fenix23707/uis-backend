package by.kovzov.uis.domain.dto.pageable;

import org.springframework.data.domain.Page;

import by.kovzov.uis.domain.entity.Specialization;

public class SpecializationPageableDto extends PageableDto<Specialization> {

    public SpecializationPageableDto(Page<Specialization> specializations) {
        super(specializations);
    }
}
