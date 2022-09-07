package com.stdio.esm.repository;

import com.stdio.esm.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsernameAndDeleteFlagIsFalse(String username);

    Optional<Account> findById(Long accountId);
    Optional<Account> findByUsername(String username);

    void deleteByUsername(String username);

    boolean existsAccountById(Long accountId);
}
