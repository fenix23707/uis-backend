package by.kovzov.uis.repository.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import by.kovzov.uis.domain.entity.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

    @Query("from Specialization s where s.parent.id = :parentId")
    List<Specialization> findAllChildrenByParentId(Long parentId);

    @Query("from Specialization s where s.parent.id is null")
    Page<Specialization> findAllParents(Pageable pageable);
}
