package ua.epam.spring.hometask.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

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

import ua.epam.spring.hometask.domain.Auditorium;

/**
 * @author Evgeny_Botyanovsky
 */
@Configuration
@ComponentScan("ua.epam.spring.hometask")
@PropertySource(value = {"classpath:/auditoriums.properties", "classpath:/db.properties"})
@EnableTransactionManagement(proxyTargetClass = true)
@EnableAspectJAutoProxy
@EnableAsync
public class MainSpringConfig {

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

}
