package controller;

import java.sql.*;

import entities.*;
import view.*;
import model.*;

import java.sql.Connection;
import java.util.ArrayList;

public class UserMenu {
    public static void runProgram(){

        DatabaseConnection dbConnection= new DatabaseConnection();
        Connection connection = dbConnection.connectToDatabase(); // connection to database

        InfoInputOutput.printMainMenu(); // printing main menu items
        int menuItem = InfoInputOutput.readMenuItem();

        switch (menuItem){ // menu items input handling
            case 1: // main menu item 1 handling
                InfoInputOutput.printItem1FromMainMenu();
                int subItem1 = InfoInputOutput.readMenuItem();
                switch (subItem1){
                    case 1: // adding new record to clients table
                        String[] clientData = new String[3];
                        clientData = InfoInputOutput.printSubItem1FromItem1();
                        String clientEmailAddress = clientData[0];
                        String clientName = clientData[1];
                        String clientPhoneNumber = clientData[2];
                        String clientCreationResult = ClientCRUDOperations.createRecord(connection, clientEmailAddress, clientName, clientPhoneNumber);
                        InfoInputOutput.printMessage(clientCreationResult);
                        System.out.println();
                        InfoInputOutput.printQueryExecutionTime();
                        break;

                    case 2: // printing list of existing clients
                        try {
                           ArrayList<Client> existingClients = ClientCRUDOperations.getAllRecords(connection);
                            InfoInputOutput.printSubItem2FromItem1(existingClients);
                        } catch (SQLException e){
                            InfoInputOutput.printMessage(e.getMessage());
                        }
                        System.out.println();
                        InfoInputOutput.printQueryExecutionTime();
                        break;

                    case 3: // printing list of clients with the biggest order
                        try {
                            ArrayList<String> clientsWithTheBiggestOrder = ClientCRUDOperations.getClientsWithMostOrders(connection);
                            InfoInputOutput.printSubItem3FromItem1(clientsWithTheBiggestOrder);
                        }
                        catch (SQLException e){
                            InfoInputOutput.printMessage(e.getMessage());
                        }
                        System.out.println();
                        InfoInputOutput.printQueryExecutionTime();
                        break;

                    case 4: // updating client data
                        String[] updatingClientData = new String[3];
                        updatingClientData = InfoInputOutput.printSubItem1FromItem1();
                        String clientEmail = updatingClientData[0];

                        String updatedClientName;
                        // checking if user input "-" in order to skip this field updating
                        if(updatingClientData[1] == "-")
                            updatedClientName = null;
                        else
                        updatedClientName = updatingClientData[1];

                        String updatedClientPhoneNumber;
                        if(updatingClientData[2] == "-")
                            updatedClientPhoneNumber = null;
                        else
                        updatedClientPhoneNumber = updatingClientData[2];

                        String clientUpdateResult = ClientCRUDOperations.createRecord(connection, clientEmail, updatedClientName, updatedClientPhoneNumber);
                        InfoInputOutput.printMessage(clientUpdateResult);
                        break;

                    case 5: // client record deletion
                        String deletedClientEmail = InfoInputOutput.printSubItem5FromItem1();
                        String deletionResult = ClientCRUDOperations.deleteRecord(connection, deletedClientEmail);
                        InfoInputOutput.printMessage(deletionResult);
                        System.out.println();
                        InfoInputOutput.printQueryExecutionTime();
                        break;
                }
                break;

            case 2: // main menu item 2 handling
                InfoInputOutput.printItem2FromMainMenu();
                int subItem2 = InfoInputOutput.readMenuItem();
                switch (subItem2){
                    case 1: // adding new record to dish table
                        String[] dishData = new String[6];
                        dishData = InfoInputOutput.printSubItem1FromItem2();
                        int dishId = Integer.parseInt(dishData[0]);
                        String dishName = dishData[1];
                        int dishWeight = Integer.parseInt(dishData[2]);
                        int dishPrice = Integer.parseInt(dishData[3]);
                        int dishServingsAmount = Integer.parseInt(dishData[4]);
                        int dishOrderNumber = Integer.parseInt(dishData[5]);
                        String dishCreationResult = DishCRUDOperations.createRecord(connection, dishId, dishName, dishWeight, dishPrice, dishServingsAmount, dishOrderNumber);
                        InfoInputOutput.printMessage(dishCreationResult);
                        System.out.println();
                        InfoInputOutput.printQueryExecutionTime();
                        break;

                    case 2: // printing list of existing dishes
                        try {
                            ArrayList<Dish> existingDishes = DishCRUDOperations.getAllRecords(connection);
                            InfoInputOutput.printSubItem2FromItem2(existingDishes);
                        } catch (SQLException e){
                            InfoInputOutput.printMessage(e.getMessage());
                        }
                        System.out.println();
                        InfoInputOutput.printQueryExecutionTime();
                        break;

                    case 3: // updating dish data
                        String[] updatingDishData = new String[6];
                        updatingDishData = InfoInputOutput.printSubItem3FromItem2();
                        int updatingDishId = Integer.parseInt(updatingDishData[0]);

                        String newDishName;
                        if(updatingDishData[1] == "-")
                            newDishName = null;
                        else
                         newDishName = updatingDishData[1];

                        int newDishWeight;
                        if(updatingDishData[2] == "-")
                            newDishWeight = 0;
                        else
                        newDishWeight = Integer.parseInt(updatingDishData[2]);

                        int newDishPrice;
                        if(updatingDishData[3] == "-")
                            newDishPrice = 0;
                        else
                        newDishPrice = Integer.parseInt(updatingDishData[3]);

                        int newDishServingsAmount;
                        if(updatingDishData[4] == "-")
                            newDishServingsAmount = 0;
                        else
                        newDishServingsAmount = Integer.parseInt(updatingDishData[4]);

                        String dishUpdateResult = DishCRUDOperations.updateRecord(connection, updatingDishId, newDishName, newDishWeight, newDishPrice, newDishServingsAmount);
                        InfoInputOutput.printMessage(dishUpdateResult);
                        System.out.println();
                        InfoInputOutput.printQueryExecutionTime();
                        break;

                    case 4: // dish record deletion
                        String deletedDishId = InfoInputOutput.printSubItem4FromItem2();
                        int integerDishId = Integer.parseInt(deletedDishId);
                        String dishDeletionResult = DishCRUDOperations.deleteRecord(connection, integerDishId);
                        InfoInputOutput.printMessage(dishDeletionResult);
                        System.out.println();
                        InfoInputOutput.printQueryExecutionTime();
                        break;
                }
                break;

            case 3: // main menu item 3 handling
                InfoInputOutput.printItem3FromMainMenu();
                int subItem3 = InfoInputOutput.readMenuItem();
                switch (subItem3){
                    case 1: // adding new record to order table
                        String[] orderData = new String[5];
                        orderData = InfoInputOutput.printSubItem1FromItem3();
                        int orderNumber = Integer.parseInt(orderData[0]);
                        String deliveryAddress = orderData[1];
                        Timestamp deliveryDateAndTime = Timestamp.valueOf(orderData[2]);
                        String clientEmailAddress = orderData[3];
                        String courierPhoneNumber = orderData[4];
                        String orderCreationResult = OrderCRUDOperations.createRecord(connection, orderNumber, deliveryAddress, deliveryDateAndTime, clientEmailAddress, courierPhoneNumber);
                        InfoInputOutput.printMessage(orderCreationResult);
                        System.out.println();
                        InfoInputOutput.printQueryExecutionTime();
                        break;

                    case 2: // printing list of existing orders
                        try {
                            ArrayList<Order> existingOrders = OrderCRUDOperations.getAllRecords(connection);
                            InfoInputOutput.printSubItem2FromItem3(existingOrders);
                        } catch (SQLException e){
                            InfoInputOutput.printMessage(e.getMessage());
                        }
                        System.out.println();
                        InfoInputOutput.printQueryExecutionTime();
                        break;

                    case 3: // updating order data
                        String[] updatingOrderData = new String[5];
                        updatingOrderData = InfoInputOutput.printSubItem3FromItem3();

                        int integerOrderNumber = Integer.parseInt(updatingOrderData[0]);

                        String updatedDeliveryAddress;
                        if(updatingOrderData[1] == "-")
                            updatedDeliveryAddress = null;
                        else
                         updatedDeliveryAddress = updatingOrderData[1];

                        Timestamp deliveryDateTime;
                        if(updatingOrderData[2] == "-")
                            deliveryDateTime = null;
                        else
                        deliveryDateTime = Timestamp.valueOf(updatingOrderData[2]);

                        String clientEmail = updatingOrderData[3];
                        String courierPhone = updatingOrderData[4];
                        String orderUpdateResult = OrderCRUDOperations.updateRecord(connection, integerOrderNumber, updatedDeliveryAddress, deliveryDateTime);
                        InfoInputOutput.printMessage(orderUpdateResult);
                        System.out.println();
                        InfoInputOutput.printQueryExecutionTime();
                        break;

                    case 4: // deletion order record
                        String deletedOrderNumber = InfoInputOutput.printSubItem4FromItem3();
                        int integerOrderNum = Integer.parseInt(deletedOrderNumber);
                        String orderDeletionResult = OrderCRUDOperations.deleteRecord(connection, integerOrderNum);
                        InfoInputOutput.printMessage(orderDeletionResult);
                        System.out.println();
                        InfoInputOutput.printQueryExecutionTime();
                        break;
                }
                break;

            case 4: // main menu item 4 handling
                InfoInputOutput.printItem4FromMainMenu();
                int subItem4 = InfoInputOutput.readMenuItem();
                switch (subItem4){
                    case 1: // adding new record to courier table
                        String[] couriersData = new String[4];
                        couriersData = InfoInputOutput.printSubItem1FromItem4();
                        String courierPhoneNumber = couriersData[0];
                        String courierName = couriersData[1];
                        String courierTransportKind = couriersData[2];
                        double courierRating = Double.parseDouble(couriersData[3]);
                        String courierCreationResult = CourierCRUDOperations.createRecord(connection, courierPhoneNumber, courierName, courierTransportKind, courierRating);
                        InfoInputOutput.printMessage(courierCreationResult);
                        System.out.println();
                        InfoInputOutput.printQueryExecutionTime();
                        break;

                    case 2: // printing list of existing couriers
                        try {
                            ArrayList<Courier> existingCouriers = CourierCRUDOperations.getAllRecords(connection);
                            InfoInputOutput.printSubItem2FromItem4(existingCouriers);
                        } catch (SQLException e){
                            InfoInputOutput.printMessage(e.getMessage());
                        }
                        System.out.println();
                        InfoInputOutput.printQueryExecutionTime();
                        break;

                    case 3: // printing list of couriers, who took the least amount of orders
                        try {
                            ArrayList<String> couriersWithLeastOrdersAmount = CourierCRUDOperations.getCouriersWithLessOrders(connection);
                            InfoInputOutput.printSubItem3FromItem4(couriersWithLeastOrdersAmount);
                        }
                        catch (SQLException e){
                            InfoInputOutput.printMessage(e.getMessage());
                        }
                        System.out.println();
                        InfoInputOutput.printQueryExecutionTime();
                        break;

                    case 4: // updating courier data
                        String[] updatingCourierData = new String[3];
                        updatingCourierData = InfoInputOutput.printSubItem4FromItem4();

                        String updatingCourierPhoneNumber = updatingCourierData[0];

                        String newCourierTransportKind;
                        if(updatingCourierData[1] == "-")
                            newCourierTransportKind = null;
                        else
                            newCourierTransportKind = updatingCourierData[1];

                        double newCourierRating;
                        if(updatingCourierData[2] == "-")
                            newCourierRating = 0;
                        else
                            newCourierRating = Double.parseDouble(updatingCourierData[2]);

                        String courierUpdateResult = CourierCRUDOperations.updateRecord(connection, updatingCourierPhoneNumber, newCourierTransportKind, newCourierRating);
                        InfoInputOutput.printMessage(courierUpdateResult);
                        System.out.println();
                        InfoInputOutput.printQueryExecutionTime();
                        break;

                    case 5: // courier record deletion
                        String deletedCourierPhoneNumber = InfoInputOutput.printSubItem5FromItem4();
                        String courierDeletionResult = CourierCRUDOperations.deleteRecord(connection, deletedCourierPhoneNumber);
                        InfoInputOutput.printMessage(courierDeletionResult);
                        System.out.println();
                        InfoInputOutput.printQueryExecutionTime();
                        break;

                    case 6:
                        int recordsNumber = InfoInputOutput.printSubItem6FromItem4();
                        CourierCRUDOperations.generateCourierData(connection, recordsNumber);
                        InfoInputOutput.printMessage(recordsNumber + " successfully added into couriers table.");
                        break;
                }
                break;
        }

        dbConnection.disconnectFromDatabase();
    }
}
