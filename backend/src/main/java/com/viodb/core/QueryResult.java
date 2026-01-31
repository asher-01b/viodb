package com.viodb.core;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class QueryResult {
    // view object

    // tell the frontend the type of this operation
    // QUERY: SELECT (show data of table)
    // EXECUTE: UPDATE/INSERT/DELETE (show affect rows)
    // DEFINITION: CREATE/ALTER/DROP (return success or not)
    // PERMISSION: GRAND/REVOKE (return success or not)
    private String type;

    // columns of table
    private List<String> columns;

    // data: [{"id":1, "name":"Bob"}, {"id":2, "name":"Jack"}]
    private List<Map<String,Object>> rows;

    private int affectRows;

    private boolean success;
}