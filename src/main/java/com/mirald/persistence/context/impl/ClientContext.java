package com.mirald.persistence.context.impl;

import com.mirald.persistence.context.GenericUnitOfWork;
import com.mirald.persistence.entity.Client;
import com.mirald.persistence.repository.contract.ClientRepository;
import org.springframework.stereotype.Component;

@Component
public class ClientContext extends GenericUnitOfWork<Client> {

    public final ClientRepository repository;

    public ClientContext(ClientRepository repository){
        super(repository);
        this.repository = repository;
    }
}