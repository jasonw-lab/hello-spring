package hello.spring.ioc.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * <h1>Application class to demonstrate CustomBeanRegistry functionality</h1>
 */
@Slf4j
public class CustomRegisteredBeanApplication {

    /**
     * <h2>Main method to demonstrate CustomBeanRegistry functionality</h2>
     */
    public static void main(String[] args) {
        // Create and configure the Spring context
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(CustomBeanRegistry.class);

        // Log the registered beans
        log.info("Registered beans:");
        for (String beanName : context.getBeanDefinitionNames()) {
            log.info("Bean: {}", beanName);
        }

        // Get and use the custom registered bean
        MessageHandler handler = (MessageHandler) context.getBean(CustomBeanRegistry.buildBeanName("bean-name"));
        log.info("Successfully retrieved custom bean: {}", handler);

        // Close the context to trigger destroy methods
        context.close();
    }
}