package by.kovzov.uis.academic.dto;

import ch.qos.logback.core.testUtil.RandomUtil;

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
    @Builder.Default
    List<Long> categories = Stream.generate(() -> RandomUtil.getPositiveInt() % 20)
        .limit(RandomUtil.getPositiveInt() % 5)
        .map(Integer::longValue)
        .toList();
}
