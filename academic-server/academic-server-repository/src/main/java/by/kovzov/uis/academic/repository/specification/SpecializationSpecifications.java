package by.kovzov.uis.academic.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import by.kovzov.uis.academic.repository.entity.Specialization;
import by.kovzov.uis.academic.repository.entity.Specialization_;

public class SpecializationSpecifications {

    public static Specification<Specialization> mameLike(String name) {
        String pattern = "%" + name.toLowerCase() + "%";
        return (root, query, builder) -> builder.like(builder.lower(root.get(Specialization_.NAME)), pattern);
    }

    public static Specification<Specialization> nameEquals(String name) {
        return (root, query, builder) -> builder.equal(root.get(Specialization_.NAME), name);
    }

    public static Specification<Specialization> shortNameLike(String shortName) {
        String pattern = "%" + shortName.toLowerCase() + "%";
        return (root, query, builder) -> builder.like(builder.lower(root.get(Specialization_.SHORT_NAME)), pattern);
    }

    public static Specification<Specialization> shortNameEquals(String shortName) {
        return (root, query, builder) -> builder.equal(root.get(Specialization_.SHORT_NAME), shortName);
    }

    public static Specification<Specialization> cipherLike(String cipher) {
        String pattern = "%" + cipher.toLowerCase() + "%";
        return (root, query, builder) -> builder.like(builder.lower(root.get(Specialization_.CIPHER)), pattern);
    }

    public static Specification<Specialization> cipherEquals(String cipher) {
        return (root, query, builder) -> builder.equal(root.get(Specialization_.CIPHER), cipher);
    }
}
