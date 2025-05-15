package hello.spring.ioc.concept.service;

/**
 * MessageServiceインターフェースのシンプルな実装。
 */
public class SimpleMessageService implements MessageService {

    /**
     * シンプルな挨拶メッセージを返します。
     */
    @Override
    public String getMessage() {
        return "Hello from SimpleMessageService!";
    }
}
