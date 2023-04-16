package by.kovzov.uis.security.service.api;

import java.util.Optional;

import by.kovzov.uis.security.dto.HttpMethod;
import by.kovzov.uis.security.repository.entity.Permission;
import by.kovzov.uis.security.repository.entity.Route;

public interface RouteService {

    Route getByUrlAndMethod(String url, HttpMethod method);

    Optional<Permission> getPermissionByRouteId(Long id);
}
