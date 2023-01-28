package by.kovzov.uis.specialization.repository.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

import by.kovzov.uis.specialization.repository.entity.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

    List<Specialization> findAllChildrenByParentId(Long parentId);

    @Query("select s.id from Specialization s where s.parent.id is null")
    Page<Long> findAllParentIds(Pageable pageable);

    @Query("from Specialization s where s.parent.id in :ids")
    @EntityGraph(attributePaths = "children")
    List<Specialization> findAllWithChildrenByIds(Set<Long> ids);
}
