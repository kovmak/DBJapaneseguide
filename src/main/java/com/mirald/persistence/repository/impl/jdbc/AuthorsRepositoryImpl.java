package com.mirald.persistence.repository.impl.jdbc;

import com.mirald.persistence.entity.Authors;
import com.mirald.persistence.repository.GenericJdbcRepository;
import com.mirald.persistence.repository.contract.AuthorsRepository;
import com.mirald.persistence.repository.contract.TableNames;
import com.mirald.persistence.repository.mapper.impl.AuthorsRowMapper;
import com.mirald.persistence.util.ConnectionManager;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorsRepositoryImpl extends GenericJdbcRepository<Authors> implements
        AuthorsRepository {
    private final AuthorsRowMapper categoryRowMapper;
    private final JdbcManyToMany<Authors> jdbcManyToMany;

    public AuthorsRepositoryImpl(
        ConnectionManager connectionManager,
        AuthorsRowMapper rowMapper,
        AuthorsRowMapper categoryRowMapper,
        JdbcManyToMany<Authors> jdbcManyToMany) {
        super(connectionManager, rowMapper, TableNames.AUTHORS.getName());
        this.jdbcManyToMany = jdbcManyToMany;
        this.categoryRowMapper = categoryRowMapper;
    }

    @Override
    protected List<String> tableAttributes() {
        return List.of("name", "description");
    }

    @Override
    protected List<Object> tableValues(Authors category) {
        return List.of(category.name(), category.address());
    }

    @Override
    public Set<Authors> findAllByToyId(UUID personId) {
        final String sql = """
        SELECT c.id,
               c.name,
               c.description
          FROM category AS c
               JOIN Description_category AS tc
                 ON c.id = tc.category_id
         WHERE tc.Description_id = ?;
        """;

        // Assuming jdbcManyToMany is a JdbcManyToMany<Authors>
        return jdbcManyToMany.getByPivot(
            personId,
            sql,
            categoryRowMapper, // Use AuthorsRowMapper here
            "Error while getting all categories for toy id: " + personId);
    }

    @Override
    public boolean attach(UUID personId, UUID categoryId) {
        final String sql =
            """
            UPDATE person
            SET category_id = ?
            WHERE id = ?;
            """;
        return jdbcManyToMany.executeUpdate(
            categoryId, personId, sql, "Error adding a category");
    }

    @Override
    public boolean detach(UUID parentId, UUID childId) {
        return false;
    }
}
