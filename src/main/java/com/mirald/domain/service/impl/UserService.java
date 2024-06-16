package com.mirald.domain.service.impl;

import com.mirald.domain.dto.UserStoryDto;
import com.mirald.domain.exception.ValidationException;
import com.mirald.persistence.context.factory.PersistenceContext;
import com.mirald.persistence.context.impl.UserContext;
import com.mirald.persistence.entity.Users;
import com.mirald.persistence.entity.Users.UsersRole;
import jakarta.validation.Validator;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserContext userContext;
    private final Validator validator;

    public UserService(PersistenceContext persistenceContext, Validator validator) {
        this.userContext = persistenceContext.users;
        this.validator = validator;
    }


    public Users create(UserStoryDto userStoreDto) {
        var violations = validator.validate(userStoreDto);
        if (!violations.isEmpty()) {
            throw ValidationException.create("User saves", violations);
        }

        Users user = new Users(
            userStoreDto.id(),
            userStoreDto.login(),
            userStoreDto.password(),
            Objects.nonNull(userStoreDto.role()) ? userStoreDto.role() : UsersRole.CLIENT,
            userStoreDto.name()
        );

        userContext.registerNew(user);
        userContext.commit();
        return null;
    }
}