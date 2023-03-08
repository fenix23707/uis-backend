package by.kovzov.uis.specialization.repository.entity;

import java.util.Set;

import by.kovzov.uis.specialization.repository.annotation.Unique;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

// HELP: https://medium.com/@kthsingh.ms/modeling-a-child-parent-relationship-in-the-same-table-using-jpa-spring-boot-and-representing-it-15e5a6256dab

@NamedEntityGraphs({
    @NamedEntityGraph(
        name = "specialization-dto-entity-graph",
        attributeNodes = {
            @NamedAttributeNode(value = "children"),
        }
    )
})
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "specializations")
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Unique
    private String name;

    @Unique
    private String shortName;

    @Unique
    private String cipher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Specialization parent;

    @OneToMany(mappedBy = "parent")
    private Set<Specialization> children;
}
