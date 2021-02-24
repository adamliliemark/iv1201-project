package com.iv1201.project.recruitment.application.exceptions;

/**
 * ValidationError instances represent an business level error in a requested action
 * @see ERROR_CODE enum.
 */
public class UserServiceError extends Exception {
    /**Denotes a specific error type*/
    public enum ERROR_CODE {
        NONE,
        INVALID_EMAIL,
        INVALID_PASSWORD,
        CONFLICTING_USER,
        UNAUTHORIZED_RESOURCE_ACCESS,
        INVALID_FIRST_NAME,
        INVALID_LAST_NAME,
        INVALID_SSN,
        CONFLICT_AVAILABIITY;
    }

    public ERROR_CODE errorCode;

    /**
     * Creates a UserServiceError
     * @param err the ERROR_CODE matching the error that occurred
     */
    public UserServiceError(ERROR_CODE err) {
        super();
        this.errorCode = err;
    }
}