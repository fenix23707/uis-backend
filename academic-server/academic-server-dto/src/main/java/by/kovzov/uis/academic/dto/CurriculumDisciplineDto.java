package by.kovzov.uis.academic.dto;

import by.kovzov.uis.academic.validation.TotalHoursCheck;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@TotalHoursCheck
public class CurriculumDisciplineDto {

    Long curriculumId;

    Long disciplineId;

    @Min(value = 1, message = "Semester can not be less than 1")
    @Max(value = 8, message = "Semester can not be more than 8")
    int semester;

    @NotNull(message = "Total hours can not be null")
    Integer totalHours;

    @Min(value = 0, message = "Lecture hours can not be negative")
    int lectureHours;

    @Min(value = 0, message = "Practice hours can not be negative")
    int practiceHours;

    @Min(value = 0, message = "Lab hours can not be negative")
    int labHours;

    @Min(value = 0, message = "Self study hours can not be negative")
    int selfStudyHours;

    @Min(value = 0, message = "Test count can not be negative")
    int testCount;

    @NotNull(message = "Has credit can not be null")
    Boolean hasCredit;

    @NotNull(message = "Has exam can not be null")
    Boolean hasExam;

    @NotNull(message = "Credit units can not be null")
    @Positive(message = "Credit units must be positive")
    Double creditUnits;
}
