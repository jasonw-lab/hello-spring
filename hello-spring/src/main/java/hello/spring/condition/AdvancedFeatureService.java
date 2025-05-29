package hello.spring.condition;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 高度な機能サービスの実装
 * app.feature.advanced プロパティが true の場合にのみ有効
 */
@Service
@ConditionalOnProperty(name = "app.feature.advanced", havingValue = "true")
public class AdvancedFeatureService implements FeatureService {

    @Override
    public String getFeatureDescription() {
        return "高度な機能 - プレミアムユーザー向けの拡張機能を提供します";
    }

    @Override
    public String executeFeature() {
        System.out.println("高度な機能を実行しています...");
        // 高度な機能の実行をシミュレート
        return "高度な機能が正常に実行されました。詳細な分析結果が生成されました。";
    }
}