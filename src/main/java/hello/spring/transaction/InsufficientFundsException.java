package hello.spring.transaction;

/**
 * 口座の残高が引き出しや送金に不足している場合にスローされる例外
 */
public class InsufficientFundsException extends Exception {

    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
