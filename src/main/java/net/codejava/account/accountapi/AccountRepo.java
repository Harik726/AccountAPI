package net.codejava.account.accountapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepo extends JpaRepository<Account,String> {
    @Query("update Account a set a.balance = a.balance+ ?1 where a.id = ?2")
    @Modifying
    void updateBalance(float balance, String id);
}
