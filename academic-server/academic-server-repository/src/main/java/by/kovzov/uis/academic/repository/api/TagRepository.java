package by.kovzov.uis.academic.repository.api;

import java.util.List;
import java.util.Set;

import by.kovzov.uis.academic.repository.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends JpaRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {

    @Query("select t from Tag t where lower(t.name) like lower(concat('%', :name, '%'))")
    Page<Tag> findAllByNameLike(@Param("name") String name, Pageable pageable);

    @Query("select t from Tag t where t.parent.id is null")
    Page<Tag> findAllParents(Pageable pageable);

    List<Tag> findAllChildrenByParentId(Long parentId, Sort sort);

    @Query("from Specialization s where s.id in :ids")
    @EntityGraph(attributePaths = "children")
    List<Tag> findAllWithChildrenByIds(Set<Long> ids,  Sort sort);
}
