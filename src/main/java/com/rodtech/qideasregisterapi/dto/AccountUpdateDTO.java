package com.rodtech.qideasregisterapi.dto;

import com.rodtech.qideasregisterapi.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateDTO extends BaseModel {

    @NotEmpty
    private String name;

    private LocalDate birthday;
}
