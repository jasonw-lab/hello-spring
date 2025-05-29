package hello.spring.ioc.concept.client;

import hello.spring.ioc.concept.service.MessageService;
import hello.spring.ioc.concept.service.TimeService;

/**
 * 複数のサービスに依存するより複雑なクライアント。
 * このクラスは、IoCが複数の依存関係の管理をどのように簡素化するかを示します。
 */
public class ComplexClient {

    private final MessageService messageService;
    private final TimeService timeService;

    /**
     * 複数のサービス依存関係を受け取るコンストラクタ。
     */
    public ComplexClient(MessageService messageService, TimeService timeService) {
        this.messageService = messageService;
        this.timeService = timeService;
    }

    /**
     * 両方の注入されたサービスを使用してタイムスタンプ付きメッセージを作成します。
     */
    public String createTimestampedMessage() {
        return String.format("[%s] %s", 
                timeService.getCurrentTime(), 
                messageService.getMessage());
    }

    /**
     * クライアントが複数のサービスを一緒に使用する方法を示します。
     */
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== Report Generated ===\n");
        report.append("Time: ").append(timeService.getCurrentTime()).append("\n");
        report.append("Message: ").append(messageService.getMessage()).append("\n");
        report.append("=== End of Report ===");
        return report.toString();
    }
}
