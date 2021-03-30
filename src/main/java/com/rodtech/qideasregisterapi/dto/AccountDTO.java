package com.rodtech.qideasregisterapi.dto;

import com.rodtech.qideasregisterapi.annotations.ValidPassword;
import com.rodtech.qideasregisterapi.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO extends BaseModel {

    @NotEmpty
    private String name;

    private LocalDate birthday;

    @Email
    @NotEmpty
    private String email;

    @ValidPassword
    private String password;
}
