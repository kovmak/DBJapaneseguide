package com.mirald.persistence.entity;

import java.util.UUID;

public record Authors(UUID id,
                      String name,
                      String address) implements Entity, Comparable<Authors> {

    public Authors(UUID id, String name) {
        this(id, name, "");
    }

    @Override
    public int compareTo(Authors o) {
        return this.name.compareTo(o.name);
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public UUID getId() {
        return id;
    }
}
