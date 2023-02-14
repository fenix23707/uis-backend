package by.kovzov.uis.specialization.dto;

import java.util.List;
import java.util.stream.Stream;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class DisciplineDto {

    Long id;
    String name;
    String shortName;
    //TODO update to real categories
    List<Long> categories = Stream.generate(() -> Math.random() % 20)
        .limit((long) (Math.random() % 5))
        .map(Double::longValue)
        .toList();
}
