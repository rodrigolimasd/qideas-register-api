package com.rodtech.qideasregisterapi.service.impl;

import com.rodtech.qideasregisterapi.client.AuthClient;
import com.rodtech.qideasregisterapi.dto.AccountDTO;
import com.rodtech.qideasregisterapi.dto.UserAuthDTO;
import com.rodtech.qideasregisterapi.exception.RegisteredEmailException;
import com.rodtech.qideasregisterapi.exception.AccountNotFoundException;
import com.rodtech.qideasregisterapi.exception.RegisteredUsernameException;
import com.rodtech.qideasregisterapi.model.Account;
import com.rodtech.qideasregisterapi.repository.AccountRepository;
import com.rodtech.qideasregisterapi.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

@Log4j2
@Service
public class AccountServiceImpl implements AccountService {

    private static final String EMAIL_REGISTERED = "E-mail already registered";
    private static final String USERNAME_REGISTERED = "Username already registered";

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
                .username(accountDTO.getUsername())
                .birthday(accountDTO.getBirthday())
                .build();

        log.info("validation account entity");
        saveAccountValidation(account);
        UserAuthDTO userAuthDTO = UserAuthDTO.builder()
                .email(accountDTO.getEmail())
                .username(accountDTO.getUsername())
                .password(accountDTO.getPassword())
                .build();

        log.info("creating user {} ", userAuthDTO);
        log.info("integration qideas-auth-api ");
        authClient.create(userAuthDTO);
        log.info("integrated qideas-auth-api success");

        account = accountRepository.save(account);

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
    public void deleteAccount(Principal principal){
        Account account = findByUserName(principal.getName());

        authClient.delete(account.getEmail());

        accountRepository.delete(account);
    }

    private void saveAccountValidation(Account account){
        findAccountByEmail(account.getEmail())
                .ifPresent(accountDb -> {
                    if(account.getId()==null || !account.getId().equals(accountDb.getId()))
                        throw new RegisteredEmailException(EMAIL_REGISTERED);
                });
        findAccountByUserName(account.getUsername())
                .ifPresent(accountDb -> {
                    if(account.getId()==null || !account.getId().equals(accountDb.getId()))
                        throw new RegisteredUsernameException(USERNAME_REGISTERED);
                });
    }

    private Account findAccountById(String id){
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    private Account findByUserName(String userName){
        return findAccountByUserName(userName)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    private Optional<Account> findAccountByEmail(String email){
        return accountRepository.findFirstByEmail(email);
    }

    private Optional<Account> findAccountByUserName(String userName){
        return accountRepository.findFirstByUsername(userName);
    }
}
