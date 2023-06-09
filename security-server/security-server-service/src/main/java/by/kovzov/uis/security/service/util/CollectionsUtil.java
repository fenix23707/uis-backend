package by.kovzov.uis.security.service.util;

import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CollectionsUtil {

    public static <T> Collector<T, ?, Optional<T>> toSingleElement(String format, Object... args) {
        return Collectors.collectingAndThen(
            Collectors.toList(),
            list -> {
                if (list.size() > 1) {
                    throw new IllegalStateException(format.formatted(args));
                }

                return list.stream().findAny();
            }
        );
    }
}
