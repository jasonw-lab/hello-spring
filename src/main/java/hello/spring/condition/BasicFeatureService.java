package hello.spring.condition;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

/**
 * 基本機能サービスの実装
 * AdvancedFeatureServiceが存在しない場合にのみ有効
 */
@Service
@ConditionalOnMissingBean(AdvancedFeatureService.class)
public class BasicFeatureService implements FeatureService {

    @Override
    public String getFeatureDescription() {
        return "基本機能 - すべてのユーザーが利用できる標準機能を提供します";
    }

    @Override
    public String executeFeature() {
        System.out.println("基本機能を実行しています...");
        // 基本機能の実行をシミュレート
        return "基本機能が正常に実行されました。標準的な結果が生成されました。";
    }
}