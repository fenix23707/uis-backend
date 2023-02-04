package by.kovzov.uis.specialization.repository.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import by.kovzov.uis.specialization.repository.entity.Specialization;

public interface SpecializationRepository
    extends JpaRepository<Specialization, Long>, JpaSpecificationExecutor<Specialization> {

    @Query("select s.id from Specialization s where s.parent.id is null")
    Page<Long> findAllParentIds(Pageable pageable);

    @Query("from Specialization s where s.id in :ids")
    @EntityGraph(attributePaths = "children")
    List<Specialization> findAllByIds(Set<Long> ids, Sort sort);

    @EntityGraph(attributePaths = "children")
    Set<Specialization> findAllChildrenByParentId(Long parentId, Sort sort);

    @EntityGraph(attributePaths = "children")
    Optional<Specialization> findWithChildrenById(Long id);
}
