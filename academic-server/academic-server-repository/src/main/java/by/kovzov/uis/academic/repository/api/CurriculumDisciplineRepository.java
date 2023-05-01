package by.kovzov.uis.academic.repository.api;

import java.util.List;

import by.kovzov.uis.academic.repository.entity.CurriculumDiscipline;
import by.kovzov.uis.academic.repository.entity.CurriculumDiscipline.CurriculumDisciplineId;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CurriculumDisciplineRepository extends JpaRepository<CurriculumDiscipline, CurriculumDisciplineId> {

    @Query("from CurriculumDiscipline cd where cd.id.curriculumId = :curriculumId ")
    List<CurriculumDiscipline> findAllByCurriculumId(Long curriculumId, Sort sort);
}
