package com.mirald.domain.dto;

import com.mirald.persistence.entity.Users.UsersRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

/**
 * A DTO for updating user details including ID, login, password, role, and name.
 * This DTO uses validation annotations to ensure the fields meet specific criteria.
 */
public record UserUpdateDto(
        /**
         * The unique identifier of the user.
         * Must not be null.
         */
        @NotNull(message = "User ID is missing")
        UUID id,

        /**
         * The login name of the user.
         * Must be between 6 and 64 characters long.
         * This field is optional for update.
         */
        @Size(min = 6, max = 64, message = "The username must contain from 6 to 64 characters")
        String login,

        /**
         * The password of the user.
         * Must be between 8 and 72 characters long.
         * This field is optional for update.
         */
        @Size(min = 8, max = 72, message = "The password must contain from 8 to 72 characters")
        String password,

        /**
         * The role of the user.
         * This field is optional for update.
         */
        UsersRole role,

        /**
         * The name of the user.
         * Must not be blank and must be between 6 and 64 characters long.
         */
        @NotBlank(message = "The username cannot be empty")
        @Size(min = 6, max = 64, message = "The username must contain from 6 to 64 characters")
        String name
) {

}
