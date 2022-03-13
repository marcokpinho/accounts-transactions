package com.marco.transactions.repository;

import com.marco.transactions.domain.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findByDocumentNumber(String documentNumber);
}
