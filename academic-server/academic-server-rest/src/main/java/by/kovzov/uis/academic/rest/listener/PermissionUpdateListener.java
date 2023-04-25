package by.kovzov.uis.academic.rest.listener;

import by.kovzov.uis.academic.service.api.StartUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionUpdateListener implements ApplicationListener<ContextRefreshedEvent> {

    private final StartUpService startUpService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        startUpService.updatePermissions();
    }
}
