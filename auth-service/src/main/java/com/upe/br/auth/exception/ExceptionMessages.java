package com.upe.br.auth.exception;

public class ExceptionMessages {

    public static final String USER_NOT_FOUND = "User not found";
    public static final String FAILED_TO_CREATE_TOKEN = "Error while creating access token";
    public static final String USER_ALREADY_EXISTIS = "Email already taken";
    public static final String INVALID_CREDENTIALS = "Invalid credentials were provided";
    public static final String INVALID_TOKEN = "An invalid token was provided";
    public static final String TOKEN_WAS_REVOKED = "The refresh token was revoked";
    public static final String FAILED_TO_GET_EXPIRATION_TIME = "Error while getting token expiration time";

    private ExceptionMessages() {}
}
