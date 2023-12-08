package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection connection;

    public Connection connectToDatabase() {
        if (connection != null)
            return connection;

        try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            String password = "simple";

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Successful database connection.");

            return connection;

        } catch (ClassNotFoundException | SQLException e){
            System.out.println("PostgreSQL connection error: " + e.getMessage());
            throw new RuntimeException("Database connection error. ", e);
        }
    }

    public void disconnectFromDatabase() {
        try {
            if(connection != null){
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e){
            System.out.println("Closing connection error: " + e.getMessage());
        }
    }
}
