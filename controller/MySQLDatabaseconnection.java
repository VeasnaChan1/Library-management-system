package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDatabaseconnection {

    private static Connection connection = null;
    private static final String URL = "jdbc:mysql://localhost:3306/librarydb";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        if (connection == null) {
            try { 
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static ResultSet executeQuery(String query){
        
            try {
                Statement statement = getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                System.out.println("Connected Succussfully");
                return resultSet;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }  
            return null;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection = null; // reset so getConnection can reconnect later
            }
        }
    }

    public static void main(String[] args) {
        connection = getConnection();
        // closeConnection();  // Remove this to keep connection open

        ResultSet resultSet = executeQuery("select * from staff");
        try {
            while (resultSet != null && resultSet.next()) {
                System.out.println(resultSet.getString("full_name")+" - "+resultSet.getString("staff_id")+ "- "+resultSet.getString("position"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet resultSet2 = executeQuery("select * from book");
        try {
            while (resultSet2 != null && resultSet2.next()) {
                System.out.println(resultSet2.getString("title")+" - "+resultSet2.getString("author")+ "- "+resultSet2.getInt("amount"));
            }
        } catch (SQLException a) {
            a.printStackTrace();
        }

        ResultSet resultSet3 = executeQuery("select * from member");
        try {
            while (resultSet3 != null && resultSet3.next()) {
                System.out.println(resultSet3.getString("name")+" - "+resultSet3.getString("member_id")+ "- "+resultSet3.getString("member_id"));
            }
        } catch (SQLException b) {
            b.printStackTrace();
        }

        closeConnection();  // Close at the end
    }
}
