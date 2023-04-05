package by.kovzov.uis.academic.repository.api;

import java.util.List;
import java.util.Set;

import by.kovzov.uis.academic.repository.entity.Discipline;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface DisciplineRepository extends JpaRepository<Discipline, Long>, JpaSpecificationExecutor<Discipline> {

    @EntityGraph(attributePaths = "tags")
    @Query("from Discipline d where d.id in :ids")
    List<Discipline> findAllWithTagsByIds(Set<Long> ids, Sort sort);
}
