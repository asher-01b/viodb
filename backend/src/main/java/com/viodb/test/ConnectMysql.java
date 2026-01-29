package com.viodb.test;

import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;


public class ConnectMysql {
    public static void main(String[] args) {
        String user = null;
        String password = null;
        int databaseId = -1;
        String baseUrl = "jdbc:mysql://localhost:3306/";

        HashMap<Integer, String> databaseTypeMap = new HashMap<>();
        databaseTypeMap.put(1, "mysql");

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        Scanner scanner = new Scanner(System.in);

        System.out.print("Select database type [1] mysql: ");
        databaseId = scanner.nextInt();

        System.out.print("Please input username: ");
        user = scanner.next();

        System.out.print("Please input password: ");
        password = scanner.next();

        // clear the \n
        scanner.nextLine();

        // get whole url
        StringBuilder sb = new StringBuilder();
        sb.append(baseUrl);
        sb.append(databaseTypeMap.get(databaseId));
        String url = sb.toString();

        // simulate a mysql client terminal
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql;
            System.out.println("Connect to database successfully. Type quit to exit.");

            while (true) {
                System.out.print("$> ");
                sql = scanner.nextLine().trim();

                if (sql.isEmpty()) continue;

                if (sql.equals("quit")) {
                    System.out.println("Bye!");
                    break;
                }

                stmt = conn.createStatement();
                boolean isResultSet = stmt.execute(sql);

                if (isResultSet) {
                    rs = stmt.getResultSet();
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    // print the metadata
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(metaData.getColumnName(i) + " ");
                    }

                    // print the resultset
                    while (rs.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            // use getObject to handle all type
                            Object val = rs.getObject(i);
                            System.out.print((val == null ? "NULL" : val.toString()) + "\t");
                        }
                        System.out.println();
                    }
                } else { // USE, INSERT, UPDATE, DELETE operation will not return Resultset
                    int updateCount = stmt.getUpdateCount();
                    System.out.println("Query OK, " + updateCount + " rows affected.");
                }

                while (rs.next()) {
                    System.out.println(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}