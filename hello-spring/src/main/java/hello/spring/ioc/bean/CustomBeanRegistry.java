package hello.spring.ioc.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;

import hello.spring.service.IAccountService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class CustomBeanRegistry implements BeanDefinitionRegistryPostProcessor {

    public static final String PROCESSOR_BEAN_NAME = "accountServiceImpl";
    public static final String BEAN_NAME_PREFIX = "imoocMultiMessageHandler";

    /** bean 初始化与销毁的执行方法 */
    public static final String INIT_METHOD_NAME = "start";
    public static final String DESTROY_METHOD_NAME = "close";


    /**
     * <h2>Define an implementation of IAccountService</h2>
     */
    @Bean(name = PROCESSOR_BEAN_NAME)
    public IAccountService accountService() {
        return new IAccountService() {
            @Override
            public void queryAccountInfo(String username) {
                log.info("Querying account info for user: {}", username);
            }
        };
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
            throws BeansException {

        String beanName = buildBeanName("bean-name");
        BeanDefinition definition = buildBeanDefinition("bean-name");
        registry.registerBeanDefinition(beanName, definition);
        log.info("......");
    }

    /**
     * <h2>生成一个新的 Bean 元数据定义</h2>
     * */
    public GenericBeanDefinition newBeanDefinition() {

        GenericBeanDefinition definition = new GenericBeanDefinition();
        definition.setScope(GenericBeanDefinition.SCOPE_SINGLETON);
        definition.setAbstract(false);
        definition.setLazyInit(false);
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_NO);
        definition.setDependencyCheck(GenericBeanDefinition.DEPENDENCY_CHECK_NONE);
        definition.setDependsOn();
        definition.setAutowireCandidate(false);
        definition.setPrimary(false);
        definition.setNonPublicAccessAllowed(true);
        definition.setLenientConstructorResolution(false);
        definition.setInitMethodName(INIT_METHOD_NAME);
        definition.setDestroyMethodName(DESTROY_METHOD_NAME);
        definition.setSynthetic(true);
        definition.setRole(GenericBeanDefinition.ROLE_APPLICATION);

        return definition;
    }

    public static String buildBeanName(String handlerName) {
        return String.format("%s_%s", BEAN_NAME_PREFIX, handlerName);
    }

    private ExecutorService buildExecutor() {
        return new ThreadPoolExecutor(1, 1, 60,
                TimeUnit.MINUTES, new SynchronousQueue<>());
    }

    /**
     * <h2>构建我们想要注册到 IOC 中的 bean</h2>
     * */
    private BeanDefinition buildBeanDefinition(String handlerName) {

        ConstructorArgumentValues values = new ConstructorArgumentValues();
        values.addIndexedArgumentValue(0, buildBeanName(handlerName));
        values.addIndexedArgumentValue(1, new RuntimeBeanReference(PROCESSOR_BEAN_NAME));
        values.addIndexedArgumentValue(2, buildExecutor());

        GenericBeanDefinition definition = newBeanDefinition();
        // 描述信息
        definition.setDescription(buildBeanName(handlerName));
        // 构造参数, 依赖
        definition.setConstructorArgumentValues(values);
        // bean 信息
        definition.setBeanClassName(MessageHandler.class.getName());
        definition.setBeanClass(MessageHandler.class);

        return definition;
    }
}
