package hello.spring.condition;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * 本番環境用のデータベースサービス実装
 */
@Service
@Profile("production") // 本番環境プロファイルの場合にのみ有効
public class ProdDatabaseService implements DatabaseService {

    @Override
    public String getConnectionInfo() {
        return "本番環境データベース - Host: prod-db.example.com, Port: 5432, Database: prod_db";
    }

    @Override
    public String connect() {
        // 本番環境データベースへの接続をシミュレート
        System.out.println("本番環境データベースに接続しています...");
        return "本番環境データベースに接続しました。高可用性モードが有効です。";
    }
}