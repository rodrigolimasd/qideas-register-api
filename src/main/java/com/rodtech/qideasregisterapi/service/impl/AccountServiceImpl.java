package com.rodtech.qideasregisterapi.service.impl;

import com.rodtech.qideasregisterapi.client.AuthClient;
import com.rodtech.qideasregisterapi.dto.AccountDTO;
import com.rodtech.qideasregisterapi.dto.UserAuthDTO;
import com.rodtech.qideasregisterapi.exception.RegisteredEmailException;
import com.rodtech.qideasregisterapi.exception.AccountNotFoundException;
import com.rodtech.qideasregisterapi.model.Account;
import com.rodtech.qideasregisterapi.repository.AccountRepository;
import com.rodtech.qideasregisterapi.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private static final String EMAIL_REGISTERED = "E-mail already registered";

    private final AccountRepository accountRepository;
    private final AuthClient authClient;

    public AccountServiceImpl(AccountRepository accountRepository, AuthClient authClient) {
        this.accountRepository = accountRepository;
        this.authClient = authClient;
    }

    @Override
    public Account findById(String id) {
        return findAccountById(id);
    }

    @Override
    public Account findByEmail(String email) {
        return findAccountByEmail(email).orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    @Transactional
    @Override
    public Account create(AccountDTO accountDTO) {
        Account account = Account.builder()
                .email(accountDTO.getEmail())
                .name(accountDTO.getName())
                .birthday(accountDTO.getBirthday())
                .build();

        saveAccountValidation(account);
        account = accountRepository.save(account);

        authClient.create(UserAuthDTO.builder()
                .email(accountDTO.getEmail())
                .username(accountDTO.getUsername())
                .password(accountDTO.getPassword())
                .build());

        return account;
    }

    @Override
    public Account update(AccountDTO account) {
        Account accountDb = findAccountById(account.getId());
        //TODO: more information to update
        accountDb.setName(account.getName());
        accountDb.setBirthday(account.getBirthday());

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
