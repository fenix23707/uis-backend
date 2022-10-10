package by.kovzov.uis.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.context.annotation.Bean;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiErrorResponse {

    private String reason;
    private String status;
    private String description;
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();
}
