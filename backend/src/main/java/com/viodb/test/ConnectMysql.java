package com.viodb.test;

import java.sql.*;


public class ConnectMysql {
    public static void main(String[] args) {
        String user = "root";
        String password = "123456";
        String url = "jdbc:mysql://localhost:3306/world";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url,  user, password);
            String sql = "select * from city";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String countryCode = rs.getString("CountryCode");
                String district = rs.getString("District");
                int population = rs.getInt("Population");

                System.out.println(id + " " + name + " " + countryCode + " " + district + " " + population);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                if (conn != null) {conn.close();}
                if (stmt != null) {stmt.close();}
                if (rs != null) {rs.close();}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}