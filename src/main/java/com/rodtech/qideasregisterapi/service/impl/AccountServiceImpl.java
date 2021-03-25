package com.rodtech.qideasregisterapi.service.impl;

import com.rodtech.qideasregisterapi.dto.AccountDTO;
import com.rodtech.qideasregisterapi.exception.RegisteredEmailException;
import com.rodtech.qideasregisterapi.exception.AccountNotFoundException;
import com.rodtech.qideasregisterapi.model.Account;
import com.rodtech.qideasregisterapi.repository.AccountRepository;
import com.rodtech.qideasregisterapi.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class AccountServiceImpl implements AccountService {

    private static final String EMAIL_REGISTERED = "E-mail already registered";

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account findById(String id) {
        return findAccountById(id);
    }

    @Override
    public Account findByEmail(String email) {
        return findAccountByEmail(email).orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    @Override
    public Account create(AccountDTO accountDTO) {
        Account account = Account.builder()
                .email(accountDTO.getEmail())
                .name(accountDTO.getName())
                .birthday(accountDTO.getBirthday())
                .build();

        saveAccountValidation(account);
        account = accountRepository.save(account);

        return account;
    }

    @Override
    public Account update(AccountDTO account) {
        Account accountDb = findAccountById(account.getId());
        //TODO: more information to update
        accountDb.setName(account.getEmail());

        saveAccountValidation(accountDb);
        accountRepository.save(accountDb);
        return null;
    }

    @Override
    public void delete(String id){
        Account account = findAccountById(id);
        accountRepository.delete(account);
    }

    private void saveAccountValidation(Account account){
        findAccountByEmail(account.getEmail())
                .ifPresent(accountDb -> {
                    if(account.getId()==null || !account.getId().equals(accountDb.getId()))
                        throw new RegisteredEmailException(EMAIL_REGISTERED);
                });
    }

    private Account findAccountById(String id){
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    private Optional<Account> findAccountByEmail(String email){
        return accountRepository.findFirstByEmail(email);
    }
}
