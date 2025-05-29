package hello.spring.condition;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * 開発環境用のデータベースサービス実装
 */
@Service
@Profile("development") // 開発環境プロファイルの場合にのみ有効
public class DevDatabaseService implements DatabaseService {

    @Override
    public String getConnectionInfo() {
        return "開発環境データベース - Host: localhost, Port: 3306, Database: dev_db";
    }

    @Override
    public String connect() {
        // 開発環境データベースへの接続をシミュレート
        System.out.println("開発環境データベースに接続しています...");
        return "開発環境データベースに接続しました。デバッグモードが有効です。";
    }
}