package by.kovzov.uis.security.repository.api;

import java.util.Optional;

import by.kovzov.uis.security.repository.entity.Route;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RouteRepository extends JpaRepository<Route, Long> {


    @EntityGraph(attributePaths = "permission")
    @Query("from Route r where r.id = :id")
    Optional<Route> findByIdWithPermission(Long id);
}
