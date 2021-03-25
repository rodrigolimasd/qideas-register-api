package com.rodtech.qideasregisterapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Document(collection = "accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseModel {

    @NotEmpty
    private String name;

    private LocalDate birthday;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String username;
}
