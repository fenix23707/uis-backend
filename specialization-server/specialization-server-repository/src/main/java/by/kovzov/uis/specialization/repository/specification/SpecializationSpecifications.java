package by.kovzov.uis.specialization.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import by.kovzov.uis.specialization.repository.entity.Specialization;
import by.kovzov.uis.specialization.repository.entity.Specialization_;

public class SpecializationSpecifications {

    public static Specification<Specialization> hasNameLike(String name) {
        String pattern = "%" + name.toLowerCase() + "%";
        return (root, query, builder) -> builder.like(builder.lower(root.get(Specialization_.NAME)), pattern);
    }

    public static Specification<Specialization> hasShortNameLike(String shortName) {
        String pattern = "%" + shortName.toLowerCase() + "%";
        return (root, query, builder) -> builder.like(builder.lower(root.get(Specialization_.SHORT_NAME)), pattern);
    }

    public static Specification<Specialization> hasCipherLike(String cipher) {
        String pattern = "%" + cipher.toLowerCase() + "%";
        return (root, query, builder) -> builder.like(builder.lower(root.get(Specialization_.CIPHER)), pattern);
    }
}
