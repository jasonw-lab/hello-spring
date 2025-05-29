/**
 * Created on 2018/3/22 22:04:00
 */
package hello.spring.logger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ロガーの自動設定クラス
 * Spring Bootのauto-configurationメカニズムによって自動的に読み込まれる
 */
@Configuration
@EnableConfigurationProperties(LoggerProperties.class)
public class LoggerAutoConfiguration {

    /**
     * ロギングアスペクトのBeanを生成する
     * すでに定義されていない場合のみ生成される
     * @return ロギングアスペクトのインスタンス
     */
    @Bean
    @ConditionalOnMissingBean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}