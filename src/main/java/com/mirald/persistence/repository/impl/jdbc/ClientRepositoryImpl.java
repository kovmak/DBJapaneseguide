package com.mirald.persistence.repository.impl.jdbc;

import com.mirald.persistence.entity.Client;
import com.mirald.persistence.entity.Users.UsersRole;
import com.mirald.persistence.exception.EntityNotFoundException;
import com.mirald.persistence.repository.GenericJdbcRepository;
import com.mirald.persistence.repository.contract.ClientRepository;
import com.mirald.persistence.repository.contract.TableNames;
import com.mirald.persistence.repository.mapper.impl.ClientRowMapper;
import com.mirald.persistence.util.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepositoryImpl extends GenericJdbcRepository<Client> implements ClientRepository {

    private final ConnectionManager connectionManager;
    private final ClientRowMapper clientRowMapper;
    private final JdbcManyToMany jdbcManyToMany;

    public ClientRepositoryImpl(
        ConnectionManager connectionManager,
        ClientRowMapper clientRowMapper,
        JdbcManyToMany jdbcManyToMany) {
        super(connectionManager, clientRowMapper, TableNames.CLIENT.getName());
        this.connectionManager = connectionManager;
        this.clientRowMapper = clientRowMapper;
        this.jdbcManyToMany = jdbcManyToMany;
    }

    @Override
    protected List<String> tableAttributes() {
        return List.of(
            "name",
            "phone",
            "address",
            "section_id"
        );
    }

    @Override
    protected List<Object> tableValues(Client client) {
        return List.of(
            client.name(),
            client.phone(),
            client.address()
        );
    }

    @Override
    public Optional<Client> findByName(String name) {
        return findBy("name", name);
    }

    @Override
    public Set<Client> findByUserRole(UsersRole UsersRole) {
        final String selectUserIdSql = "SELECT id FROM users WHERE role = ?";
        final String selectClientSql = "SELECT * FROM client WHERE id = ?";
        UUID clientId = null;

        try (Connection connection = connectionManager.get();
            PreparedStatement selectUserIdStatement = connection.prepareStatement(selectUserIdSql)) {

            selectUserIdStatement.setString(1, UsersRole.toString());
            ResultSet resultSet = selectUserIdStatement.executeQuery();

            if (resultSet.next()) {
                clientId = UUID.fromString(resultSet.getString("id"));
            }

        } catch (SQLException throwables) {
            throw new EntityNotFoundException("Customer search error");
        }

        if (clientId != null) {
            return jdbcManyToMany.getByPivot(
                clientId,
                selectClientSql,
                clientRowMapper,
                "Customer search error: " + UsersRole
            );
        } else {
            return Set.of();
        }
    }
}
