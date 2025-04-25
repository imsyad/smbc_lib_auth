package com.smbc.library.auth_service.dto;

import java.io.Serial;
import java.io.Serializable;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 341455593821L;

    @NotNull(message = "User ID cannot be null")
    @Min(value = 1, message = "User ID must be greater than 0")
    private Long userId;

    @NotEmpty(message = "Fullname cannot be empty")
    @NotBlank(message = "Fullname cannot contain only whitespace")
    @Size(min = 1, max = 255, message = "Fullname length must be between 1 and 255 characters")
    private String fullname;
}
