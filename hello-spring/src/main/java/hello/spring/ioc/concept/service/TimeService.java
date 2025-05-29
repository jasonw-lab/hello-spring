package hello.spring.ioc.concept.service;

/**
 * 時間関連の機能を提供するサービスインターフェース。
 * これはIoCの例で複数の依存関係を示すために使用されます。
 */
public interface TimeService {

    /**
     * フォーマットされた文字列として現在の時刻を取得します。
     */
    String getCurrentTime();
}
