package com.mirald.persistence.repository.mapper.impl;

import com.mirald.persistence.entity.Authors;
import com.mirald.persistence.repository.mapper.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class AuthorsRowMapper implements RowMapper<Authors> {

    @Override
    public Authors mapRow(ResultSet rs) throws SQLException {
        return new Authors(
            UUID.fromString(rs.getString("id")),
            rs.getString("name"),
            rs.getString("description")
        );
    }
}
