package com.rodtech.qideasregisterapi.controller;

import com.rodtech.qideasregisterapi.dto.AccountDTO;
import com.rodtech.qideasregisterapi.model.Account;
import com.rodtech.qideasregisterapi.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        return ResponseEntity.ok(accountService.findById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email){
        return ResponseEntity.ok(accountService.findByEmail(email));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid AccountDTO entity){
        Account newAccount = accountService.create(entity);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(newAccount.getId())
                .toUri();

        return ResponseEntity.created(uri).body(newAccount);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid AccountDTO entity){
        accountService.update(entity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<?> delete(Principal principal){
        accountService.deleteAccount(principal);
        return ResponseEntity.noContent().build();
    }
}
