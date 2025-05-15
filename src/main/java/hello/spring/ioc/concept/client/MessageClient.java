package hello.spring.ioc.concept.client;

import hello.spring.ioc.concept.service.MessageService;

/**
 * MessageServiceに依存するクライアントクラス。
 * このクラスは、Spring IoCによる依存性注入と、
 * IoCを使用しない手動の依存関係管理を示すために使用されます。
 */
public class MessageClient {

    private final MessageService messageService;

    /**
     * MessageService依存関係を受け取るコンストラクタ。
     */
    public MessageClient(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * 注入されたMessageServiceを使用してメッセージを取得し表示します。
     */
    public String processMessage() {
        return "Client processed: " + messageService.getMessage();
    }
}
