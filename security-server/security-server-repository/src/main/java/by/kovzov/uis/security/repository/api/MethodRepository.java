package by.kovzov.uis.security.repository.api;

import java.util.Collection;
import java.util.List;

import by.kovzov.uis.security.repository.entity.Method;
import by.kovzov.uis.security.repository.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MethodRepository extends JpaRepository<Method, String> {


    @Query("from Method m where m in :methods")
    List<Method> findAllIn(Collection<Method> methods);

    @Modifying
    @Query("delete from Method m where m not in :methods")
    void deleteAllExcept(Collection<Method> methods);

    @Modifying
    @Query("delete from Method m where m in :methods")
    void deleteAllIn(Collection<Method> methods);

    @Modifying
    @Query("delete from Method m where m.permission = :permission and m not in :except")
    void deleteAllByPermission(Permission permission, Collection<Method> except);
}
