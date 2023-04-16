package by.kovzov.uis.security.rest;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import by.kovzov.uis.security.dto.HttpMethod;
import by.kovzov.uis.security.repository.api.RouteRepository;
import by.kovzov.uis.security.repository.entity.Route;
import by.kovzov.uis.security.service.api.StartUpService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication(scanBasePackages = {
    "by.kovzov.uis.security",
    "by.kovzov.uis.common.exception.handler",
    "by.kovzov.uis.common.validator"
})
@OpenAPIDefinition
@AllArgsConstructor
@Slf4j
public class SecurityServerApplication implements CommandLineRunner {

    private final StartUpService startUpService;

    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    private RouteRepository routeRepository;

    public static void main(String[] args) {
        SpringApplication.run(SecurityServerApplication.class, args);
    }

    @PostConstruct
    public void onApplicationEvent() {
        routeRepository.deleteAll();//TODO: temp solution
        Map<RequestMappingInfo, HandlerMethod> mappings = requestMappingHandlerMapping.getHandlerMethods();
        for (RequestMappingInfo mappingInfo : mappings.keySet()) {
            var patterns = mappingInfo.getPathPatternsCondition().getPatterns();
            Set<RequestMethod> methods = mappingInfo.getMethodsCondition().getMethods();
            if (patterns.isEmpty() || methods.isEmpty()) {
                continue;
            }
            String pattern = patterns.iterator().next().getPatternString();
            String httpMethod = methods.iterator().next().toString();

            Route route = Route.builder()
                .pattern(pattern)
                .method(HttpMethod.valueOf(httpMethod))
                .applicationId("security-server")
                .build();
            routeRepository.save(route);
        }
    }

    @Override
    public void run(String... args) {
        try {
            startUpService.updateAdminUser();
        } catch (Exception e) {
            log.warn("Unable to update admin user: " + e.getMessage());
        }
    }
}
