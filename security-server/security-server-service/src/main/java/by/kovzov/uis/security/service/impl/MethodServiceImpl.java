package by.kovzov.uis.security.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import by.kovzov.uis.security.dto.MethodDto;
import by.kovzov.uis.security.repository.api.MethodRepository;
import by.kovzov.uis.security.repository.entity.Method;
import by.kovzov.uis.security.repository.entity.Permission;
import by.kovzov.uis.security.service.api.MethodService;
import by.kovzov.uis.security.service.mapper.MethodMapper;
import by.kovzov.uis.security.service.util.CollectionsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class MethodServiceImpl implements MethodService {

    private final MethodRepository methodRepository;
    private final MethodMapper methodMapper;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateMethodsByPermission(Permission permission, Collection<MethodDto> methods) {
        var entities = methodMapper.toEntities(methods);

        var existingMethods = methodRepository.findAllIn(entities);
        existingMethods.forEach(method -> method.setPermission(permission));
        methodRepository.saveAll(existingMethods);

        var newMethods = entities.stream()
            .filter(Predicate.not(existingMethods::contains))
            .peek(method -> method.setPermission(permission))
            .toList();
        methodRepository.saveAll(newMethods);

        var methodsToDelete = permission.getMethods().stream()
            .filter(Predicate.not(entities::contains))
            .peek(method -> method.setPermission(null))
            .toList();
        if (!methodsToDelete.isEmpty()) {
            methodRepository.saveAll(methodsToDelete);
        }

        permission.getMethods().clear();
        permission.getMethods().addAll(entities);
    }

    @Override
    public void deleteAllExcept(Collection<MethodDto> methodsNotDelete) {
        var entities = methodMapper.toEntities(methodsNotDelete);
        methodRepository.deleteAllExcept(entities);
    }

    @Override
    public void deleteAll(Collection<Method> methods) {
        if (CollectionUtils.isEmpty(methods)) {
            return;
        }
        methodRepository.flush();
        methodRepository.deleteAllIn(methods);
    }

    @Override
    public Optional<Permission> findByMethods(List<Permission> existingPermissions, Collection<MethodDto> methods) {
        var entities = methodMapper.toEntities(methods);
        return existingPermissions.stream()
            .filter(permission -> !permission.getMethods().isEmpty())
            .filter(permission -> entities.containsAll(permission.getMethods()))
            .collect(CollectionsUtil.toSingleElement("Find more than one permissions by methods: %s", methods));
    }
}
