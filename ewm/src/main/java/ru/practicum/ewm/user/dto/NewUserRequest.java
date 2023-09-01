package ru.practicum.ewm.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewUserRequest {

    @Email
    @NotBlank(message = "Specify the email")
    @Size(min = 6, max = 254, message = "Allowable size of email 6-254")
    private String email;
    @NotBlank(message = "Specify the name")
    @Size(min = 2, max = 250, message = "Allowable size of name 2-250")
    private String name;
}
