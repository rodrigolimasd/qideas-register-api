package com.rodtech.qideasregisterapi.repository;

import com.rodtech.qideasregisterapi.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    Optional<Account> findFirstByEmail(String email);
}
