package by.kovzov.uis.security.domain.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper=false)
@SuperBuilder
@JsonInclude(Include.NON_NULL)
public class ValidationErrorMessageDto extends ApiErrorMessageDto {

    private List<String> validationMessages;
}
