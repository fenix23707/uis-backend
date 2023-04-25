package by.kovzov.uis.security.dto;

import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class GroupedPermission {

    String scope;
    List<ActionDto> actions;
}
