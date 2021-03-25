package com.rodtech.qideasregisterapi.client;

import com.rodtech.qideasregisterapi.dto.UserAuthDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:8091/v1/users", name = "auth-client", configuration = FeignAuthClientConfig.class)
public interface AuthClient {

    @PostMapping
    ResponseEntity<?> create(@RequestBody UserAuthDTO userAuthDTO);

    @DeleteMapping("/email/{email}")
    ResponseEntity<?> delete(@PathVariable String email);

}
