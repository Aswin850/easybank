package com.easybanks.accounts.repositories;

import com.easybanks.accounts.entities.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepo extends JpaRepository<Accounts,Long> {
    void deleteByCustomerId(Long customerId);
    Optional<Accounts> findByCustomerId(Long customerId);
}
