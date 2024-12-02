package com.linkedin.backend.features.authentication.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthenticationRequestBody {


    @NotBlank(message = "Email should not be blank")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;

}
