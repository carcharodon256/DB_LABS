package model;

import java.sql.*;
import java.util.ArrayList;

import entities.Dish;
import query_execution_time.*;

public class DishCRUDOperations {
    public static String createRecord(Connection connection, int dishId, String dishName, int weight, int price, int servingsAmount, int orderNumber){
        String creationQuery = "INSERT INTO dish (dish_id, dish_name, weight, price, servings_amount, order_number) VALUES (?, ?, ?, ?, ?, ?)";

        String operationResult;

        try (PreparedStatement quwryObject = connection.prepareStatement(creationQuery)){
            quwryObject.setInt(1, dishId);
            quwryObject.setString(2, dishName);
            quwryObject.setInt(3, weight);
            quwryObject.setInt(4, price);
            quwryObject.setInt(5, servingsAmount);
            quwryObject.setInt(6, orderNumber);

            long queryExecutionStartTime = System.currentTimeMillis();

            int rowsChanged = quwryObject.executeUpdate();

            long queryExecutionEndTime = System.currentTimeMillis();

            QueryExecutionTimeMeasuring.queryExecutionTime = QueryExecutionTimeMeasuring.measureQueryExecutionTime(queryExecutionStartTime, queryExecutionEndTime);

            if (rowsChanged > 0) {
                operationResult = "New record successfully created in 'dish' table.";
            } else {
                operationResult = "New record adding error.";
            }
        } catch (SQLException e) {
            operationResult = "New record adding error: " + e.getMessage();
        }

        return operationResult;
    }

    public static ArrayList<Dish> getAllRecords(Connection connection) throws SQLException{
        ArrayList<Dish> dishes = new ArrayList<>();

        String selectionQuery = "SELECT * FROM dish";

        try (PreparedStatement queryObject = connection.prepareStatement(selectionQuery)) {

            long queryExecutionStartTime = System.currentTimeMillis();

            ResultSet resultSet = queryObject.executeQuery();

            long queryExecutionEndTime = System.currentTimeMillis();

            QueryExecutionTimeMeasuring.queryExecutionTime = QueryExecutionTimeMeasuring.measureQueryExecutionTime(queryExecutionStartTime, queryExecutionEndTime);

            while (resultSet.next()){
                int dishId = resultSet.getInt("dish_id");
                String dishName = resultSet.getString("dish_name");
                int weight = resultSet.getInt("weight");
                int price = resultSet.getInt("price");
                int servingsAmount = resultSet.getInt("servings_amount");
                int orderNumber = resultSet.getInt("order_number");

                Dish dish = new Dish(dishId, dishName, weight, price, servingsAmount, orderNumber);
                dishes.add(dish);
            }
        } catch (SQLException e) {
            throw e;
        }

        return dishes;
    }

    public static String updateRecord(Connection connection, int dishId, String newDishName, int newWeight, int newPrice, int newServingsAmount){
        String updateQuery = "UPDATE dish SET dish_name = COALESCE(?, dish_name), weight = COALESCE(?, weight), price = COALESCE(?, price), servings_amount = COALESCE(?, servings_amount) WHERE dish_id = ?";

        String operationResult;

        try (PreparedStatement queryObject = connection.prepareStatement(updateQuery)) {
            queryObject.setString(1, newDishName);
            queryObject.setInt(2, newWeight);
            queryObject.setInt(3, newPrice);
            queryObject.setInt(4, newServingsAmount);
            queryObject.setInt(5, dishId);

            long queryExecutionStartTime = System.currentTimeMillis();

            int rowsChanged = queryObject.executeUpdate();

            long queryExecutionEndTime = System.currentTimeMillis();

            QueryExecutionTimeMeasuring.queryExecutionTime = QueryExecutionTimeMeasuring.measureQueryExecutionTime(queryExecutionStartTime, queryExecutionEndTime);

            if (rowsChanged > 0) {
                operationResult = "Record successfully updated in 'dish' table.";
            } else {
                operationResult = "Dish with dish ID " + dishId + " not found.";
            }
        } catch (SQLException e) {
            operationResult = "Record update error: " + e.getMessage();
        }

        return operationResult;
    }

    public static String deleteRecord(Connection connection, int dishId){
        String deletionQuery = "DELETE FROM dish WHERE dish_id = ?";

        String operationResult;

        try(PreparedStatement deleteDishRecord = connection.prepareStatement(deletionQuery)) {
            deleteDishRecord.setInt(1, dishId);

            long queryExecutionStartTime = System.currentTimeMillis();

            int dishChangedRows = deleteDishRecord.executeUpdate();

            long queryExecutionEndTime = System.currentTimeMillis();

            QueryExecutionTimeMeasuring.queryExecutionTime = QueryExecutionTimeMeasuring.measureQueryExecutionTime(queryExecutionStartTime, queryExecutionEndTime);

            if(dishChangedRows > 0)
                operationResult = "Dish successfully deleted.";
            else
                operationResult = "Dish with dish_id " + dishId + " not found.";

        } catch (SQLException e) {
            operationResult = "Dish deletion error: " + e.getMessage();
        }

        return operationResult;
    }
}
