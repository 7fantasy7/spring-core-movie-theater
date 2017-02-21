package ua.epam.spring.hometask.runner;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Evgeny_Botyanovsky
 */
public class Runner {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("file:src/main/resources/context.xml");
        applicationContext.registerShutdownHook();
        applicationContext.close();
    }
}
