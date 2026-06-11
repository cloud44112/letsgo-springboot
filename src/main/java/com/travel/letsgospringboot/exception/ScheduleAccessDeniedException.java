package com.travel.letsgospringboot.exception;


public class ScheduleAccessDeniedException extends RuntimeException {
    public ScheduleAccessDeniedException(String message) {
        super(message);
    }
}
