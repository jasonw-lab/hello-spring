package hello.spring.ioc.concept.nonioc;

import hello.spring.ioc.concept.client.ComplexClient;
import hello.spring.ioc.concept.client.MessageClient;
import hello.spring.ioc.concept.service.MessageService;
import hello.spring.ioc.concept.service.SimpleMessageService;
import hello.spring.ioc.concept.service.TimeService;
import hello.spring.ioc.concept.service.SimpleTimeService;

/**
 * IoCを使用しない従来の依存関係管理を示すメインアプリケーションクラス。
 * このクラスは依存関係を手動で作成し配線します。
 * 従来のアプローチでは、開発者が明示的に依存関係を作成し管理する必要があります。
 * アプリケーションが複雑になるにつれて、依存関係の管理も複雑になり、
 * コードの保守性と拡張性が低下する可能性があります。
 * このサンプルは、IoCを使用しない場合の依存関係管理の課題を示しています。
 */
public class NonIocApplication {

    public static void main(String[] args) {
        System.out.println("=== Non-IoC Example ===");

        // MessageClientを使用したシンプルな例
        System.out.println("\n--- Simple Client Example ---");

        // サービスを手動で作成
        MessageService messageService = new SimpleMessageService();

        // クライアントを手動で作成し、サービスを注入
        MessageClient simpleClient = new MessageClient(messageService);

        // クライアントを使用してメッセージを処理
        String simpleResult = simpleClient.processMessage();
        System.out.println(simpleResult);

        // ComplexClientを使用した複雑な例
        System.out.println("\n--- Complex Client Example ---");

        // 必要なすべてのサービスを手動で作成
        // 新しいインスタンスが必要な場合はMessageServiceを再度作成する必要がある
        // またはシングルトンのような動作が必要な場合は既存のものを再利用する
        MessageService messageServiceForComplex = messageService; // 同じインスタンスを再利用
        TimeService timeService = new SimpleTimeService();

        // 複雑なクライアントを手動で作成し、すべての依存関係を注入
        ComplexClient complexClient = new ComplexClient(messageServiceForComplex, timeService);

        // 複雑なクライアントを使用してタイムスタンプ付きメッセージを作成
        String timestampedMessage = complexClient.createTimestampedMessage();
        System.out.println("Timestamped message: " + timestampedMessage);

        // 複雑なクライアントを使用してレポートを生成
        String report = complexClient.generateReport();
        System.out.println("\nReport:\n" + report);

        System.out.println("\n=== Non-IoC Example End ===");
    }
}
