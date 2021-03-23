package com.rodtech.qideasregisterapi.service;

import com.rodtech.qideasregisterapi.model.User;

public interface UserService {
    User findById(String id);
    User findByEmail(String email);
    User create(User user);
    User update(User user);
    void delete(String id);

}
