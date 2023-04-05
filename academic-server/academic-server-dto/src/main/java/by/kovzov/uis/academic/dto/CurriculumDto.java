package by.kovzov.uis.academic.dto;

import java.time.LocalDate;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class CurriculumDto {

    @Nullable
    Long id;

    @NotNull LocalDate approvalDate;

    @NotNull Integer admissionYear;

    @NotNull Long specializationId;
}
