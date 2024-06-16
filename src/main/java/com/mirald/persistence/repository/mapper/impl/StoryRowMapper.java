package com.mirald.persistence.repository.mapper.impl;

import com.mirald.persistence.entity.Story;
import com.mirald.persistence.repository.mapper.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class StoryRowMapper implements RowMapper<Story> {

    @Override
    public Story mapRow(ResultSet rs) throws SQLException {
        return new Story(
            UUID.fromString(rs.getString("id")),
            rs.getString("name"),
            rs.getString("description")
        );
    }
}
