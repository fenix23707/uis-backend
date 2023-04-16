package by.kovzov.uis.security.service.api;

import by.kovzov.uis.security.dto.AuthorizationDto;

public interface AuthorizationService {

    void verifyAccess(AuthorizationDto authorizationDto);
}
