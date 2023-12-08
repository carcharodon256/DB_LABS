package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

import entities.Client;
import query_execution_time.*;

import entities.Client;

public class ClientCRUDOperations {
    public static String createRecord(Connection connection, String clientEmailAddress, String clientName, String clientPhoneNumber){
        // string with query text
        String creationQuery = "INSERT INTO client (client_email_address, client_name, client_phone_number) VALUES (?, ?, ?)";

        // query result text
        String operationResult;

        // preparing of query object
        try (PreparedStatement queryObject = connection.prepareStatement(creationQuery)) {
            // putting field values
            queryObject.setString(1, clientEmailAddress);
            queryObject.setString(2, clientName);
            queryObject.setString(3, clientPhoneNumber);

            long queryExecutionStartTime = System.currentTimeMillis();

            // query execution
            int rowsChanged = queryObject.executeUpdate();

            long queryExecutionEndTime = System.currentTimeMillis();

            QueryExecutionTimeMeasuring.queryExecutionTime = QueryExecutionTimeMeasuring.measureQueryExecutionTime(queryExecutionStartTime, queryExecutionEndTime);

            // checking query execution correctness
            if (rowsChanged > 0) {
                operationResult = "New record successfully created in 'client' table.";
            } else {
                operationResult = "New record adding error.";
            }
        } catch (SQLException e) {
            operationResult = "New record adding error: " + e.getMessage();
        }

        return operationResult;
    }

    public static ArrayList<Client> getAllRecords(Connection connection) throws SQLException{
        ArrayList<Client> clients = new ArrayList<>(); // list for saving clients records

        String selectionQuery = "SELECT * FROM client";

        try (PreparedStatement queryObject = connection.prepareStatement(selectionQuery)) {
            long queryExecutionStartTime = System.currentTimeMillis();

            // object with a set of results
            ResultSet resultSet = queryObject.executeQuery();

            long queryExecutionEndTime = System.currentTimeMillis();

            QueryExecutionTimeMeasuring.queryExecutionTime = QueryExecutionTimeMeasuring.measureQueryExecutionTime(queryExecutionStartTime, queryExecutionEndTime);

            while (resultSet.next()) { // getting record fields values
                String clientEmailAddress = resultSet.getString("client_email_address");
                String clientName = resultSet.getString("client_name");
                String client_phone_number = resultSet.getString("client_phone_number");

                // client object creation
                Client client = new Client(clientEmailAddress, clientName, client_phone_number);
                clients.add(client); // adding client objects into list
            }
        } catch (SQLException e){
            throw  e;
        }

        return clients;
    }

    public static ArrayList<String> getClientsWithMostOrders(Connection connection) throws SQLException{
        ArrayList<String> clients = new ArrayList<>();

        String selectionQuery = "select client.client_name from client join\n" +
                "(select client_email_address, count(*) as order_count\n" +
                "from orders group by client_email_address order by \n" +
                "order_count desc limit 1) as s1 on \n" +
                "client.client_email_address = s1.client_email_address;";

        try (PreparedStatement queryObject = connection.prepareStatement(selectionQuery)){

            long queryExecutionStartTime = System.currentTimeMillis();

            try (ResultSet resultSet = queryObject.executeQuery()){

                long queryExecutionEndTime = System.currentTimeMillis();

                QueryExecutionTimeMeasuring.queryExecutionTime = QueryExecutionTimeMeasuring.measureQueryExecutionTime(queryExecutionStartTime, queryExecutionEndTime);

                while (resultSet.next()){
                    String clientName = resultSet.getString("client_name");
                    clients.add(clientName);
                }
            }
        } catch (SQLException e){
            throw  e;
        }

        return clients;
    }

    public static String updateRecord(Connection connection, String clientEmailAddress, String newClientName, String newClientPhoneNumber){
        String updateQuery = "UPDATE client SET client_name = COALESCE(?, client_name), client_phone_number = COALESCE(?, client_phone_number) WHERE client_email_address = ?";
        String operationResult;

        long queryExecutionStartTime = System.currentTimeMillis();

        try (PreparedStatement queryObject = connection.prepareStatement(updateQuery)) {
            long queryExecutionEndTime = System.currentTimeMillis();

            QueryExecutionTimeMeasuring.queryExecutionTime = QueryExecutionTimeMeasuring.measureQueryExecutionTime(queryExecutionStartTime, queryExecutionEndTime);

            queryObject.setString(1, newClientName);
            queryObject.setString(2, newClientPhoneNumber);
            queryObject.setString(3, clientEmailAddress);

            int rowsChanged = queryObject.executeUpdate();

            if (rowsChanged > 0) {
                operationResult = "Record successfully updated in 'client' table.";
            } else {
                operationResult = "Client with email address " + clientEmailAddress + " not found.";
            }
        } catch (SQLException e) {
            operationResult = "Record update error: " + e.getMessage();
        }

        return operationResult;
    }

    public static String deleteRecord(Connection connection, String clientEmailAddress){
        String selectOrderNumbersQuery = "SELECT order_number FROM orders WHERE client_email_address = ?";
        String deleteDishRecordsQuery = "DELETE FROM dish WHERE order_number = ?";
        String deleteClientQuery = "DELETE FROM client WHERE client_email_address = ?";
        String deleteOrdersQuery = "DELETE FROM orders WHERE client_email_address = ?";

        String operationResult;

        int ordersRowsChanged = 0;
        int dishRowsChanged = 0;

        long queryExecutionStartTime = System.currentTimeMillis();

        try (PreparedStatement selectOrderNumbers = connection.prepareStatement(selectOrderNumbersQuery);
             PreparedStatement deleteDishRecords = connection.prepareStatement(deleteDishRecordsQuery);
             PreparedStatement deleteClientRecord = connection.prepareStatement(deleteClientQuery);
             PreparedStatement deleteOrdersRecord = connection.prepareStatement(deleteOrdersQuery)) {

            long queryExecutionEndTime = System.currentTimeMillis();

            QueryExecutionTimeMeasuring.queryExecutionTime = QueryExecutionTimeMeasuring.measureQueryExecutionTime(queryExecutionStartTime, queryExecutionEndTime);

            connection.setAutoCommit(false);

            selectOrderNumbers.setString(1, clientEmailAddress);
            ResultSet orderNumbersResultSet = selectOrderNumbers.executeQuery();

            while (orderNumbersResultSet.next()) {
                int orderNumber = orderNumbersResultSet.getInt("order_number");

                deleteDishRecords.setInt(1, orderNumber);
                dishRowsChanged = deleteDishRecords.executeUpdate();
            }

            deleteOrdersRecord.setString(1, clientEmailAddress);
            ordersRowsChanged = deleteOrdersRecord.executeUpdate();

            deleteClientRecord.setString(1, clientEmailAddress);
            int clientRowsChanged = deleteClientRecord.executeUpdate();

            if (dishRowsChanged > 0 || ordersRowsChanged > 0 || clientRowsChanged > 0) {
                connection.commit();
                operationResult = "Dish records, orders, and client successfully deleted.";
            } else {
                operationResult = "Client with email address " + clientEmailAddress + " not found.";
            }
        } catch (SQLException e) {
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException ex) {
                operationResult = "Database connection error: " + e.getMessage();
            }
            operationResult = "Client deletion error: " + e.getMessage();
        }

        return operationResult;
    }
}
