package by.kovzov.uis.security.repository.api;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import by.kovzov.uis.security.repository.entity.Method;
import by.kovzov.uis.security.repository.entity.Permission;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PermissionRepository extends JpaRepository<Permission, Long> {


    @Modifying
    @Query("delete from Permission p where p.methods is empty")
    void deleteAllWithoutMethods();

    @EntityGraph(attributePaths = "methods", type = EntityGraphType.FETCH)
    List<Permission> findAllByMethodsIn(Collection<Method> methods);

    @EntityGraph(attributePaths = "methods", type = EntityGraphType.FETCH)
    @Query("from Permission")
    List<Permission> findAllWithMethods();

    @EntityGraph(attributePaths = "methods", type = EntityGraphType.FETCH)
    @Query("from Permission p where p.applicationName = :applicationName")
    List<Permission> findAllWithMethodsByApplicationName(String applicationName);

    @EntityGraph(attributePaths = "methods", type = EntityGraphType.FETCH)
    @Query("from Permission where scope = :scope and action = :action")
    Optional<Permission> findWithMethods(String scope, String action);

    @Modifying
    @Query("delete from Permission p where p in :permissions")
    void deleteAllIn(Collection<Permission> permissions);
}
