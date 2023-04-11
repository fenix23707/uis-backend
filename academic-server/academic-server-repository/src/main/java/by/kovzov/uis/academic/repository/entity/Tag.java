package by.kovzov.uis.academic.repository.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import by.kovzov.uis.common.validator.unique.Unique;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "tags")
@Getter
@Setter
@ToString
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId(mutable = true)
    @Unique
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @ToString.Exclude
    private Tag parent;

    @OneToMany(mappedBy = "parent")
    @ToString.Exclude
    private Set<Tag> children;

    @ManyToMany(mappedBy = "tags")
    @ToString.Exclude
    private Set<Discipline> disciplines = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (!(that instanceof Tag tag)) {
            return false;
        }
        return Objects.equals(name, tag.name);
    }
}
