package by.kovzov.uis.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import lombok.Data;
import lombok.EqualsAndHashCode;

// HELP: https://medium.com/@kthsingh.ms/modeling-a-child-parent-relationship-in-the-same-table-using-jpa-spring-boot-and-representing-it-15e5a6256dab
@Data
@Entity
@Table(name = "specializations")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Specialization {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    private String shortName;

    private String cipher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Specialization parent;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    private Set<Specialization> children;
}

