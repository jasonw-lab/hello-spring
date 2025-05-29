package hello.spring.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * BankAccountエンティティのリポジトリ
 */
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    /**
     * 口座番号で銀行口座を検索する
     * 
     * @param accountNumber 検索する口座番号
     * @return 銀行口座が見つかった場合はOptionalに含まれる
     */
    Optional<BankAccount> findByAccountNumber(String accountNumber);
}
