package by.kovzov.uis.specialization.dto;

import java.util.List;
import java.util.stream.Stream;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class DisciplineDto {

    @Nullable
    Long id;

    @NotBlank(message = "Name can not be blank")
    String name;

    @NotBlank(message = "Short name can not be blank")
    String shortName;

    //TODO update to real categories
    @Nullable
    List<Long> categories = Stream.generate(() -> Math.random() % 20)
        .limit((long) (Math.random() % 5))
        .map(Double::longValue)
        .toList();
}
