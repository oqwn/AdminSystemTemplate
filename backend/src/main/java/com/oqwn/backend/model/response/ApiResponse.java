package com.oqwn.backend.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private String status; // success or error
    private int code; // HTTP status code or custom error code
    private String message; // Response message
    private String timestamp; // Timestamp of the response
    private T data; // Data returned from the request, could be null or an object

    // Constructors, getters, and setters
    public ApiResponse(String status, int code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.timestamp = new Date().toString();
        this.data = data;
    }

    // Default constructor for success responses with no data
    public ApiResponse(String status, int code, String message) {
        this(status, code, message, null);
    }
}

