package by.kovzov.uis.academic.validation;

import by.kovzov.uis.academic.dto.CurriculumDisciplineDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TotalHoursValidator implements ConstraintValidator<TotalHoursCheck, CurriculumDisciplineDto> {

    @Override
    public boolean isValid(CurriculumDisciplineDto dto, ConstraintValidatorContext context) {
        int totalHours = dto.getTotalHours();

        int lectureHours = dto.getLectureHours();
        int practiceHours = dto.getPracticeHours();
        int labHours = dto.getLabHours();
        int selfStudyHours = dto.getSelfStudyHours();

        return totalHours >= (lectureHours + practiceHours + labHours + selfStudyHours);
    }
}
