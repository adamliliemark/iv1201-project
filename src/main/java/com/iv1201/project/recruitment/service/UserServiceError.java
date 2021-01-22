package com.iv1201.project.recruitment.service;

/**
 * ValidationError instances represent an error in a requested action
 * See <code>ERROR_CODE</code> enum.
 */
public class UserServiceError extends Exception {
    public enum ERROR_CODE {
        NONE,
        INVALID_EMAIL,
        INVALID_PASSWORD,
        CONFLICTING_USER,
        UNAUTHORIZED_RESOURCE_ACCESS,
        INVALID_FIRST_NAME,
        INVALID_LAST_NAME,
        INVALID_SSN;

    }

    public ERROR_CODE errorCode;
    public UserServiceError(ERROR_CODE err) {
        super();
        this.errorCode = err;
    }
}