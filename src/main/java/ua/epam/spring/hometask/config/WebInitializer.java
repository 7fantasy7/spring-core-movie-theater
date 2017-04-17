package ua.epam.spring.hometask.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import java.io.File;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final int MAX_UPLOAD_SIZE_IN_MB = 5 * 1024 * 1024; // 5 MB

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{MVCConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        File uploadDirectory = new File(System.getProperty("java.io.tmpdir"));
        MultipartConfigElement multipartConfigElement =
                new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
                        MAX_UPLOAD_SIZE_IN_MB, MAX_UPLOAD_SIZE_IN_MB * 2, MAX_UPLOAD_SIZE_IN_MB / 2);
        registration.setMultipartConfig(multipartConfigElement);
    }

}
