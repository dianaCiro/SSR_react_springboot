package com.atlassian.springbootreact.application.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseError {

    /**
     * Indicate the error code status.
     */
    private int code;

    /**
     * Indicate the error message.
     */
    private String message;
}
