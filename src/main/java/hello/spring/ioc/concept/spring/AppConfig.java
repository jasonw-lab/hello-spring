package hello.spring.ioc.concept.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.spring.ioc.concept.client.ComplexClient;
import hello.spring.ioc.concept.client.MessageClient;
import hello.spring.ioc.concept.service.MessageService;
import hello.spring.ioc.concept.service.SimpleMessageService;
import hello.spring.ioc.concept.service.TimeService;
import hello.spring.ioc.concept.service.SimpleTimeService;

/**
 * IoCコンテナのBeanを定義するSpring設定クラス。
 */
@Configuration
public class AppConfig {

    /**
     * MessageService Beanを作成します。
     */
    @Bean
    public MessageService messageService() {
        return new SimpleMessageService();
    }

    /**
     * TimeService Beanを作成します。
     */
    @Bean
    public TimeService timeService() {
        return new SimpleTimeService();
    }

    /**
     * MessageServiceが注入されたMessageClient Beanを作成します。
     * SpringはmessageService()メソッドによって作成されたMessageService Beanを自動的に注入します。
     */
    @Bean
    public MessageClient messageClient(MessageService messageService) {
        return new MessageClient(messageService);
    }

    /**
     * MessageServiceとTimeServiceの両方が注入されたComplexClient Beanを作成します。
     * Springは両方の依存関係を自動的に注入します。
     */
    @Bean
    public ComplexClient complexClient(MessageService messageService, TimeService timeService) {
        return new ComplexClient(messageService, timeService);
    }
}
