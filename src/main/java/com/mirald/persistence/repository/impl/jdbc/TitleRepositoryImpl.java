package com.mirald.persistence.repository.impl.jdbc;

import com.mirald.persistence.entity.Title;
import com.mirald.persistence.repository.GenericJdbcRepository;
import com.mirald.persistence.repository.contract.TitleRepository;
import com.mirald.persistence.repository.contract.TableNames;
import com.mirald.persistence.repository.mapper.impl.TitleRowMapper;
import com.mirald.persistence.util.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class TitleRepositoryImpl extends GenericJdbcRepository<Title> implements
        TitleRepository {
    private final ConnectionManager connectionManager;

    public TitleRepositoryImpl(
        ConnectionManager connectionManager,
        TitleRowMapper sectionRowMapper) {
        super(connectionManager, sectionRowMapper, TableNames.TITLE.getName());
        this.connectionManager = connectionManager;
    }

    @Override
    protected List<String> tableAttributes() {
        return List.of(
            "name",
            "description"
        );
    }

    @Override
    protected List<Object> tableValues(Title sections) {
        return List.of(
            sections.name()
        );
    }

    @Override
    public Optional<Title> findByName(String name) {
        return findBy("name", name);
    }

    @Override
    public Optional<Title> findByDescription(String description) {
        return super.findBy("description", description);
    }

    @Override
    public boolean attach(UUID parentId, UUID childId) {
        String sql = "UPDATE Toy SET section_id = ? WHERE id = ?";
        try (Connection connection = connectionManager.get();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, parentId);
            statement.setObject(2, childId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            new SQLException(e);
            return false;
        }
    }

    @Override
    public boolean detach(UUID parentId, UUID childId) {
        String sql = "UPDATE Toy SET section_id = NULL WHERE id = ?";
        try (Connection connection = connectionManager.get();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, childId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            new SQLException(e);
            return false;
        }
    }
}

