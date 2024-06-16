package com.mirald.domain.service.impl;

import com.mirald.domain.exception.UserAlreadyAuthenticatedException;
import com.mirald.persistence.context.factory.PersistenceContext;
import com.mirald.persistence.entity.Users;
import com.mirald.persistence.repository.contract.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private Users user;

    AuthenticationService(PersistenceContext persistenceContext) {
        this.userRepository = persistenceContext.users.repository;
    }

    public boolean authenticate(String login, String password) {
        if (user != null) {
            throw new UserAlreadyAuthenticatedException(
                STR."You are already logged in as: \{user.login()}");
        }

        Optional<Users> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isPresent()) {
            Users foundedUser = optionalUser.get();
            if (password.equals(foundedUser.getPassword())) {
                user = foundedUser;
                return true;
            }
        }

        return false;
    }

    public boolean isAuthenticated() {
        return user != null;
    }

    public Users getUser() {
        return user;
    }

    public void logout() {
        if (user == null) {
            throw new UserAlreadyAuthenticatedException("You are not yet authenticated.");
        }
        user = null;
    }

}
