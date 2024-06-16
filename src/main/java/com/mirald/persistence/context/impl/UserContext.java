package com.mirald.persistence.context.impl;

import com.mirald.persistence.context.GenericUnitOfWork;
import com.mirald.persistence.entity.Users;
import com.mirald.persistence.repository.contract.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserContext extends GenericUnitOfWork<Users> {

    public final UserRepository repository;

    protected UserContext(UserRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
