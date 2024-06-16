package com.mirald.persistence.repository.mapper.impl;

import com.mirald.persistence.entity.Title;
import com.mirald.persistence.repository.mapper.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class TitleRowMapper implements RowMapper<Title> {

    @Override
    public Title mapRow(ResultSet rs) throws SQLException {
        return new Title(
            UUID.fromString(rs.getString("id")),
            rs.getString("name"),
                rs.getString("description")
        );
    }
}
