package hello.spring.condition;

/**
 * 機能サービスのインターフェース
 */
public interface FeatureService {
    
    /**
     * 機能の説明を取得
     * @return 機能の説明
     */
    String getFeatureDescription();
    
    /**
     * 機能を実行
     * @return 実行結果
     */
    String executeFeature();
}