package com.JobAppRest.Rest;

public class JobPostNotFoundException extends RuntimeException {
    public JobPostNotFoundException(String message) {
        super(message);
    }
}
