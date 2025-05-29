package hello.spring.transaction;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

/**
 * トランザクション管理のデモアプリケーション
 * このクラスは、Spring Transactionの宣言的およびプログラム的トランザクション管理の両方を示します。
 */
@SpringBootApplication
public class TransactionDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionDemoApplication.class, args);
    }

    /**
     * アプリケーション起動時に実行されるデモコード
     * 
     * @param annotationBankService 宣言的トランザクション管理を使用するサービス
     * @param programmaticBankService プログラム的トランザクション管理を使用するサービス
     * @param bankAccountRepository 銀行口座リポジトリ
     * @return CommandLineRunner インスタンス
     */
    @Bean
    public CommandLineRunner demo(
            AnnotationBankService annotationBankService,
            ProgrammaticBankService programmaticBankService,
            BankAccountRepository bankAccountRepository) {

        return args -> {
            System.out.println("=== トランザクションデモアプリケーションを開始します ===");

            // データベースをクリア
            bankAccountRepository.deleteAll();

            try {
                // 宣言的トランザクション管理を使用した口座作成
                System.out.println("\n--- 宣言的トランザクション管理を使用した口座作成 ---");
                BankAccount account1 = annotationBankService.createAccount("ACC-001", "山田太郎", new BigDecimal("1000.00"));
                BankAccount account2 = annotationBankService.createAccount("ACC-002", "佐藤花子", new BigDecimal("2000.00"));

                System.out.println("作成された口座: " + account1.getAccountNumber() + ", 残高: " + account1.getBalance());
                System.out.println("作成された口座: " + account2.getAccountNumber() + ", 残高: " + account2.getBalance());

                // プログラム的トランザクション管理を使用した口座作成
                System.out.println("\n--- プログラム的トランザクション管理を使用した口座作成 ---");
                BankAccount account3 = programmaticBankService.createAccount("ACC-003", "鈴木一郎", new BigDecimal("3000.00"));
                BankAccount account4 = programmaticBankService.createAccount("ACC-004", "田中美咲", new BigDecimal("4000.00"));

                System.out.println("作成された口座: " + account3.getAccountNumber() + ", 残高: " + account3.getBalance());
                System.out.println("作成された口座: " + account4.getAccountNumber() + ", 残高: " + account4.getBalance());

                // 宣言的トランザクション管理を使用した送金
                System.out.println("\n--- 宣言的トランザクション管理を使用した送金 ---");
                try {
                    boolean success = annotationBankService.transfer("ACC-001", "ACC-002", new BigDecimal("500.00"));
                    System.out.println("送金結果: " + (success ? "成功" : "失敗"));

                    // 更新された残高を表示
                    bankAccountRepository.findByAccountNumber("ACC-001").ifPresent(
                            acc -> System.out.println("口座 ACC-001 の残高: " + acc.getBalance()));
                    bankAccountRepository.findByAccountNumber("ACC-002").ifPresent(
                            acc -> System.out.println("口座 ACC-002 の残高: " + acc.getBalance()));
                } catch (Exception e) {
                    System.out.println("送金中にエラーが発生しました: " + e.getMessage());
                }

                // プログラム的トランザクション管理を使用した送金
                System.out.println("\n--- プログラム的トランザクション管理を使用した送金 ---");
                try {
                    boolean success = programmaticBankService.transferWithTemplate("ACC-003", "ACC-004", new BigDecimal("1000.00"));
                    System.out.println("送金結果: " + (success ? "成功" : "失敗"));

                    // 更新された残高を表示
                    bankAccountRepository.findByAccountNumber("ACC-003").ifPresent(
                            acc -> System.out.println("口座 ACC-003 の残高: " + acc.getBalance()));
                    bankAccountRepository.findByAccountNumber("ACC-004").ifPresent(
                            acc -> System.out.println("口座 ACC-004 の残高: " + acc.getBalance()));
                } catch (Exception e) {
                    System.out.println("送金中にエラーが発生しました: " + e.getMessage());
                }

                // エラーケース: 残高不足
                System.out.println("\n--- エラーケース: 残高不足 ---");
                // 送金前の口座番号と残高を表示
                bankAccountRepository.findByAccountNumber("ACC-001").ifPresent(
                        acc -> System.out.println("送金前 口座: " + acc.getAccountNumber() + ", 残高: " + acc.getBalance()));
                bankAccountRepository.findByAccountNumber("ACC-002").ifPresent(
                        acc -> System.out.println("送金前 口座: " + acc.getAccountNumber() + ", 残高: " + acc.getBalance()));
                // 送金金額を表示
                System.out.println("送金金額: 10000.00");
                try {
                    annotationBankService.transfer("ACC-001", "ACC-002", new BigDecimal("10000.00"));
                } catch (InsufficientFundsException e) {
                    System.out.println("予想通りのエラー: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("予期しないエラー: " + e.getMessage());
                }

                // エラーケース: 存在しない口座
                System.out.println("\n--- エラーケース: 存在しない口座 ---");
                // 送金前の口座番号と残高を表示（存在する口座のみ）
                System.out.println("送金前 口座: ACC-999 は存在しません");
                bankAccountRepository.findByAccountNumber("ACC-001").ifPresent(
                        acc -> System.out.println("送金前 口座: " + acc.getAccountNumber() + ", 残高: " + acc.getBalance()));
                // 送金金額を表示
                System.out.println("送金金額: 100.00");
                try {
                    programmaticBankService.transferWithTemplate("ACC-999", "ACC-001", new BigDecimal("100.00"));
                } catch (AccountNotFoundException e) {
                    System.out.println("予想通りのエラー: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("予期しないエラー: " + e.getMessage());
                }

                // エラーケース: 無効な金額
                System.out.println("\n--- エラーケース: 無効な金額 ---");
                // 送金前の口座番号と残高を表示
                bankAccountRepository.findByAccountNumber("ACC-001").ifPresent(
                        acc -> System.out.println("送金前 口座: " + acc.getAccountNumber() + ", 残高: " + acc.getBalance()));
                bankAccountRepository.findByAccountNumber("ACC-002").ifPresent(
                        acc -> System.out.println("送金前 口座: " + acc.getAccountNumber() + ", 残高: " + acc.getBalance()));
                // 送金金額を表示
                System.out.println("送金金額: -100.00");
                try {
                    annotationBankService.transfer("ACC-001", "ACC-002", new BigDecimal("-100.00"));
                } catch (IllegalArgumentException e) {
                    System.out.println("予想通りのエラー: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("予期しないエラー: " + e.getMessage());
                }

                // カスタム分離レベルを使用した送金
                System.out.println("\n--- カスタム分離レベルを使用した送金 ---");
                try {
                    boolean success = annotationBankService.transferWithCustomIsolation("ACC-001", "ACC-002", new BigDecimal("100.00"));
                    System.out.println("送金結果: " + (success ? "成功" : "失敗"));

                    // 更新された残高を表示
                    bankAccountRepository.findByAccountNumber("ACC-001").ifPresent(
                            acc -> System.out.println("口座 ACC-001 の残高: " + acc.getBalance()));
                    bankAccountRepository.findByAccountNumber("ACC-002").ifPresent(
                            acc -> System.out.println("口座 ACC-002 の残高: " + acc.getBalance()));
                } catch (Exception e) {
                    System.out.println("送金中にエラーが発生しました: " + e.getMessage());
                }

                // PlatformTransactionManagerを使用した送金
                System.out.println("\n--- PlatformTransactionManagerを使用した送金 ---");
                try {
                    boolean success = programmaticBankService.transferWithManager("ACC-003", "ACC-004", new BigDecimal("500.00"));
                    System.out.println("送金結果: " + (success ? "成功" : "失敗"));

                    // 更新された残高を表示
                    bankAccountRepository.findByAccountNumber("ACC-003").ifPresent(
                            acc -> System.out.println("口座 ACC-003 の残高: " + acc.getBalance()));
                    bankAccountRepository.findByAccountNumber("ACC-004").ifPresent(
                            acc -> System.out.println("口座 ACC-004 の残高: " + acc.getBalance()));
                } catch (Exception e) {
                    System.out.println("送金中にエラーが発生しました: " + e.getMessage());
                }

            } catch (Exception e) {
                System.out.println("デモ実行中にエラーが発生しました: " + e.getMessage());
                e.printStackTrace();
            }

            System.out.println("\n=== トランザクションデモアプリケーションを終了します ===");
        };
    }
}
