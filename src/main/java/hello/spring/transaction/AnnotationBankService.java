package hello.spring.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service demonstrating annotation-based transaction management for bank operations.
 */
@Service
public class AnnotationBankService {

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public AnnotationBankService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Create a new bank account with basic transaction settings.
     */
    @Transactional
    public BankAccount createAccount(String accountNumber, String ownerName, BigDecimal initialBalance) {
        BankAccount account = new BankAccount(accountNumber, ownerName, initialBalance);
        return bankAccountRepository.save(account);
    }

    /**
     * Get all bank accounts with read-only transaction.
     */
    @Transactional(readOnly = true)
    public List<BankAccount> getAllAccounts() {
        return bankAccountRepository.findAll();
    }

    /**
     * Basic transfer implementation with default transaction settings.
     * By default, the transaction will be rolled back for RuntimeExceptions.
     * 
     * 基本的な@Transactionalアノテーションを使用した送金処理
     * デフォルトでは、RuntimeExceptionが発生した場合にロールバックされます
     * 
     * @param fromAccountNumber The account number to transfer from (送金元口座番号)
     * @param toAccountNumber The account number to transfer to (送金先口座番号)
     * @param amount The amount to transfer (送金額)
     * @return true if the transfer was successful
     * @throws InsufficientFundsException if the source account has insufficient funds
     * @throws AccountNotFoundException if either account is not found
     * @throws IllegalArgumentException if the amount is not positive
     */
    @Transactional
    public boolean transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) 
            throws InsufficientFundsException, AccountNotFoundException {

        // Validate input parameters
        if (fromAccountNumber == null || fromAccountNumber.isEmpty()) {
            throw new IllegalArgumentException("Source account number cannot be empty");
        }

        if (toAccountNumber == null || toAccountNumber.isEmpty()) {
            throw new IllegalArgumentException("Destination account number cannot be empty");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        // Check if source and destination accounts are the same
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("Source and destination accounts cannot be the same");
        }

        // Find source account (送金元口座を検索)
        BankAccount fromAccount = bankAccountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Source account not found: " + fromAccountNumber));

        // Find destination account (送金先口座を検索)
        BankAccount toAccount = bankAccountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Destination account not found: " + toAccountNumber));

        // Withdraw from source account (送金元口座から引き出し)
        fromAccount.withdraw(amount);

        // Deposit to destination account (送金先口座に入金)
        toAccount.deposit(amount);

        // Save both accounts (両方の口座を保存)
        bankAccountRepository.save(fromAccount);
        bankAccountRepository.save(toAccount);

        return true;
    }

    /**
     * Transfer with custom isolation level and propagation behavior.
     * - ISOLATION_SERIALIZABLE: Highest isolation level, prevents dirty reads, non-repeatable reads, and phantom reads
     * - PROPAGATION_REQUIRES_NEW: Always creates a new transaction, suspending the current transaction if one exists
     * 
     * @param fromAccountNumber The account number to transfer from
     * @param toAccountNumber The account number to transfer to
     * @param amount The amount to transfer
     * @return true if the transfer was successful
     * @throws InsufficientFundsException if the source account has insufficient funds
     * @throws AccountNotFoundException if either account is not found
     */
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
    public boolean transferWithCustomIsolation(String fromAccountNumber, String toAccountNumber, BigDecimal amount) 
            throws InsufficientFundsException, AccountNotFoundException {

        return transfer(fromAccountNumber, toAccountNumber, amount);
    }

    /**
     * Transfer with rollback rules.
     * - rollbackFor: Specifies exception types that must cause rollback
     * - noRollbackFor: Specifies exception types that must not cause rollback
     * 
     * @param fromAccountNumber The account number to transfer from
     * @param toAccountNumber The account number to transfer to
     * @param amount The amount to transfer
     * @return true if the transfer was successful
     * @throws InsufficientFundsException if the source account has insufficient funds
     * @throws AccountNotFoundException if either account is not found
     */
    @Transactional(rollbackFor = {Exception.class}, noRollbackFor = {AccountNotFoundException.class})
    public boolean transferWithRollbackRules(String fromAccountNumber, String toAccountNumber, BigDecimal amount) 
            throws InsufficientFundsException, AccountNotFoundException {

        return transfer(fromAccountNumber, toAccountNumber, amount);
    }

    /**
     * Transfer with timeout.
     * The transaction will be rolled back if it takes longer than the specified timeout.
     * 
     * @param fromAccountNumber The account number to transfer from
     * @param toAccountNumber The account number to transfer to
     * @param amount The amount to transfer
     * @return true if the transfer was successful
     * @throws InsufficientFundsException if the source account has insufficient funds
     * @throws AccountNotFoundException if either account is not found
     */
    @Transactional(timeout = 30)
    public boolean transferWithTimeout(String fromAccountNumber, String toAccountNumber, BigDecimal amount) 
            throws InsufficientFundsException, AccountNotFoundException {

        return transfer(fromAccountNumber, toAccountNumber, amount);
    }
}
