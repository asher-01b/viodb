package com.viodb.core.dto;

import lombok.Data;

@Data
public class SqlDTO {
    private String sql;

    public SqlDTO(String sql) {
        this.sql = sql;
    }
}
