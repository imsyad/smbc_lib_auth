package com.smbc.library.auth_service.dto;

import java.io.Serial;
import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 8903219805233154L;

    @NotEmpty(message = "Username cannot be empty")
    @NotBlank(message = "Username cannot contain only whitespace")
    @Size(min = 4, max = 255, message = "Username length must be between 4 and 255 characters")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @NotEmpty(message = "Password cannot contain only whitespace")
    @Size(min = 6, max = 255, message = "Password length must be between 6 and 255 characters")
    private String password;
}
