package by.kovzov.uis.academic.repository.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "curriculum_discipline")
@Getter
@Setter
@ToString
public class CurriculumDiscipline {

    @EmbeddedId
    private CurriculumDisciplineId id;

    private int semester;

    private int totalHours;

    private int lectureHours;

    private int practiceHours;

    private int labHours;

    private int selfStudyHours;

    private int testCount;

    private boolean hasCredit;

    private boolean hasExam;

    private double creditUnits;

   @Data
   @AllArgsConstructor
   @NoArgsConstructor
   @Embeddable
    public static class CurriculumDisciplineId implements Serializable {

        private Long curriculumId;
        private Long disciplineId;
    }

}
