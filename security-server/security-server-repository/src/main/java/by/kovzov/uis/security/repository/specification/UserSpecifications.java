package by.kovzov.uis.security.repository.specification;

import java.text.MessageFormat;

import by.kovzov.uis.security.repository.entity.User;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class UserSpecifications {

    public static Specification<User> usernameLike(String username) {
        String pattern = MessageFormat.format("%{0}%", username.toLowerCase());
        return (root, query, builder) -> builder.like(builder.lower(root.get("username")), pattern);
    }
}
