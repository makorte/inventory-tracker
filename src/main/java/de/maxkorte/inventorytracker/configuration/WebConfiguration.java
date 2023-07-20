package de.maxkorte.inventorytracker.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String userHomeDirectory = System.getProperty("user.home");
        String externalImagePath = "file:" + userHomeDirectory + "/inventory-manager-img/";
        registry.addResourceHandler("/images/**")
                .addResourceLocations(externalImagePath);
    }
}
