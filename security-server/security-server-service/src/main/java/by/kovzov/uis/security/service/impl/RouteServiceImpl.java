package by.kovzov.uis.security.service.impl;

import java.util.Optional;

import by.kovzov.uis.common.exception.NotFoundException;
import by.kovzov.uis.security.dto.HttpMethod;
import by.kovzov.uis.security.repository.api.RouteRepository;
import by.kovzov.uis.security.repository.entity.Permission;
import by.kovzov.uis.security.repository.entity.Route;
import by.kovzov.uis.security.service.api.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final AntPathMatcher matcher;

    @Override
    public Route getByUrlAndMethod(String url, HttpMethod method) {
        return routeRepository.findAll().stream()
            .filter(route -> route.getMethod().equals(method))
            .filter(route -> matcher.match(route.getPattern(), url))
            .findAny()
            .orElseThrow(() -> new NotFoundException("Route with url = %s and method = %s not found.".formatted(url, method)));
    }

    @Override
    public Optional<Permission> getPermissionByRouteId(Long id) {
        return Optional.ofNullable(getWithPermissionById(id).getPermission());
    }

    private Route getWithPermissionById(Long id) {
        return routeRepository.findByIdWithPermission(id)
            .orElseThrow(() -> new NotFoundException("Route with id = %d not found.".formatted(id)));
    }
}
