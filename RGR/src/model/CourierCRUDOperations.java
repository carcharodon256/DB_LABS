package model;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import entities.Courier;
import query_execution_time.*;

public class CourierCRUDOperations {
    public static String createRecord(Connection connection, String courierPhoneNumber, String courierName, String transportKind, double courierRating){
        String creationQuery = "INSERT INTO courier (courier_phone_number, courier_name, transport_kind, courier_rating) VALUES (?, ?, ?, ?)";

        String operationResult;

        try (PreparedStatement queryObject = connection.prepareStatement(creationQuery)) {
            queryObject.setString(1, courierPhoneNumber);
            queryObject.setString(2, courierName);
            queryObject.setString(3, transportKind);
            queryObject.setDouble(4, courierRating);

            long queryExecutionStartTime = System.currentTimeMillis();

            int rowsChanged = queryObject.executeUpdate();

            long queryExecutionEndTime = System.currentTimeMillis();

            QueryExecutionTimeMeasuring.queryExecutionTime = QueryExecutionTimeMeasuring.measureQueryExecutionTime(queryExecutionStartTime, queryExecutionEndTime);

            if (rowsChanged > 0)
                operationResult = "New record successfully created in 'courier' table.";
            else
                operationResult = "New record adding error.";
        }
        catch(SQLException e){
            operationResult = "New record adding error: " + e.getMessage();
        }
        return operationResult;
    }

    public static void generateCourierData(Connection connection, int numberOfRecords) {
        try {
            // data generation
            String insertQuery = "INSERT INTO courier (courier_phone_number, courier_name, transport_kind, courier_rating) " +
                    "SELECT " +
                    "  lpad(round(random() * 9999999999)::bigint::text, 10, '0'), " +
                    "  md5(random()::text), " +
                    "  unnest(ARRAY['Car', 'Motorbike', 'Bike']), " +
                    "  floor(random() * 10 + 1)::int " +
                    "FROM generate_series(1, ?)";

            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setInt(1, numberOfRecords);

                // query execution
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Courier> getAllRecords(Connection connection) throws SQLException{
        ArrayList<Courier> couriers = new ArrayList<>();

        String selectionQuery = "SELECT * FROM courier";

        try (PreparedStatement queryObject = connection.prepareStatement(selectionQuery)) {

            long queryExecutionStartTime = System.currentTimeMillis();

            ResultSet resultSet = queryObject.executeQuery();

            long queryExecutionEndTime = System.currentTimeMillis();
            QueryExecutionTimeMeasuring.queryExecutionTime = QueryExecutionTimeMeasuring.measureQueryExecutionTime(queryExecutionStartTime, queryExecutionEndTime);

            while (resultSet.next()) {
                String courierPhoneNumber = resultSet.getString("courier_phone_number");
                String courierName = resultSet.getString("courier_name");
                String transportKind = resultSet.getString("transport_kind");
                double courierRating = resultSet.getDouble("courier_rating");

                Courier courier = new Courier(courierPhoneNumber, courierName, transportKind, courierRating);
                couriers.add(courier);
            }
        } catch (SQLException e) {
            throw e;
        }

        return couriers;
    }

    public static ArrayList<String> getCouriersWithLessOrders(Connection connection) throws SQLException{
        ArrayList<String> couriers = new ArrayList<>();

        try {
            String selectionQuery = "SELECT courier_name " +
                    "FROM courier " +
                    "LEFT JOIN orders ON courier.courier_phone_number = orders.courier_phone_number " +
                    "GROUP BY courier_name " +
                    "ORDER BY COUNT(orders.order_number) ASC " +
                    "LIMIT 1";

            long queryExecutionStartTime = System.currentTimeMillis();
            try (PreparedStatement queryObject = connection.prepareStatement(selectionQuery);
                 ResultSet resultSet = queryObject.executeQuery()) {

                long queryExecutionEndTime = System.currentTimeMillis();
                QueryExecutionTimeMeasuring.queryExecutionTime = QueryExecutionTimeMeasuring.measureQueryExecutionTime(queryExecutionStartTime, queryExecutionEndTime);

                while (resultSet.next()) {
                    String courierName = resultSet.getString("courier_name");
                    couriers.add(courierName);
                }
            }
        } catch (SQLException e) {
            throw e;
        }

        return couriers;
    }

    public static String updateRecord(Connection connection, String courierPhoneNumber, String newTransportKind, double newCourierRating){
        String updateQuery = "UPDATE courier SET transport_kind = COALESCE(?, transport_kind), courier_rating = ? WHERE courier_phone_number = ?";
        String operationResult;

        try (PreparedStatement queryObject = connection.prepareStatement(updateQuery)) {
            queryObject.setString(1, newTransportKind);
            queryObject.setDouble(2, newCourierRating);
            queryObject.setString(3, courierPhoneNumber);

            long queryExecutionStartTime = System.currentTimeMillis();

            int rowsChanged = queryObject.executeUpdate();

            long queryExecutionEndTime = System.currentTimeMillis();
            QueryExecutionTimeMeasuring.queryExecutionTime = QueryExecutionTimeMeasuring.measureQueryExecutionTime(queryExecutionStartTime, queryExecutionEndTime);

            if (rowsChanged > 0) {
                operationResult = "Record successfully updated in 'courier' table.";
            } else {
                operationResult = "Courier with phone number " + courierPhoneNumber + " not found.";
            }
        } catch (SQLException e) {
            operationResult = "Courier update error: " + e.getMessage();
        }

        return operationResult;
    }

    public static String deleteRecord(Connection connection, String courierPhoneNumber){
        String updateOrdersQuery = "UPDATE orders " +
                "SET courier_phone_number = (SELECT courier_phone_number " +
                "FROM courier " +
                "ORDER BY (SELECT COUNT(*) " +
                "FROM orders AS o " +
                "WHERE o.courier_phone_number = courier.courier_phone_number) " +
                "LIMIT 1) " +
                "WHERE courier_phone_number = ?";

        String deleteCourierQuery = "DELETE FROM courier WHERE courier_phone_number = ?";

        String operationResult;

        try (PreparedStatement updateOrdersRecords = connection.prepareStatement(updateOrdersQuery);
             PreparedStatement deleteCourierRecord = connection.prepareStatement(deleteCourierQuery)) {
            connection.setAutoCommit(false);

            updateOrdersRecords.setString(1, courierPhoneNumber);
            int ordersRowsChanged = updateOrdersRecords.executeUpdate();

            deleteCourierRecord.setString(1, courierPhoneNumber);

            long queryExecutionStartTime = System.currentTimeMillis();

            int courierRowsChanged = deleteCourierRecord.executeUpdate();

            long queryExecutionEndTime = System.currentTimeMillis();
            QueryExecutionTimeMeasuring.queryExecutionTime = QueryExecutionTimeMeasuring.measureQueryExecutionTime(queryExecutionStartTime, queryExecutionEndTime);

            if (ordersRowsChanged > 0) {
                operationResult = "Orders successfully updated.";
            } else {
                operationResult = "Orders updating error.";
            }

            if (courierRowsChanged > 0) {
                operationResult = "Courier successfully deleted.";
                connection.commit();
            } else {
                operationResult = "Courier with phone number " + courierPhoneNumber + " not found.";
            }
        } catch (SQLException e) {
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException ex) {
                operationResult = "Database connection error: " + ex.getMessage();
            }
            operationResult = "Courier deletion error: " + e.getMessage();
        }

        return operationResult;
    }
}
