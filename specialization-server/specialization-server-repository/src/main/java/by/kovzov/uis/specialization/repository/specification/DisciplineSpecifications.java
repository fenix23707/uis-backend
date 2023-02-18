package by.kovzov.uis.specialization.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import by.kovzov.uis.specialization.repository.entity.Discipline;
import by.kovzov.uis.specialization.repository.entity.Discipline_;

public class DisciplineSpecifications {

    public static Specification<Discipline> mameLike(String name) {
        String pattern = "%" + name.toLowerCase() + "%";
        return (root, query, builder) -> builder.like(builder.lower(root.get(Discipline_.NAME)), pattern);
    }

    public static Specification<Discipline> nameEquals(String name) {
        return (root, query, builder) -> builder.equal(root.get(Discipline_.NAME), name);
    }

    public static Specification<Discipline> shortNameLike(String shortName) {
        String pattern = "%" + shortName.toLowerCase() + "%";
        return (root, query, builder) -> builder.like(builder.lower(root.get(Discipline_.SHORT_NAME)), pattern);
    }

    public static Specification<Discipline> shortNameEquals(String shortName) {
        return (root, query, builder) -> builder.equal(root.get(Discipline_.SHORT_NAME), shortName);
    }
}
