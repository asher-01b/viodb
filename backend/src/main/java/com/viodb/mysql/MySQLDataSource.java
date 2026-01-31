package com.viodb.mysql;

import com.viodb.core.dto.ConnectDTO;
import com.viodb.core.QueryResult;
import com.viodb.core.VioDataSource;
import com.viodb.core.dto.SqlDTO;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MySQLDataSource implements VioDataSource {
    private Connection currentConnection;

    @Override
    public String connect(ConnectDTO connDTO) throws SQLException {
        String url = "jdbc:" + connDTO.getDatabaseType() + "://" + connDTO.getHost() + ":" + connDTO.getPort() + "/";
        if (connDTO.getDatabaseName() != null) url += connDTO.getDatabaseName();

        SqlDTO sqlDTO = new SqlDTO("select version()");
        QueryResult queryResult = execute(sqlDTO);

        List<Map<String, Object>> rows = queryResult.getRows();

        try {
            currentConnection = DriverManager.getConnection(url, connDTO.getUsername(), connDTO.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows.getFirst().get("version").toString();
    }

    @Override
    public QueryResult execute(SqlDTO sqlDTO) throws SQLException {
        QueryResult queryResult = new QueryResult();
        String sql = sqlDTO.getSql().toLowerCase();
        ResultSet rs = null;
        boolean hasResults = false;

        // determine the type of sql statement
        if (sql.startsWith("select")) {
            // hard-coding might not be a good practice
            queryResult.setType("QUERY");
        } else if (sql.startsWith("insert") || sql.startsWith("update") || sql.startsWith("delete")) {
            queryResult.setType("EXECUTE");
        } else if (sql.startsWith("create") || sql.startsWith("alter") || sql.startsWith("drop")) {
            queryResult.setType("DEFINITION");
        } else if (sql.startsWith("grand") || sql.startsWith("revoke")) {
            queryResult.setType("PERMISSION");
        }

        Statement stmt = currentConnection.createStatement();
        hasResults = stmt.execute(sql);

        // SELECT operation
        if (hasResults) {
            rs = stmt.getResultSet();
            // get columns name
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            ArrayList<String> columns = new ArrayList<>();
            ArrayList<Map<String, Object>> rows = new ArrayList<>();

            for (int i = 1; i <= columnCount; i++) {
                columns.add(metaData.getColumnName(i));
            }

            queryResult.setColumns(columns);

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    HashMap<String, Object> row = new HashMap<>();
                    row.put(columns.get(i - 1), rs.getObject(i));
                    rows.add(row);
                }
            }
            
            queryResult.setRows(rows);
        } else {
            // UPDATE/INSERT/DELETE operation
            int updateCount = stmt.getUpdateCount();

            queryResult.setAffectRows(updateCount);
        }
        // if no exception is thrown, the execution is successful
        queryResult.setSuccess(true);

        return queryResult;
    }

    @Override
    public void close() {
        if (currentConnection != null) {
            try {
                currentConnection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}