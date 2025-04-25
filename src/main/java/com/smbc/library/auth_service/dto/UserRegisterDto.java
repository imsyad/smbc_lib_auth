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
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 2141249124L;

    @NotEmpty(message = "Username cannot be empty")
    @NotBlank(message = "Username cannot contain only whitespace")
    @Size(min = 4, max = 255, message = "Username length must be between 4 and 255 characters")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @NotEmpty(message = "Password cannot contain only whitespace")
    @Size(min = 6, max = 255, message = "Password length must be between 6 and 255 characters")
    private String password;

    @NotEmpty(message = "Fullname cannot be empty")
    @NotBlank(message = "Fullname cannot contain only whitespace")
    @Size(min = 1, max = 255, message = "Fullname length must be between 1 and 255 characters")
    private String fullname;
}
