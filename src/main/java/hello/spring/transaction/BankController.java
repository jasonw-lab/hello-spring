package hello.spring.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * トランザクション管理を示す銀行操作のためのコントローラ
 */
@RestController
@RequestMapping("/api/bank")
public class BankController {

    private final ProgrammaticBankService programmaticBankService;
    private final AnnotationBankService annotationBankService;

    @Autowired
    public BankController(
            ProgrammaticBankService programmaticBankService,
            AnnotationBankService annotationBankService) {
        this.programmaticBankService = programmaticBankService;
        this.annotationBankService = annotationBankService;
    }

    // Account creation endpoints

    @PostMapping("/accounts/programmatic")
    public ResponseEntity<?> createAccountProgrammatic(@RequestBody Map<String, String> request) {
        String accountNumber = request.get("accountNumber");
        String ownerName = request.get("ownerName");
        BigDecimal initialBalance;

        try {
            initialBalance = new BigDecimal(request.get("initialBalance"));
            if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Invalid initial balance",
                    "message", "Initial balance cannot be negative"
                ));
            }
        } catch (NumberFormatException | NullPointerException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Invalid initial balance",
                "message", "Initial balance must be a valid number"
            ));
        }

        if (accountNumber == null || accountNumber.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Invalid account number",
                "message", "Account number cannot be empty"
            ));
        }

        if (ownerName == null || ownerName.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Invalid owner name",
                "message", "Owner name cannot be empty"
            ));
        }

        try {
            BankAccount account = programmaticBankService.createAccount(accountNumber, ownerName, initialBalance);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Account creation failed",
                "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/accounts/annotation")
    public ResponseEntity<?> createAccountAnnotation(@RequestBody Map<String, String> request) {
        String accountNumber = request.get("accountNumber");
        String ownerName = request.get("ownerName");
        BigDecimal initialBalance;

        try {
            initialBalance = new BigDecimal(request.get("initialBalance"));
            if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Invalid initial balance",
                    "message", "Initial balance cannot be negative"
                ));
            }
        } catch (NumberFormatException | NullPointerException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Invalid initial balance",
                "message", "Initial balance must be a valid number"
            ));
        }

        if (accountNumber == null || accountNumber.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Invalid account number",
                "message", "Account number cannot be empty"
            ));
        }

        if (ownerName == null || ownerName.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Invalid owner name",
                "message", "Owner name cannot be empty"
            ));
        }

        try {
            BankAccount account = annotationBankService.createAccount(accountNumber, ownerName, initialBalance);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Account creation failed",
                "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<BankAccount>> getAllAccounts() {
        List<BankAccount> accounts = annotationBankService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    // Transfer endpoints - Programmatic approach

    @PostMapping("/transfer/programmatic/template")
    public ResponseEntity<?> transferWithTemplate(@RequestBody Map<String, String> request) {
        String fromAccountNumber = request.get("fromAccountNumber");
        String toAccountNumber = request.get("toAccountNumber");
        BigDecimal amount;

        try {
            amount = new BigDecimal(request.get("amount"));
        } catch (NumberFormatException | NullPointerException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Invalid amount",
                "message", "Amount must be a valid number"
            ));
        }

        try {
            boolean success = programmaticBankService.transferWithTemplate(fromAccountNumber, toAccountNumber, amount);
            return ResponseEntity.ok(Map.of(
                "success", success,
                "message", "Transfer completed successfully"
            ));
        } catch (InsufficientFundsException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Insufficient funds",
                "message", e.getMessage()
            ));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Account not found",
                "message", e.getMessage()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Invalid input",
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Transfer failed",
                "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/transfer/programmatic/manager")
    public ResponseEntity<?> transferWithManager(@RequestBody Map<String, String> request) {
        String fromAccountNumber = request.get("fromAccountNumber");
        String toAccountNumber = request.get("toAccountNumber");
        BigDecimal amount;

        try {
            amount = new BigDecimal(request.get("amount"));
        } catch (NumberFormatException | NullPointerException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Invalid amount",
                "message", "Amount must be a valid number"
            ));
        }

        try {
            boolean success = programmaticBankService.transferWithManager(fromAccountNumber, toAccountNumber, amount);
            return ResponseEntity.ok(Map.of(
                "success", success,
                "message", "Transfer completed successfully"
            ));
        } catch (InsufficientFundsException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Insufficient funds",
                "message", e.getMessage()
            ));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Account not found",
                "message", e.getMessage()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Invalid input",
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Transfer failed",
                "message", e.getMessage()
            ));
        }
    }

    // Transfer endpoints - Annotation approach

    @PostMapping("/transfer/annotation/basic")
    public ResponseEntity<?> transferBasic(@RequestBody Map<String, String> request) {
        String fromAccountNumber = request.get("fromAccountNumber");
        String toAccountNumber = request.get("toAccountNumber");
        BigDecimal amount;

        try {
            amount = new BigDecimal(request.get("amount"));
        } catch (NumberFormatException | NullPointerException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Invalid amount",
                "message", "Amount must be a valid number"
            ));
        }

        try {
            boolean success = annotationBankService.transfer(fromAccountNumber, toAccountNumber, amount);
            return ResponseEntity.ok(Map.of(
                "success", success,
                "message", "Transfer completed successfully"
            ));
        } catch (InsufficientFundsException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Insufficient funds",
                "message", e.getMessage()
            ));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Account not found",
                "message", e.getMessage()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Invalid input",
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Transfer failed",
                "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/transfer/annotation/custom-isolation")
    public ResponseEntity<?> transferWithCustomIsolation(@RequestBody Map<String, String> request) {
        String fromAccountNumber = request.get("fromAccountNumber");
        String toAccountNumber = request.get("toAccountNumber");
        BigDecimal amount;

        try {
            amount = new BigDecimal(request.get("amount"));
        } catch (NumberFormatException | NullPointerException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Invalid amount",
                "message", "Amount must be a valid number"
            ));
        }

        try {
            boolean success = annotationBankService.transferWithCustomIsolation(fromAccountNumber, toAccountNumber, amount);
            return ResponseEntity.ok(Map.of(
                "success", success,
                "message", "Transfer completed successfully with custom isolation"
            ));
        } catch (InsufficientFundsException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Insufficient funds",
                "message", e.getMessage()
            ));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Account not found",
                "message", e.getMessage()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Invalid input",
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Transfer failed",
                "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/transfer/annotation/rollback-rules")
    public ResponseEntity<?> transferWithRollbackRules(@RequestBody Map<String, String> request) {
        String fromAccountNumber = request.get("fromAccountNumber");
        String toAccountNumber = request.get("toAccountNumber");
        BigDecimal amount;

        try {
            amount = new BigDecimal(request.get("amount"));
        } catch (NumberFormatException | NullPointerException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Invalid amount",
                "message", "Amount must be a valid number"
            ));
        }

        try {
            boolean success = annotationBankService.transferWithRollbackRules(fromAccountNumber, toAccountNumber, amount);
            return ResponseEntity.ok(Map.of(
                "success", success,
                "message", "Transfer completed successfully with custom rollback rules"
            ));
        } catch (InsufficientFundsException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Insufficient funds",
                "message", e.getMessage()
            ));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Account not found",
                "message", e.getMessage()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Invalid input",
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Transfer failed",
                "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/transfer/annotation/timeout")
    public ResponseEntity<?> transferWithTimeout(@RequestBody Map<String, String> request) {
        String fromAccountNumber = request.get("fromAccountNumber");
        String toAccountNumber = request.get("toAccountNumber");
        BigDecimal amount;

        try {
            amount = new BigDecimal(request.get("amount"));
        } catch (NumberFormatException | NullPointerException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Invalid amount",
                "message", "Amount must be a valid number"
            ));
        }

        try {
            boolean success = annotationBankService.transferWithTimeout(fromAccountNumber, toAccountNumber, amount);
            return ResponseEntity.ok(Map.of(
                "success", success,
                "message", "Transfer completed successfully with timeout"
            ));
        } catch (InsufficientFundsException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Insufficient funds",
                "message", e.getMessage()
            ));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Account not found",
                "message", e.getMessage()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Invalid input",
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Transfer failed",
                "message", e.getMessage()
            ));
        }
    }
}
