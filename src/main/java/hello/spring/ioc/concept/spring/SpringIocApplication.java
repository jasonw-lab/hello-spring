package hello.spring.ioc.concept.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.spring.ioc.concept.client.ComplexClient;
import hello.spring.ioc.concept.client.MessageClient;

/**
 * Spring IoCを示すメインアプリケーションクラス。
 * このクラスはSpringのIoCコンテナを使用して依存関係を管理します。
 * IoCコンテナがBeanの作成と依存関係の注入を自動的に行うため、
 * 開発者は依存関係の管理ではなく、ビジネスロジックに集中できます。
 * これにより、コードの保守性と拡張性が向上します。
 */
public class SpringIocApplication {

    public static void main(String[] args) {
        // 設定クラスを使用してSpringアプリケーションコンテキストを作成
        try (AnnotationConfigApplicationContext context = 
                new AnnotationConfigApplicationContext(AppConfig.class)) {

            System.out.println("=== Spring IoC Example ===");

            // MessageClientを使用したシンプルな例
            System.out.println("\n--- Simple Client Example ---");
            // SpringコンテキストからMessageClient Beanを取得
            // Springはすでにインスタンス化し、依存関係を注入している
            MessageClient simpleClient = context.getBean(MessageClient.class);

            // クライアントを使用してメッセージを処理
            String simpleResult = simpleClient.processMessage();
            System.out.println(simpleResult);

            // ComplexClientを使用した複雑な例
            System.out.println("\n--- Complex Client Example ---");
            // SpringコンテキストからComplexClient Beanを取得
            // Springはすでにインスタンス化し、すべての依存関係を注入している
            ComplexClient complexClient = context.getBean(ComplexClient.class);

            // 複雑なクライアントを使用してタイムスタンプ付きメッセージを作成
            String timestampedMessage = complexClient.createTimestampedMessage();
            System.out.println("Timestamped message: " + timestampedMessage);

            // 複雑なクライアントを使用してレポートを生成
            String report = complexClient.generateReport();
            System.out.println("\nReport:\n" + report);

            System.out.println("\n=== Spring IoC Example End ===");
        }
    }
}
