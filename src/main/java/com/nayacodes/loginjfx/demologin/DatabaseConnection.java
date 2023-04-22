package com.nayacodes.loginjfx.demologin;

import java.sql.Connection;
import java.sql.DriverManager;
public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName = "pocBase";
        String databaseUser = "root";
        String databasePassword = "chinaTUU98";
        String url = "jdbc:mysql://localhost/"+databaseName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        }catch(Exception e){
            e.printStackTrace();
        }

        return databaseLink;
    }
}
