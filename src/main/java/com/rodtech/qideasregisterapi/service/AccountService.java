package com.rodtech.qideasregisterapi.service;

import com.rodtech.qideasregisterapi.dto.AccountDTO;
import com.rodtech.qideasregisterapi.model.Account;

public interface AccountService {
    Account findById(String id);
    Account findByEmail(String email);
    Account create(AccountDTO account);
    Account update(AccountDTO account);
    void delete(String id);

}
