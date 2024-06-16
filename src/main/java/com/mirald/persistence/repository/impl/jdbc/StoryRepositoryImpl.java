package com.mirald.persistence.repository.impl.jdbc;

import com.mirald.persistence.entity.Story;
import com.mirald.persistence.repository.GenericJdbcRepository;
import com.mirald.persistence.repository.contract.TableNames;
import com.mirald.persistence.repository.contract.StoryRepository;
import com.mirald.persistence.repository.mapper.impl.StoryRowMapper;
import com.mirald.persistence.util.ConnectionManager;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class StoryRepositoryImpl extends GenericJdbcRepository<Story> implements StoryRepository {

    private final JdbcManyToMany<Story> jdbcManyToMany;
    private final StoryRowMapper descriptionRowMapper;

    public StoryRepositoryImpl(
        ConnectionManager connectionManager,
        StoryRowMapper descriptionRowMapper,
        JdbcManyToMany<Story> jdbcManyToMany) {
        super(connectionManager, descriptionRowMapper, TableNames.STORY.getName());
        this.jdbcManyToMany = jdbcManyToMany;
        this.descriptionRowMapper = descriptionRowMapper;
    }

    @Override
    protected List<String> tableAttributes() {
        return List.of(
            "name",
            "description",
            "price",
            "category_id"
        );
    }

    @Override
    protected List<Object> tableValues(Story description) {
        return List.of(
                description.name(),
                description.description());
    }

    @Override
    public Set<Story> findAllDescriptionsCategory(UUID categoryId) {
        final String sql = """
        SELECT d.id, d.name, d.description, d.price, d.category_id, d.manufacture_id
        FROM toy d
        WHERE d.category_id = ?;
        """;

        return jdbcManyToMany.getByPivot(
            categoryId,
            sql,
            descriptionRowMapper,
            "Error while getting all description for category id: " + categoryId);
    }


    @Override
    public boolean attach(UUID descriptionId, UUID categoryId) {
        final String sql =
            """
            UPDATE description
            SET category_id = ?
            WHERE id = ?;
            """;
        return jdbcManyToMany.executeUpdate(
            categoryId, descriptionId, sql, "Error adding a category");
    }

    @Override
    public boolean detach(UUID descriptionId, UUID categoryId) {
        final String sql =
            """
            UPDATE description
            SET category_id = NULL
            WHERE id = ? AND category_id = ?;
            """;
        return jdbcManyToMany.executeUpdate(
                descriptionId, categoryId, sql, "Error when deleting a category");
    }
}
