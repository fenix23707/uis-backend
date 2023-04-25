package by.kovzov.uis.security.rest.litener;

import by.kovzov.uis.security.service.api.StartUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PermissionUpdateListener implements ApplicationListener<ContextRefreshedEvent> {

    private final StartUpService startUpService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        startUpService.updatePermissions();
        try {
            startUpService.updateAdminUser();
        } catch (Exception e) {
            log.warn("Unable to update admin user: " + e.getMessage());
        }
    }
}
