package com.mirald.persistence.context.impl;

import com.mirald.persistence.context.GenericUnitOfWork;
import com.mirald.persistence.entity.Story;
import com.mirald.persistence.repository.contract.StoryRepository;
import org.springframework.stereotype.Component;

@Component
public class StoryContext extends GenericUnitOfWork<Story> {

    public final StoryRepository repository;

    protected StoryContext(StoryRepository repository){
        super(repository);
        this.repository = repository;
    }
}
