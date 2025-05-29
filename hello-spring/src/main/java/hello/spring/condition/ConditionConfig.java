package hello.spring.condition;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * 条件付きBeanの設定クラス
 */
@Configuration
@PropertySource("classpath:application.properties") // プロパティファイルを読み込む
public class ConditionConfig {

    /**
     * カスタム条件クラス - メモリが十分にある場合にtrueを返す
     */
    public static class HighMemoryCondition implements org.springframework.context.annotation.Condition {
        @Override
        public boolean matches(org.springframework.context.annotation.ConditionContext context,
                              org.springframework.core.type.AnnotatedTypeMetadata metadata) {
            // システムプロパティからメモリ設定を取得（実際のアプリケーションでは実際のメモリ使用量をチェック）
            String memoryProp = context.getEnvironment().getProperty("app.memory");
            return "high".equals(memoryProp);
        }
    }

    /**
     * 高メモリ環境用のメッセージサービス
     * app.memory=high の場合にのみ有効
     */
    @Bean
    @Conditional(HighMemoryCondition.class)
    public MessageService highMemoryMessageService() {
        return new MessageService() {
            @Override
            public String getMessage() {
                return "高メモリ環境用のメッセージサービスが有効です。大量のデータを処理できます。";
            }
        };
    }

    /**
     * 標準メモリ環境用のメッセージサービス
     * 他のMessageServiceが存在しない場合にのみ有効
     */
    @Bean
    @org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean(MessageService.class)
    public MessageService standardMessageService() {
        return new MessageService() {
            @Override
            public String getMessage() {
                return "標準メモリ環境用のメッセージサービスが有効です。通常の処理を行います。";
            }
        };
    }

    /**
     * メッセージサービスのインターフェース
     */
    public interface MessageService {
        String getMessage();
    }
}