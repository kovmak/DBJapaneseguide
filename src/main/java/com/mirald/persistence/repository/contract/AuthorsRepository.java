package com.mirald.persistence.repository.contract;

import com.mirald.persistence.entity.Authors;
import com.mirald.persistence.repository.Repository;
import java.util.Set;
import java.util.UUID;

public interface AuthorsRepository extends Repository<Authors>, OneToMany {

    Set<Authors> findAllByToyId(UUID toyId);
}
