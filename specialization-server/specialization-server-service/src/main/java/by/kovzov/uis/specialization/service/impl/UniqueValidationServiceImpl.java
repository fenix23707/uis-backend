package by.kovzov.uis.specialization.service.impl;

import static java.text.MessageFormat.format;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import by.kovzov.uis.common.exception.AlreadyExistsException;
import by.kovzov.uis.specialization.repository.annotation.Unique;
import by.kovzov.uis.specialization.service.api.UniqueValidationService;
import jakarta.persistence.Id;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
            .map(field -> format("{0} = \"{1}\"",field.getName(), getFieldValue(field, entity)))
            .collect(Collectors.joining(", and ", prefix, suffix));
    }

    private Set<Field> getFieldsWithAnnotation(Object value, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(value.getClass().getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(annotationClass))
            .collect(Collectors.toSet());
    }

    private <T> Specification<T> constructQuery(Object value) {
        return (root, query, builder) ->
            builder.or(generatePredicates(value, root, builder).toArray(Predicate[]::new));
    }

    private List<Predicate> generatePredicates(Object value, Root<?> root, CriteriaBuilder builder) {
        Function<Field, Predicate> fieldToPredicate =
            field -> builder.equal(root.get(field.getName()), getFieldValue(field, value));

        List<Predicate> predicates = getFieldsWithAnnotation(value, Unique.class).stream()
            .map(fieldToPredicate)
            .collect(Collectors.toCollection(LinkedList::new));
        getIdPredicate(value, root, builder).ifPresent(predicates::add);
        return predicates;

    }

    private Optional<Predicate> getIdPredicate(Object value, Root<?> root, CriteriaBuilder builder) {
        return getFieldsWithAnnotation(value, Id.class).stream()
            .filter(field -> Objects.nonNull(getFieldValue(field, value)))
            .findAny()
            .map(field -> builder.notEqual(root.get(field.getName()), getFieldValue(field, value)));
    }

    @SneakyThrows
    private Object getFieldValue(Field field, Object o) {
        field.setAccessible(true);
        return field.get(o);
    }
}
