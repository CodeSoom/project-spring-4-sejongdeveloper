package com.codesoom.sejongdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {

    private String message; //에러 메시지

    public ErrorResponse(String message) {
        this.message = message;
    }

}
