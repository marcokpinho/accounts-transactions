package com.marco.transactions.repository;

import com.marco.transactions.domain.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
