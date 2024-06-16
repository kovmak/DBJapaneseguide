package com.mirald.persistence.context.impl;

import com.mirald.persistence.context.GenericUnitOfWork;
import com.mirald.persistence.entity.Authors;
import com.mirald.persistence.repository.contract.AuthorsRepository;
import org.springframework.stereotype.Component;

@Component
public class AuthorsContext extends GenericUnitOfWork<Authors> {

    public final AuthorsRepository repository;

    public AuthorsContext(AuthorsRepository repository){
        super(repository);
        this.repository = repository;
    }
}
