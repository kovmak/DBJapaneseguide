package com.mirald.persistence.repository.contract;

import com.mirald.persistence.entity.Client;
import com.mirald.persistence.entity.Users.UsersRole;
import com.mirald.persistence.repository.Repository;
import java.util.Optional;
import java.util.Set;

public interface ClientRepository extends Repository<Client> {
    Optional<Client> findByName(String name);
    Set<Client> findByUserRole(UsersRole UsersRole);
}
