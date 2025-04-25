package com.smbc.library.auth_service.dto;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -7208658160002406393L;

    @JsonProperty(index = 1)
    private Integer status;

    @JsonProperty(index = 2)
    private String message;

    @JsonProperty(index = 3)
    private T data;
}
