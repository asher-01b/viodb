package com.viodb.core.dto;

import lombok.Data;

@Data
public class ConnectDTO {
    // mysql, oracle etc.
    private String databaseType;
    private String host;
    private int port;
    private String username;
    private String password;
    // specific database name, like viodb_test, world etc. (optional)
    private String databaseName;
}