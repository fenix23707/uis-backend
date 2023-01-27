package by.kovzov.uis.specialization.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

// HELP: https://medium.com/@kthsingh.ms/modeling-a-child-parent-relationship-in-the-same-table-using-jpa-spring-boot-and-representing-it-15e5a6256dab
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "specializations")
public class Specialization {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String shortName;

    private String cipher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @Exclude
    private Specialization parent;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    @Exclude
    private Set<Specialization> children;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Specialization that = (Specialization) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
