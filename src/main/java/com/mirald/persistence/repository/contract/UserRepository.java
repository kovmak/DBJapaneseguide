package com.mirald.persistence.repository.contract;

import com.mirald.persistence.entity.Users;
import com.mirald.persistence.repository.Repository;
import java.util.Optional;

public interface UserRepository extends Repository<Users> {

    Optional<Users> findByLogin(String login);

    Optional<Users> findByName(String name);
}
