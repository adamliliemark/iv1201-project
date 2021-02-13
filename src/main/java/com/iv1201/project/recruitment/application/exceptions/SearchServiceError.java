package com.iv1201.project.recruitment.application.exceptions;

/**
 * A exception class thrown when errors concerning searches in the applications has occurred.
 */
public class SearchServiceError extends Exception{

    /**
     * A constructor.
     * @param error is the message containing info about the thrown error.
     */
    public SearchServiceError(String error){
        super(error);
    }
}

