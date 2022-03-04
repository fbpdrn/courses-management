package it.unibg.studenti;

import com.vaadin.collaborationengine.CollaborationEngineConfiguration;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static io.swagger.codegen.v3.config.CodegenConfigurator.LOGGER;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@Push
@SpringBootApplication
@Theme(value = "coursemanagement")
@PWA(name = "Course Management", shortName = "Course Management", offlineResources = {"images/logo.png"})
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CollaborationEngineConfiguration ceConfigBean() {
        CollaborationEngineConfiguration configuration = new CollaborationEngineConfiguration(
                licenseEvent -> {
                    switch (licenseEvent.getType()) {
                        case GRACE_PERIOD_STARTED, LICENSE_EXPIRES_SOON -> LOGGER.warn(licenseEvent.getMessage());
                        case GRACE_PERIOD_ENDED, LICENSE_EXPIRED -> LOGGER.error(licenseEvent.getMessage());
                    }
                });
        configuration.setDataDir(System.getProperty("user.home") + "/collaboration-engine/");
        return configuration;
    }
}
