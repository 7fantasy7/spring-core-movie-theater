package ua.epam.spring.hometask.config;

import freemarker.template.utility.XmlEscape;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan({"ua.epam.spring.hometask", "ua.epam.spring.hometask.controller"})
@EnableWebMvc
@Import({SecurityConfig.class})
public class MVCConfig extends WebMvcConfigurerAdapter {

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("/WEB-INF/pages/");
        Map<String, Object> map = new HashMap<>();
        map.put("xml_escape", new XmlEscape());
        configurer.setFreemarkerVariables(map);
        return configurer;
    }

    @Bean
    public ViewResolver viewResolver() {
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
        freeMarkerViewResolver.setPrefix("");
        freeMarkerViewResolver.setSuffix(".ftl");
        freeMarkerViewResolver.setCache(true);
        freeMarkerViewResolver.setContentType("text/html; charset=UTF-8");
        return freeMarkerViewResolver;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        resolver.setDefaultErrorView("error");
        resolver.setExceptionAttribute("ex");
        return resolver;
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.ignoreUnknownPathExtensions(false).defaultContentType(MediaType.TEXT_HTML);
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.freeMarker();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/WEB-INF/pages/").addResourceLocations("/pages/");
    }


}
