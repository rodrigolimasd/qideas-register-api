package com.rodtech.qideasregisterapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthDTO implements Serializable {
    private String id;
    private String email;
    private String username;
    private String password;
}
