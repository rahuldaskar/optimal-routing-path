package com.rda.optimal.route.exception;

/**
 * Common global exception.
 */
public class BaseException extends RuntimeException {
    public BaseException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public BaseException(String emptyFileError) {
        super(emptyFileError);
    }
}
