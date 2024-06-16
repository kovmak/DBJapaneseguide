package com.mirald.persistence.context.factory;


import com.mirald.persistence.context.impl.*;
import com.mirald.persistence.context.impl.TitleContext;
import org.springframework.stereotype.Component;

@Component
public class PersistenceContext {

    public final AuthorsContext authors;
    public final ClientContext clients;
    public final TitleContext title;
    public final StoryContext story;
    public final UserContext users;

    public PersistenceContext(
            AuthorsContext authorsContext,
            ClientContext clientContext,
            TitleContext titleContext,
            StoryContext storyContext,
            UserContext userContext) {
        this.authors = authorsContext;
        this.clients = clientContext;
        this.title = titleContext;
        this.story = storyContext;
        this.users = userContext;
    }
}