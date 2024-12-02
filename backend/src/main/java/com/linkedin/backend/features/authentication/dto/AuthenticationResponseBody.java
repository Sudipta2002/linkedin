package com.linkedin.backend.features.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticationResponseBody {

    private final String token;
    private final String message;



}
