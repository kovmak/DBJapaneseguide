package com.mirald.domain.dto;

import com.mirald.persistence.entity.Users.UsersRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

/**
 * A DTO for User Guides that contains user details including ID, login, password, role, and name.
 * This DTO uses validation annotations to ensure the fields meet specific criteria.
 */
public record UserStoryDto(
        /**
         * The unique identifier of the user.
         * Must not be null.
         */
        @NotNull(message = "User ID is missing")
        UUID id,

        /**
         * The login name of the user.
         * Must not be blank and must be between 6 and 32 characters long.
         */
        @NotBlank(message = "User login cannot be empty")
        @Size(min = 6, max = 32, message = "User login must be between 6 and 32 characters long")
        String login,

        /**
         * The password of the user.
         * Must not be blank and must be between 8 and 72 characters long.
         */
        @NotBlank(message = "Password cannot be empty")
        @Size(min = 8, max = 72, message = "Password must be between 8 and 72 characters long")
        String password,

        /**
         * The role of the user.
         */
        UsersRole role,

        /**
         * The name of the user.
         * Must not be blank and must be between 6 and 64 characters long.
         */
        @NotBlank(message = "User name cannot be empty")
        @Size(min = 6, max = 64, message = "User name must be between 6 and 64 characters long")
        String name
) {

}
