package by.kovzov.uis.security.service.impl;

import java.util.List;

import by.kovzov.uis.common.exception.AccessDeniedException;
import by.kovzov.uis.security.dto.AuthorizationDto;
import by.kovzov.uis.security.repository.entity.Permission;
import by.kovzov.uis.security.service.api.AuthorizationService;
import by.kovzov.uis.security.service.api.PermissionService;
import by.kovzov.uis.security.service.api.RouteService;
import by.kovzov.uis.security.service.api.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final TokenService tokenService;
    private final PermissionService permissionService;
    private final RouteService routeService;

    @Override
    public void verifyAccess(AuthorizationDto dto) {
        var route = routeService.getByUrlAndMethod(dto.getUrl(), dto.getHttpMethod());
        var permission = routeService.getPermissionByRouteId(route.getId());
        permission.ifPresent(it -> verifyUserHasPermission(it, dto.getAccessToken()));

    }

    private void verifyUserHasPermission(Permission permission, String accessToken) {
        List<Long> roleIds = tokenService.extractRoleIds(accessToken);
        var permissions = permissionService.getAllByRolesIds(roleIds);
        if (!permissions.contains(permission)) {
            throw new AccessDeniedException("Access denied");
        }
    }
}
