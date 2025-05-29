package hello.spring.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * メール送信サービス
 * 非同期処理を使用して通知メールを送信する
 */
@Service
public class EmailService {

    /**
     * 非同期でメールを送信する
     * @param to 宛先メールアドレス
     * @param subject 件名
     * @param content 本文
     */
    @Async
    public void sendNotificationEmail(String to, String subject, String content) {
        try {
            // メール送信処理をシミュレート（実際のメール送信ロジックに置き換える）
            System.out.println("メール送信開始: " + to);
            Thread.sleep(3000); // 3秒間の処理時間をシミュレート
            System.out.println("メール送信完了: " + to + ", 件名: " + subject);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("メール送信中に中断されました: " + e.getMessage());
        }
    }

    /**
     * 非同期でメールを送信し、結果を返す
     * @param to 宛先メールアドレス
     * @param subject 件名
     * @param content 本文
     * @return 送信結果を含むCompletableFuture
     */
    @Async
    public CompletableFuture<String> sendNotificationEmailWithResult(String to, String subject, String content) {
        try {
            // メール送信処理をシミュレート
            System.out.println("メール送信開始（結果付き）: " + to);
            Thread.sleep(3000); // 3秒間の処理時間をシミュレート
            String result = "メール送信成功: " + to + ", 件名: " + subject;
            System.out.println(result);
            return CompletableFuture.completedFuture(result);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("メール送信中に中断されました: " + e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }
}