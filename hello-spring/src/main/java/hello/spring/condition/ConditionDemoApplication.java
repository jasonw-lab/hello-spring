package hello.spring.condition;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * Spring Conditionのデモアプリケーション
 * プロファイルやプロパティに応じた構成分岐を実演する
 */
public class ConditionDemoApplication {

    public static void main(String[] args) {
        System.out.println("===== Spring Condition デモ開始 =====");
        
        // 1. プロファイルベースの条件付きBeanのデモ
        demonstrateProfileBasedCondition();
        
        // 2. プロパティベースの条件付きBeanのデモ
        demonstratePropertyBasedCondition();
        
        // 3. カスタム条件のデモ
        demonstrateCustomCondition();
        
        System.out.println("===== Spring Condition デモ終了 =====");
    }
    
    /**
     * プロファイルベースの条件付きBeanをデモンストレーション
     */
    private static void demonstrateProfileBasedCondition() {
        System.out.println("\n----- プロファイルベースの条件付きBean -----");
        
        // 開発環境プロファイルでコンテキストを作成
        System.out.println("1. 開発環境プロファイル:");
        AnnotationConfigApplicationContext devContext = new AnnotationConfigApplicationContext();
        devContext.getEnvironment().setActiveProfiles("development");
        devContext.register(DevDatabaseService.class, ProdDatabaseService.class);
        devContext.refresh();
        
        // データベースサービスを取得して使用
        DatabaseService devDbService = devContext.getBean(DatabaseService.class);
        System.out.println("接続情報: " + devDbService.getConnectionInfo());
        System.out.println("接続結果: " + devDbService.connect());
        devContext.close();
        
        // 本番環境プロファイルでコンテキストを作成
        System.out.println("\n2. 本番環境プロファイル:");
        AnnotationConfigApplicationContext prodContext = new AnnotationConfigApplicationContext();
        prodContext.getEnvironment().setActiveProfiles("production");
        prodContext.register(DevDatabaseService.class, ProdDatabaseService.class);
        prodContext.refresh();
        
        // データベースサービスを取得して使用
        DatabaseService prodDbService = prodContext.getBean(DatabaseService.class);
        System.out.println("接続情報: " + prodDbService.getConnectionInfo());
        System.out.println("接続結果: " + prodDbService.connect());
        prodContext.close();
    }
    
    /**
     * プロパティベースの条件付きBeanをデモンストレーション
     */
    private static void demonstratePropertyBasedCondition() {
        System.out.println("\n----- プロパティベースの条件付きBean -----");
        
        // 高度な機能が無効な場合
        System.out.println("1. 高度な機能が無効:");
        AnnotationConfigApplicationContext basicContext = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment env1 = basicContext.getEnvironment();
        Map<String, Object> props1 = new HashMap<>();
        props1.put("app.feature.advanced", "false");
        env1.getPropertySources().addFirst(new MapPropertySource("programmaticProperties", props1));
        basicContext.register(AdvancedFeatureService.class, BasicFeatureService.class);
        basicContext.refresh();
        
        // 機能サービスを取得して使用
        FeatureService basicService = basicContext.getBean(FeatureService.class);
        System.out.println("機能の説明: " + basicService.getFeatureDescription());
        System.out.println("実行結果: " + basicService.executeFeature());
        basicContext.close();
        
        // 高度な機能が有効な場合
        System.out.println("\n2. 高度な機能が有効:");
        AnnotationConfigApplicationContext advancedContext = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment env2 = advancedContext.getEnvironment();
        Map<String, Object> props2 = new HashMap<>();
        props2.put("app.feature.advanced", "true");
        env2.getPropertySources().addFirst(new MapPropertySource("programmaticProperties", props2));
        advancedContext.register(AdvancedFeatureService.class, BasicFeatureService.class);
        advancedContext.refresh();
        
        // 機能サービスを取得して使用
        FeatureService advancedService = advancedContext.getBean(FeatureService.class);
        System.out.println("機能の説明: " + advancedService.getFeatureDescription());
        System.out.println("実行結果: " + advancedService.executeFeature());
        advancedContext.close();
    }
    
    /**
     * カスタム条件のデモンストレーション
     */
    private static void demonstrateCustomCondition() {
        System.out.println("\n----- カスタム条件 -----");
        
        // 標準メモリ設定の場合
        System.out.println("1. 標準メモリ設定:");
        AnnotationConfigApplicationContext standardContext = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment env1 = standardContext.getEnvironment();
        Map<String, Object> props1 = new HashMap<>();
        props1.put("app.memory", "standard");
        env1.getPropertySources().addFirst(new MapPropertySource("memoryProperties", props1));
        standardContext.register(ConditionConfig.class);
        standardContext.refresh();
        
        // メッセージサービスを取得して使用
        ConditionConfig.MessageService standardService = standardContext.getBean(ConditionConfig.MessageService.class);
        System.out.println("メッセージ: " + standardService.getMessage());
        standardContext.close();
        
        // 高メモリ設定の場合
        System.out.println("\n2. 高メモリ設定:");
        AnnotationConfigApplicationContext highMemContext = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment env2 = highMemContext.getEnvironment();
        Map<String, Object> props2 = new HashMap<>();
        props2.put("app.memory", "high");
        env2.getPropertySources().addFirst(new MapPropertySource("memoryProperties", props2));
        highMemContext.register(ConditionConfig.class);
        highMemContext.refresh();
        
        // メッセージサービスを取得して使用
        ConditionConfig.MessageService highMemService = highMemContext.getBean(ConditionConfig.MessageService.class);
        System.out.println("メッセージ: " + highMemService.getMessage());
        highMemContext.close();
    }
}