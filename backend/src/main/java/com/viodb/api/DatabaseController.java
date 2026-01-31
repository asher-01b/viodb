package com.viodb.api;

import com.viodb.core.ApiResponse;
import com.viodb.core.dto.ConnectDTO;
import com.viodb.core.dto.SqlDTO;
import com.viodb.core.QueryResult;
import com.viodb.mysql.MySQLDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api")
@CrossOrigin("*") // Allow Electron to make cross-origin calls
public class DatabaseController {

    @Autowired
    private MySQLDataSource mysqlDataSource;

    // POST http://localhost:8080/api/connect
    @PostMapping("/connect")
    public ApiResponse<String> connect(@RequestBody ConnectDTO connectDTO) {
        // return database version if connect successfully
        String version = "";
        // handle SQLException here
        try {
            version = mysqlDataSource.connect(connectDTO);
        } catch (SQLException e) {
            return ApiResponse.error("Connect error: " + e.getMessage());
        }

        return ApiResponse.success(version);
    }

    @PostMapping("/execute")
    public ApiResponse<QueryResult> execute(@RequestBody SqlDTO sqlDTO) {
        QueryResult queryResult;

        if (sqlDTO.getSql() == null || sqlDTO.getSql().trim().isEmpty())
            return ApiResponse.error("sql is empty");

        try {
            queryResult = mysqlDataSource.execute(sqlDTO);
        } catch (SQLException e) {
            return ApiResponse.error("Execute error: " + e.getMessage());
        }
        return ApiResponse.success(queryResult);
    }
}