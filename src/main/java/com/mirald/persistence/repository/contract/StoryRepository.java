package com.mirald.persistence.repository.contract;

import com.mirald.persistence.entity.Story;
import com.mirald.persistence.repository.Repository;
import java.util.Set;
import java.util.UUID;

public interface StoryRepository extends Repository<Story>, ManyToMany {

    Set<Story> findAllDescriptionsCategory(UUID categoryId);

}