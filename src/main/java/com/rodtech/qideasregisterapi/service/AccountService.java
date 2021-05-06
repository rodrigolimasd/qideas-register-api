package com.rodtech.qideasregisterapi.service;

import com.rodtech.qideasregisterapi.dto.AccountDTO;
import com.rodtech.qideasregisterapi.dto.AccountUpdateDTO;
import com.rodtech.qideasregisterapi.model.Account;

import java.security.Principal;

public interface AccountService {
    Account findByEmail(String email);
    Account create(AccountDTO account);
    Account update(AccountUpdateDTO account, Principal principal);
    void deleteAccount(Principal principal);

}
