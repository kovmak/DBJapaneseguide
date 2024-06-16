package com.mirald.persistence.repository.contract;

import com.mirald.persistence.entity.Title;
import com.mirald.persistence.repository.Repository;
import java.util.Optional;

public interface TitleRepository extends Repository<Title>, OneToMany {

    Optional<Title> findByName(String name);

    Optional<Title> findByDescription(String description);
}
