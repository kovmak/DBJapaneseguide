package com.mirald.domain.exception;

import java.io.Serial;

public class AccessDeniedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 174948262083496647L;

    public AccessDeniedException(String message) {
        super(message);
    }

    public static AccessDeniedException notAuthorUser(String suffix) {
        return new AccessDeniedException(STR."You are not the author, so you do not have the right to \{suffix}.");
    }

    public static AccessDeniedException notAuthorOrBannedUser(String suffix) {
        return new AccessDeniedException(
            STR."You are not the author or have been banned, so you are not allowed to \{suffix}.");
    }

    public static AccessDeniedException bannedUser(String suffix) {
        return new AccessDeniedException(STR."You are banned, so you are not allowed to \{suffix}.");
    }
}
