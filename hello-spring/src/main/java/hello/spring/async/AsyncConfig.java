package hello.spring.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 非同期処理の設定クラス
 */
@Configuration
@EnableAsync // 非同期処理を有効にする
@ComponentScan("hello.spring.async") // hello.spring.asyncパッケージのコンポーネントをスキャン
public class AsyncConfig {

    /**
     * 非同期処理用のExecutorを設定
     * @return 設定されたExecutor
     */
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // コアプールサイズ（常に維持されるスレッド数）
        executor.setCorePoolSize(2);
        // 最大プールサイズ（同時に実行できる最大スレッド数）
        executor.setMaxPoolSize(5);
        // キューの容量（スレッドがビジー状態の場合にタスクを保持するキューのサイズ）
        executor.setQueueCapacity(10);
        // スレッド名のプレフィックス
        executor.setThreadNamePrefix("Async-");
        // 初期化
        executor.initialize();
        return executor;
    }
}
