package com.mirald.persistence.context.impl;

import com.mirald.persistence.context.GenericUnitOfWork;
import com.mirald.persistence.entity.Title;
import com.mirald.persistence.repository.contract.TitleRepository;
import org.springframework.stereotype.Component;

@Component
public class TitleContext extends GenericUnitOfWork<Title> {

    public final TitleRepository repository;

    public TitleContext(TitleRepository repository){
        super(repository);
        this.repository = repository;
    }
}
