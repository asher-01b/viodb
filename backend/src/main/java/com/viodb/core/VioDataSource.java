package com.viodb.core;

import com.viodb.core.dto.ConnectDTO;
import com.viodb.core.dto.SqlDTO;

public interface VioDataSource {
    /**
     * try to connect to the database
     * @param connDTO information of connection
     * @return the version of database (successfully connect or not)
     * @throws Exception
     */
    String connect(ConnectDTO connDTO) throws Exception;

    /**
     * execute sql statement
     * @param sqlDTO sql statement
     * @return Packaged results
     * @throws Exception when an error occurs
     */
    QueryResult execute(SqlDTO sqlDTO) throws Exception;

    /**
     * close the connection
     */
    void close();
}