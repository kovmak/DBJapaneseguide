package com.mirald.persistence.entity;

import java.util.UUID;

public record Story(UUID id,
                    String name,
                    String description,
                    UUID categoryId) implements Entity, Comparable<Story> {

    public Story(UUID id, String name) {
        this(id, name, "", null);
    }


    public Story(UUID id, String name, String description) {
        this(id, name, description, null);
    }

    @Override
    public int compareTo(Story description) {
        return this.name.compareTo(description.name);
    }

    public String getName() {
        return name;
    }

    public String getDescriptions() {
        return description;
    }
}
