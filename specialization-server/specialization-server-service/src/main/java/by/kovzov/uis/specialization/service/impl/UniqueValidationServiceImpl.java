package by.kovzov.uis.specialization.service.impl;

import static java.text.MessageFormat.format;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import by.kovzov.uis.common.exception.AlreadyExistsException;
import by.kovzov.uis.specialization.repository.annotation.Unique;
import by.kovzov.uis.specialization.service.api.UniqueValidationService;
import jakarta.persistence.Id;
import lombok.SneakyThrows;

// TODO: add javadoc, unit tests
@Service
public class UniqueValidationServiceImpl implements UniqueValidationService {

    @Override
    public <T> void checkEntity(T entity, JpaSpecificationExecutor<T> jpaSpecificationExecutor) {
        jpaSpecificationExecutor.findOne(constructQuery(entity))
            .map(existedEntity -> generateMessage(entity, existedEntity))
            .ifPresent(msg -> {
                throw new AlreadyExistsException(msg);
            });
    }

    private <T> String generateMessage(T entity, T existedEntity) {
        String prefix = format("{0} with ", entity.getClass().getSimpleName());
        String suffix = " already exists.";
        return Arrays.stream(entity.getClass().getDeclaredFields())
            .filter(field -> Objects.equals(getFieldValue(field, entity), (getFieldValue(field, existedEntity))))
            .map(field -> format("{0} = '{1}'", field.getName(), getFieldValue(field, entity)))
            .collect(Collectors.joining(", and ", prefix, suffix));
    }

    private <T> Specification<T> constructQuery(T value) {
        return Specification.anyOf(generateSpecifications(value))
            .and(getIdSpecification(value).orElse(null));
    }

    private <T> Set<Specification<T>> generateSpecifications(T value) {
        return getFieldsWithAnnotation(value, Unique.class).stream()
            .map(field -> toEqualSpecification(field, value))
            .collect(Collectors.toSet());
    }

    private <T> Optional<Specification<T>> getIdSpecification(T value) {
        return getFieldsWithAnnotation(value, Id.class).stream()
            .filter(field -> Objects.nonNull(getFieldValue(field, value)))
            .findAny()
            .map(field -> toNotEqualSpecification(field, value));
    }

    private <T> Specification<T> toNotEqualSpecification(Field field, T value) {
        return (root, query, builder) -> builder.notEqual(root.get(field.getName()), getFieldValue(field, value));
    }

    private <T> Specification<T> toEqualSpecification(Field field, T value) {
        return (root, query, builder) -> builder.equal(root.get(field.getName()), getFieldValue(field, value));
    }

    private Set<Field> getFieldsWithAnnotation(Object value, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(value.getClass().getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(annotationClass))
            .collect(Collectors.toSet());
    }

    @SneakyThrows
    private Object getFieldValue(Field field, Object o) {
        field.setAccessible(true);
        return field.get(o);
    }
}
