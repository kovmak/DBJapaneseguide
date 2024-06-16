package com.mirald.persistence.entity;

import java.util.UUID;

public record Title(UUID id,
                    String name,
                    String description) implements Entity, Comparable<Title> {

    public Title(UUID id, String name) {
        this(id, name, "");
    }

    @Override
    public int compareTo(Title o) {
        return this.name.compareTo(o.name);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public UUID getId() {
        return id;
    }
}
