package by.kovzov.uis.academic.repository.entity;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "curriculums")
@Getter
@Setter
@ToString
public class Curriculum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "approval_date", nullable = false)
    private LocalDate approvalDate;

    @Column(name = "admission_year", nullable = false)
    private Integer admissionYear;

    @ManyToOne
    @JoinColumn(name = "specialization_id", nullable = false)
    @ToString.Exclude
    private Specialization specialization;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (!(that instanceof Curriculum curriculum)) {
            return false;
        }
        return id != null && Objects.equals(id, curriculum.id);
    }
}
