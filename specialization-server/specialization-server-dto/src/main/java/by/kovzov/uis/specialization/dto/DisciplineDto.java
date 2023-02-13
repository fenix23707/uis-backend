package by.kovzov.uis.specialization.dto;

import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class DisciplineDto {

    Long id;
    String name;
    String shortName;
    //TODO update to real categories
    List<Long> categories;
}
