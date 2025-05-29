package hello.spring.async;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Spring Asyncのデモアプリケーション
 * 非同期メール送信機能を実演する
 */
public class AsyncDemoApplication {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Spring コンテキストを初期化
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AsyncConfig.class);
        
        // EmailServiceのBeanを取得
        EmailService emailService = context.getBean(EmailService.class);
        
        System.out.println("===== 非同期メール送信デモ開始 =====");
        
        // 現在の時間を記録
        long startTime = System.currentTimeMillis();
        
        System.out.println("1. 複数のメールを非同期で送信（戻り値なし）");
        // 複数のメールを非同期で送信
        emailService.sendNotificationEmail("user1@example.com", "お知らせ1", "これはお知らせメール1です。");
        emailService.sendNotificationEmail("user2@example.com", "お知らせ2", "これはお知らせメール2です。");
        emailService.sendNotificationEmail("user3@example.com", "お知らせ3", "これはお知らせメール3です。");
        
        System.out.println("メール送信リクエストが完了しました。バックグラウンドで処理が続行されます。");
        System.out.println("経過時間: " + (System.currentTimeMillis() - startTime) + "ms");
        
        // 少し待機して非同期処理の実行を確認
        Thread.sleep(1000);
        System.out.println("メインスレッドは他の処理を続行できます。");
        
        System.out.println("\n2. 結果を返す非同期メール送信");
        // 結果を返す非同期メール送信
        CompletableFuture<String> future = emailService.sendNotificationEmailWithResult(
                "admin@example.com", "重要なお知らせ", "これは重要なお知らせです。");
        
        System.out.println("結果を返す非同期メール送信リクエストが完了しました。結果を待機します...");
        
        // 非同期処理の結果を取得（ブロッキング）
        String result = future.get();
        System.out.println("非同期処理の結果: " + result);
        System.out.println("合計経過時間: " + (System.currentTimeMillis() - startTime) + "ms");
        
        // コンテキストを閉じる
        context.close();
        
        System.out.println("===== 非同期メール送信デモ終了 =====");
    }
}