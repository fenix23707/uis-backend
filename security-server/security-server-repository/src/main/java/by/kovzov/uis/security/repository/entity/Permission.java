package by.kovzov.uis.security.repository.entity;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity
@Table(name = "permissions", schema = "security")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String scope;

    private String action;

    private String applicationName;

    @OneToMany(mappedBy = "permission")
    @ToString.Exclude
    private Set<Method> methods = Collections.emptySet();

    public Permission(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (!(that instanceof Permission permission)) {
            return false;
        }
        return Objects.equals(id, permission.id);
    }

    public boolean equalsByScopeAndAction(Object that) {
        if (this == that) {
            return true;
        }
        if (!(that instanceof Permission permission)) {
            return false;
        }
        return new EqualsBuilder()
            .append(scope, permission.scope)
            .append(action, permission.action)
            .isEquals();
    }
}
