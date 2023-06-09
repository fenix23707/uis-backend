package by.kovzov.uis.academic.service.impl;

import static org.apache.commons.lang3.Validate.notBlank;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import by.kovzov.uis.academic.service.api.StartUpService;
import by.vsu.uis.security.permission.PermissionProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class StartUpServiceImpl implements StartUpService {

    private final List<PermissionProducer> permissionProducers;
    private final WebClient securityWebClient;

    private String applicationName;

    @Value("${spring.application.name}")
    public void setApplicationName(String applicationName) {
        this.applicationName = notBlank(applicationName, "application name can not be blank");
    }


    @Override
    public void updatePermissions() { // TODO: optimize using reactive approach
        var permissions = permissionProducers.stream()
            .map(PermissionProducer::producePermissions)
            .flatMap(Collection::stream)
            .toList();
        securityWebClient.post()
            .uri("/api/internal/permissions/" + applicationName)
            .bodyValue(permissions)
            .retrieve()
            .bodyToMono(String.class)
            .doOnSuccess(message -> log.info("Permission have been updated successfully: " + message))
            .doOnError(message -> {
                throw new RuntimeException("Failed to save permissions, message: " + message);
            })
            .block();
    }
}
