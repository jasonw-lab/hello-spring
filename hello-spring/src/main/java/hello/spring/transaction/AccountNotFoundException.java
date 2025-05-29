package hello.spring.transaction;

/**
 * 銀行口座が見つからない場合にスローされる例外
 */
public class AccountNotFoundException extends Exception {

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
