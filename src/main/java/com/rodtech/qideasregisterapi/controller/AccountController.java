package com.rodtech.qideasregisterapi.controller;

import com.rodtech.qideasregisterapi.dto.AccountDTO;
import com.rodtech.qideasregisterapi.dto.AccountUpdateDTO;
import com.rodtech.qideasregisterapi.model.Account;
import com.rodtech.qideasregisterapi.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

@Log4j2
@RestController
@RequestMapping("/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/info")
    public ResponseEntity<?> accountInfo(Principal principal){
        log.info("get info account {} ", principal);
        Account result = accountService.findByEmail(principal.getName());
        log.info("account info {} ", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid AccountDTO entity){
        log.info("creating account {} ", entity);
        Account newAccount = accountService.create(entity);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(newAccount.getId())
                .toUri();
        log.info("account created {} ", newAccount);
        return ResponseEntity.created(uri).body(newAccount);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid AccountUpdateDTO entity, Principal principal){
        log.info("updating account {} ", entity);
        accountService.update(entity, principal);
        log.info("account updated {} ", entity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<?> delete(Principal principal){
        log.info("delete account {} ", principal);
        accountService.deleteAccount(principal);
        log.info("account deleted {} ", principal);
        return ResponseEntity.noContent().build();
    }
}
