package model;

import java.sql.*;
import java.util.ArrayList;

import entities.Order;
import query_execution_time.*;

public class OrderCRUDOperations {
    public static String createRecord(Connection connection, int orderNumber, String deliveryAddress, Timestamp deliveryDateTime, String clientEmailAddress, String courierPhoneNumber){
        String creationQuery = "INSERT INTO orders (order_number, delivery_address, delivery_date_time, client_email_address, courier_phone_number) VALUES (?, ?, ?, ?, ?)";

        String operationResult;

        try (PreparedStatement queryObject = connection.prepareStatement(creationQuery)) {
            queryObject.setInt(1, orderNumber);
            queryObject.setString(2, deliveryAddress);
            queryObject.setTimestamp(3, deliveryDateTime);
            queryObject.setString(4, clientEmailAddress);
            queryObject.setString(5, courierPhoneNumber);

            long queryExecutionStartTime = System.currentTimeMillis();

            int rowsChanged = queryObject.executeUpdate();

            long queryExecutionEndTime = System.currentTimeMillis();

            QueryExecutionTimeMeasuring.queryExecutionTime = QueryExecutionTimeMeasuring.measureQueryExecutionTime(queryExecutionStartTime, queryExecutionEndTime);

            if (rowsChanged > 0) {
                operationResult = "New record successfully created in 'orders' table.";
            } else {
                operationResult = "New record adding error.";
            }
        } catch (SQLException e) {
            operationResult = "New record adding error: " + e.getMessage();
        }

        return operationResult;
    }

    public static ArrayList<Order> getAllRecords(Connection connection) throws SQLException{
        ArrayList<Order> orders = new ArrayList<>();

        String selectionQuery = "SELECT * FROM orders";

        try (PreparedStatement queryObject = connection.prepareStatement(selectionQuery)) {

            long queryExecutionStartTime = System.currentTimeMillis();

            ResultSet resultSet = queryObject.executeQuery();

            long queryExecutionEndTime = System.currentTimeMillis();

            QueryExecutionTimeMeasuring.queryExecutionTime = QueryExecutionTimeMeasuring.measureQueryExecutionTime(queryExecutionStartTime, queryExecutionEndTime);

            while (resultSet.next()) {
                int orderNumber = resultSet.getInt("order_number");
                String deliveryAddress = resultSet.getString("delivery_address");
                Timestamp deliveryDateTime = resultSet.getTimestamp("delivery_date_time");
                String clientEmailAddress = resultSet.getString("client_email_address");
                String courierPhoneNumber = resultSet.getString("courier_phone_number");

                Order order = new Order(orderNumber, deliveryAddress, deliveryDateTime, clientEmailAddress, courierPhoneNumber);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw e;
        }

        return orders;
    }

    public static String updateRecord(Connection connection, int orderNumber, String newDeliveryAddress, Timestamp newDeliveryDateTime){
        String updateQuery = "UPDATE orders SET delivery_address = COALESCE(?, delivery_address), delivery_date_time = COALESCE(?, delivery_date_time) WHERE order_number = ?";

        String operationResult;

        try (PreparedStatement queryObject = connection.prepareStatement(updateQuery)) {
            queryObject.setString(1, newDeliveryAddress);
            queryObject.setTimestamp(2, newDeliveryDateTime);
            queryObject.setInt(3, orderNumber);

            long queryExecutionStartTime = System.currentTimeMillis();

            int rowsChanged = queryObject.executeUpdate();

            long queryExecutionEndTime = System.currentTimeMillis();

            QueryExecutionTimeMeasuring.queryExecutionTime = QueryExecutionTimeMeasuring.measureQueryExecutionTime(queryExecutionStartTime, queryExecutionEndTime);

            if (rowsChanged > 0) {
                operationResult = "Record successfully updated in 'orders' table.";
            } else {
                operationResult = "Order with order number " + orderNumber + " not found.";
            }
        } catch (SQLException e) {
            operationResult = "Order update error: " + e.getMessage();
        }

        return operationResult;
    }

    public static String deleteRecord(Connection connection, int orderNumber){
        String deletionQuery = "DELETE FROM orders WHERE order_number = ?";
        String cascadeDeletionQuery = "DELETE FROM dish WHERE order_number = ?";

        String operationResult;

        try (PreparedStatement deleteOrderRecord = connection.prepareStatement(deletionQuery);
             PreparedStatement deleteDishesRecords = connection.prepareCall(cascadeDeletionQuery)) {
            connection.setAutoCommit(false);

            deleteOrderRecord.setInt(1, orderNumber);

            long queryExecutionStartTime = System.currentTimeMillis();

            int ordersRowsChanged = deleteOrderRecord.executeUpdate();

            long queryExecutionEndTime = System.currentTimeMillis();
            QueryExecutionTimeMeasuring.queryExecutionTime = QueryExecutionTimeMeasuring.measureQueryExecutionTime(queryExecutionStartTime, queryExecutionEndTime);

            deleteDishesRecords.setInt(1, orderNumber);
            int dishRowsChanged = deleteDishesRecords.executeUpdate();

            if (ordersRowsChanged > 0 || dishRowsChanged > 0) {
                operationResult = "Order and its content successfully deleted.";
                connection.commit();
            } else
                operationResult = "Order with number " + orderNumber + " not found.";
        } catch (SQLException e) {
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException ex) {
                operationResult = "Database connection error: " + ex.getMessage();
            }
            operationResult = "Order deletion error: " + e.getMessage();
        }

        return operationResult;
    }
}
