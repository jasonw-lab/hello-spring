package hello.spring.ioc.concept.service;

/**
 * IoC と非IoC の実装を示すためのシンプルなサービスインターフェース。
 */
public interface MessageService {

    /**
     * サービスからメッセージを取得します。
     */
    String getMessage();
}
