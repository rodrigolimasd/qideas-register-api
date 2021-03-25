package com.rodtech.qideasregisterapi.client;

import com.rodtech.qideasregisterapi.dto.UserAuthDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:8091/v1/users", name = "auth")
public interface AuthClient {

    @PostMapping
    ResponseEntity<?> create(@RequestBody UserAuthDTO userAuthDTO);

}
