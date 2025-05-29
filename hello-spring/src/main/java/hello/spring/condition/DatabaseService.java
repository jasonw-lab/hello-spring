package hello.spring.condition;

/**
 * データベースサービスのインターフェース
 */
public interface DatabaseService {
    
    /**
     * データベース接続情報を取得
     * @return 接続情報の文字列
     */
    String getConnectionInfo();
    
    /**
     * データベースに接続する
     * @return 接続結果のメッセージ
     */
    String connect();
}