package by.kovzov.uis.security.repository.api;

import by.kovzov.uis.security.repository.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    @Query("select r from Role r where lower(r.name) like lower(concat('%', :name, '%'))")
    Page<Role> findAllByNameLike(@Param("name") String name, Pageable pageable);
}
