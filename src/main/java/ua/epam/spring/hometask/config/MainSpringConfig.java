package ua.epam.spring.hometask.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import freemarker.template.utility.XmlEscape;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import ua.epam.spring.hometask.aspect.LuckyWinnerAspect;
import ua.epam.spring.hometask.domain.Auditorium;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Evgeny_Botyanovsky
 */
@Configuration
@ComponentScan("ua.epam.spring.hometask")
@PropertySource(value = {"classpath:/auditoriums.properties", "classpath:/db.properties"})
@EnableTransactionManagement(proxyTargetClass = true)
@EnableAspectJAutoProxy
@EnableAsync
@EnableWebMvc
public class MainSpringConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment environment;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() throws Exception {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(environment.getRequiredProperty("db.driver.class"));
        dataSource.setUrl(environment.getRequiredProperty("db.url"));
        dataSource.setUsername(environment.getRequiredProperty("db.username"));
        dataSource.setPassword(environment.getRequiredProperty("db.password"));

        return dataSource;
    }

    @Bean
    @Autowired
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) throws IOException {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(dataSource);
        final String[] packages = new String[]{"ua.epam.spring.hometask.domain"};
        bean.setAnnotatedPackages(packages);
        bean.setPackagesToScan(packages);

        Properties props = new Properties();
        props.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        props.put("hibernate.hbm2ddl.auto", "validate");
        props.put("hibernate.id.new_generator_mappings", "false");
        props.put("hibernate.show_sql", "false");
        props.put("hibernate.format_sql", "false");

        bean.setHibernateProperties(props);
        bean.afterPropertiesSet();
        return bean;
    }

    @Bean
    @Autowired
    public static HibernateTransactionManager hibernateTransactionManager(DataSource dataSource, SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager(sessionFactory);
        txManager.setDataSource(dataSource);
        return txManager;
    }

    @Bean
    @Qualifier("redAuditorium")
    public Auditorium redAuditorium() {
        final Set<Long> redAuditoriumVipSeats = Arrays.stream(environment.getProperty("one.vipSeats")
                .split(","))
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        return new Auditorium()
                .setName(environment.getProperty("one.name"))
                .setNumberOfSeats(Integer.parseInt(environment.getProperty("one.numberOfSeats")))
                .setVipSeats(redAuditoriumVipSeats);
    }

    @Bean
    @Qualifier("yellowAuditorium")
    public Auditorium yellowAuditorium() {
        final Set<Long> yellowAuditoriumVipSeats = Arrays.stream(environment.getProperty("two.vipSeats")
                .split(","))
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        return new Auditorium()
                .setName(environment.getProperty("two.name"))
                .setNumberOfSeats(Integer.parseInt(environment.getProperty("two.numberOfSeats")))
                .setVipSeats(yellowAuditoriumVipSeats);
    }

    @Bean
    @Qualifier("blueAuditorium")
    public Auditorium blueAuditorium() {
        final Set<Long> yellowAuditoriumVipSeats = Arrays.stream(environment.getProperty("three.vipSeats")
                .split(","))
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        return new Auditorium()
                .setName(environment.getProperty("three.name"))
                .setNumberOfSeats(Integer.parseInt(environment.getProperty("three.numberOfSeats")))
                .setVipSeats(yellowAuditoriumVipSeats);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
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
        //freeMarkerViewResolver.setPrefix("");
        freeMarkerViewResolver.setSuffix(".ftl");
        freeMarkerViewResolver.setCache(true);
        return freeMarkerViewResolver;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
        return resolver;
    }

    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        resolver.setDefaultErrorView("error");
        resolver.setExceptionAttribute("ex");
        return resolver;
    }

}
