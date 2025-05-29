package hello.spring.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Service demonstrating programmatic transaction management for bank operations.
 */
@Service
public class ProgrammaticBankService {

    private final BankAccountRepository bankAccountRepository;
    private final PlatformTransactionManager transactionManager;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public ProgrammaticBankService(
            BankAccountRepository bankAccountRepository,
            PlatformTransactionManager transactionManager,
            TransactionTemplate transactionTemplate) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionManager = transactionManager;
        this.transactionTemplate = transactionTemplate;
    }

    /**
     * Create a new bank account using TransactionTemplate.
     */
    public BankAccount createAccount(String accountNumber, String ownerName, BigDecimal initialBalance) {
        return transactionTemplate.execute(status -> {
            try {
                BankAccount account = new BankAccount(accountNumber, ownerName, initialBalance);
                return bankAccountRepository.save(account);
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
        });
    }

    /**
     * Transfer money between accounts using TransactionTemplate.
     * This is the preferred way for programmatic transaction management.
     * 
     * TransactionTemplateを使用した送金処理
     * これはプログラム的トランザクション管理の推奨方法です
     * 
     * @param fromAccountNumber The account number to transfer from (送金元口座番号)
     * @param toAccountNumber The account number to transfer to (送金先口座番号)
     * @param amount The amount to transfer (送金額)
     * @return true if the transfer was successful
     * @throws InsufficientFundsException if the source account has insufficient funds
     * @throws AccountNotFoundException if either account is not found
     * @throws IllegalArgumentException if the amount is not positive or account numbers are invalid
     */
    public boolean transferWithTemplate(String fromAccountNumber, String toAccountNumber, BigDecimal amount) 
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

        try {
            Boolean result = transactionTemplate.execute(status -> {
                try {
                    // Find source account (送金元口座を検索)
                    Optional<BankAccount> fromAccountOpt = bankAccountRepository.findByAccountNumber(fromAccountNumber);
                    if (fromAccountOpt.isEmpty()) {
                        throw new RuntimeException(new AccountNotFoundException("Source account not found: " + fromAccountNumber));
                    }
                    BankAccount fromAccount = fromAccountOpt.get();

                    // Find destination account (送金先口座を検索)
                    Optional<BankAccount> toAccountOpt = bankAccountRepository.findByAccountNumber(toAccountNumber);
                    if (toAccountOpt.isEmpty()) {
                        throw new RuntimeException(new AccountNotFoundException("Destination account not found: " + toAccountNumber));
                    }
                    BankAccount toAccount = toAccountOpt.get();

                    try {
                        // Withdraw from source account (送金元口座から引き出し)
                        fromAccount.withdraw(amount);

                        // Deposit to destination account (送金先口座に入金)
                        toAccount.deposit(amount);

                        // Save both accounts (両方の口座を保存)
                        bankAccountRepository.save(fromAccount);
                        bankAccountRepository.save(toAccount);

                        return true;
                    } catch (InsufficientFundsException e) {
                        throw new RuntimeException(e);
                    }
                } catch (RuntimeException e) {
                    // Set transaction rollback (トランザクションをロールバック)
                    status.setRollbackOnly();
                    throw e;
                }
            });

            return result != null && result;
        } catch (RuntimeException e) {
            // Unwrap the cause if it's one of our checked exceptions
            if (e.getCause() instanceof InsufficientFundsException) {
                throw (InsufficientFundsException) e.getCause();
            } else if (e.getCause() instanceof AccountNotFoundException) {
                throw (AccountNotFoundException) e.getCause();
            }
            throw e;
        }
    }

    /**
     * Transfer money between accounts using PlatformTransactionManager directly.
     * This gives more control but is more verbose.
     * 
     * PlatformTransactionManagerを直接使用した送金処理
     * より詳細な制御が可能ですが、より冗長です
     * 
     * @param fromAccountNumber The account number to transfer from (送金元口座番号)
     * @param toAccountNumber The account number to transfer to (送金先口座番号)
     * @param amount The amount to transfer (送金額)
     * @return true if the transfer was successful
     * @throws InsufficientFundsException if the source account has insufficient funds
     * @throws AccountNotFoundException if either account is not found
     * @throws IllegalArgumentException if the amount is not positive or account numbers are invalid
     */
    public boolean transferWithManager(String fromAccountNumber, String toAccountNumber, BigDecimal amount) 
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

        // Define transaction properties (トランザクションプロパティを定義)
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        definition.setTimeout(30); // 30 seconds

        // Begin transaction (トランザクション開始)
        TransactionStatus status = transactionManager.getTransaction(definition);

        try {
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

            // Commit transaction if everything is successful (すべて成功したらコミット)
            transactionManager.commit(status);
            return true;
        } catch (Exception e) {
            // Rollback transaction in case of error (エラーが発生した場合はロールバック)
            transactionManager.rollback(status);

            // Re-throw the exception (例外を再スロー)
            if (e instanceof InsufficientFundsException) {
                throw (InsufficientFundsException) e;
            } else if (e instanceof AccountNotFoundException) {
                throw (AccountNotFoundException) e;
            } else {
                throw new RuntimeException("Error during transfer: " + e.getMessage(), e);
            }
        }
    }
}
