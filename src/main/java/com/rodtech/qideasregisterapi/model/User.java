package com.rodtech.qideasregisterapi.model;

import com.rodtech.qideasregisterapi.annotations.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel {

    @NotEmpty
    private String name;

    @Email
    @NotEmpty
    private String email;

    @ValidPassword
    private String password;
}
