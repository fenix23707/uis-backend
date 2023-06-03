package by.kovzov.uis.academic.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.AssertTrue;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CurriculumSearchDto {

    Integer admissionYearBegin;
    Integer admissionYearEnd;

    LocalDate approvalDateBegin;
    LocalDate approvalDateEnd;

    @AssertTrue(message = "admissionYearBegin must be less than admissionYearEnd")
    private boolean isAdmissionYearValid() {
        return admissionYearBegin == null || admissionYearEnd == null || admissionYearBegin <= admissionYearEnd;
    }

    @AssertTrue(message = "approvalDateBegin must be before approvalDateEnd")
    private boolean isApprovalDateValid() {
        return approvalDateBegin == null || approvalDateEnd == null || approvalDateBegin.isBefore(approvalDateEnd);
    }
}
