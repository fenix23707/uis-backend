package by.kovzov.uis.security.repository.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity
@Table(name = "methods", schema = "security")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Method {

    @Id
    private String description;

    @ManyToOne
    @JoinColumn(name = "permission_id")
    @ToString.Exclude
    private Permission permission;

    public Method(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(description);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (!(that instanceof Method method)) {
            return false;
        }

        return new EqualsBuilder()
            .append(description, method.description)
            .isEquals();
    }
}
