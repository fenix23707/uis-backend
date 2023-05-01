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

    @NotNull(message = "Curriculum id can not be null")
    Long curriculumId;

    @NotNull(message = "Discipline id can not be null")
    Long disciplineId;

    @Min(value = 1, message = "Semester can not be less than 1")
    @Max(value = 8, message = "Semester can not be more than 8")
    int semester;

    @NotNull(message = "Total hours can not be null")
    @Positive(message = "Total hours must be positive number")
    Integer totalHours;

    @Positive(message = "Lecture hours must be positive")
    int lectureHours;

    @Positive(message = "Practice hours must be positive")
    int practiceHours;

    @Positive( message = "Lab hours must be positive")
    int labHours;

    @Positive(message = "Self study hours must be positive")
    int selfStudyHours;

    @Positive(message = "Test count hours must be positive")
    int testCount;

    @NotNull(message = "Has credit can not be null")
    Boolean hasCredit;

    @NotNull(message = "Has exam can not be null")
    Boolean hasExam;

    @NotNull(message = "Credit units can not be null")
    @Positive(message = "Credit units must be positive")
    Double creditUnits;
}
