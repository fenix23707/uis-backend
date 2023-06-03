package by.kovzov.uis.academic.repository.specification;

import java.time.LocalDate;

import by.kovzov.uis.academic.repository.entity.Curriculum;
import by.kovzov.uis.academic.repository.entity.Curriculum_;
import by.kovzov.uis.academic.repository.entity.Discipline;
import by.kovzov.uis.academic.repository.entity.Discipline_;
import org.springframework.data.jpa.domain.Specification;

public class CurriculumSpecifications {

    public static Specification<Curriculum> admissionYearGte(Integer admissionYear) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(Curriculum_.ADMISSION_YEAR), admissionYear);
    }

    public static Specification<Curriculum> admissionYearLt(Integer admissionYear) {
        return (root, query, builder) -> builder.lessThan(root.get(Curriculum_.ADMISSION_YEAR), admissionYear);
    }

    public static Specification<Curriculum> approvalDateGte(LocalDate approvalDate) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(Curriculum_.APPROVAL_DATE), approvalDate);
    }

    public static Specification<Curriculum> approvalDateLt(LocalDate approvalDate) {
        return (root, query, builder) -> builder.lessThan(root.get(Curriculum_.APPROVAL_DATE), approvalDate);
    }
}
