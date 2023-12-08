package view;

import java.sql.Timestamp;
import java.util.*;
import entities.Client;
import entities.Courier;
import entities.Dish;
import entities.Order;
import query_execution_time.*;

public class InfoInputOutput {

    // printing resulting message
    public static void printMessage(String message) {
        System.out.println(message);
    }

    public static void printQueryExecutionTime(){ // printing query execution time
        System.out.print("Execution time of this query: ");
        System.out.print(QueryExecutionTimeMeasuring.queryExecutionTime);
        System.out.println(" milliseconds");
    }

    public static int readMenuItem() { // reading menu item
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        return number;
    }

    public static void printMainMenu() {
        System.out.println("THIS IS APPLICATION FOR WORK");
        System.out.println("WITH DELIVERY DATABASE");
        System.out.println("HAVE A NICE WORK!");
        System.out.println();
        System.out.println();
        System.out.println("MAIN MENU");
        System.out.println();
        System.out.println("CHOOSE TABLE, WHAT YOU WANT TO WORK WITH OR OPTION");
        System.out.println("(PRESS NECESSARY NUMBER ON YOUR KEYBOARD)");
        System.out.println();
        System.out.println("1. CLIENTS TABLE");
        System.out.println("2. DISHES TABLE");
        System.out.println("3. ORDERS TABLE");
        System.out.println("4. COURIERS TABLE");
        System.out.println();
    }

    public static void printItem1FromMainMenu() {
        System.out.println("YOU HAVE SELECTED A CLIENTS TABLE");
        System.out.println();
        System.out.println("CHOOSE OPERATION YOU WANT TO DO");
        System.out.println();
        System.out.println("(PRESS NECESSARY NUMBER ON YOUR KEYBOARD)");
        System.out.println();
        System.out.println("1. ADD NEW CLIENT");
        System.out.println("2. GET LIST OF CLIENTS");
        System.out.println("3. GET LIST OF CLIENTS, WHO MADE THE BIGGEST ORDER");
        System.out.println("4. UPDATE CLIENT INFORMATION");
        System.out.println("5. DELETE CLIENT");
    }

    public static void printItem2FromMainMenu() {
        System.out.println("YOU HAVE SELECTED A DISHES TABLE");
        System.out.println();
        System.out.println("CHOOSE OPERATION YOU WANT TO DO");
        System.out.println();
        System.out.println("(PRESS NECESSARY NUMBER ON YOUR KEYBOARD)");
        System.out.println();
        System.out.println("1. ADD NEW DISH");
        System.out.println("2. GET LIST OF DISHES");
        System.out.println("3. UPDATE DISH INFORMATION");
        System.out.println("4. DELETE DISH");
    }

    public static void printItem3FromMainMenu() {
        System.out.println("YOU HAVE SELECTED AN ORDERS TABLE");
        System.out.println();
        System.out.println("CHOOSE OPERATION YOU WANT TO DO");
        System.out.println();
        System.out.println("(PRESS NECESSARY NUMBER ON YOUR KEYBOARD)");
        System.out.println();
        System.out.println("1.ADD NEW ORDER");
        System.out.println("2. GET LIST OF ORDERS");
        System.out.println("3. UPDATE ORDER INFORMATION");
        System.out.println("4. DELETE ORDER");
    }

    public static void printItem4FromMainMenu() {
        System.out.println("YOU HAVE SELECTED A COURIERS TABLE");
        System.out.println();
        System.out.println("CHOOSE OPERATION YOU WANT TO DO");
        System.out.println();
        System.out.println("(PRESS NECESSARY NUMBER ON YOUR KEYBOARD)");
        System.out.println("1.ADD NEW COURIER");
        System.out.println("2. GET LIST OF COURIERS");
        System.out.println("3. GET LIST OF COURIERS, WHO GOT THE LEAST AMOUNT OF ORDERS");
        System.out.println("4. UPDATE COURIER INFORMATION");
        System.out.println("5. DELETE COURIER");
        System.out.println("6. GENERATE RANDOM COURIERS DATA");
    }

    public static String[] printSubItem1FromItem1() {
        String[] clientData = new String[3];

        System.out.println("ADDING NEW CLIENT");
        System.out.println();
        System.out.print("INPUT CLIENT EMAIL ADDRESS (PRESS 'ENTER' AFTER INPUT): ");
        Scanner scanner = new Scanner(System.in);

        clientData[0] = scanner.nextLine();
        System.out.print("INPUT CLIENT NAME (PRESS 'ENTER' AFTER INPUT): ");
        clientData[1] = scanner.nextLine();

        System.out.print("INPUT CLIENT PHONE NUMBER (PRESS 'ENTER' AFTER INPUT): ");
        clientData[2] = scanner.nextLine();

        return clientData;
    }

    public static void printSubItem2FromItem1(ArrayList<Client> list){
        //get list of clients
        int clientNumber = 1;

        System.out.println("LIST OF EXISTING CLIENTS");
        System.out.println();
        System.out.println();
        System.out.println(" Number         Client email address                 Client name               Client phone number");

        for (Client counter: list) {
            System.out.println("   " + clientNumber + "          " + counter.toString());
            clientNumber++;
        }
    }

    public static void printSubItem3FromItem1(ArrayList <String> list){
        System.out.println();
        System.out.println("LIST OF CLIENTS WHO MADE THE BIGGEST ORDERS");
        System.out.println();

        for (String counter: list) {
            System.out.println(counter.toString());
        }
    }

    public static String[] printSubItem4FromItem1() {
        String[] updatedClient = new String[3];

        System.out.println("CLIENT UPDATE");
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("INPUT EMAIL ADDRESS OF CLIENT, YOU WANT TO UPDATE (PRESS 'ENTER' AFTER INPUT): ");
        updatedClient[0] = scanner.nextLine();


        System.out.print("INPUT NEW CLIENT NAME. IF YOU DON'T WANT TO CHANGE IT, PUT -. PRESS 'ENTER' AFTER INPUT");
        updatedClient[1] = scanner.nextLine();
        System.out.print("INPUT NEW CLIENT PHONE NUMBER. IF YOU DON'T WANT TO CHANGE IT, PUT -. PRESS 'ENTER' AFTER INPUT");
        updatedClient[2] = scanner.nextLine();

        return updatedClient;
    }

    public static String printSubItem5FromItem1() {
        String clientEmailAddress;

        System.out.println("CLIENT DELETION");
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("INPUT EMAIL ADDRESS OF CLIENT, YOU WANT TO DELETE (PRESS 'ENTER' AFTER INPUT): ");
        clientEmailAddress = scanner.nextLine();

        return clientEmailAddress;
    }

    public static String[] printSubItem1FromItem2(){
        String[] dishData = new String[6];

        System.out.println("ADDING NEW DISH");
        System.out.println();
        System.out.print("INPUT DISH ID (PRESS 'ENTER' AFTER INPUT): ");
        Scanner scanner = new Scanner(System.in);
        dishData[0] = scanner.nextLine();

        System.out.print("INPUT DISH NAME (PRESS 'ENTER' AFTER INPUT): ");
        dishData[1] = scanner.nextLine();

        System.out.print("INPUT DISH WEIGHT (PRESS 'ENTER' AFTER INPUT): ");
        dishData[2] = scanner.nextLine();

        System.out.print("INPUT DISH PRICE (PRESS 'ENTER' AFTER INPUT): ");
        dishData[3] = scanner.nextLine();

        System.out.print("INPUT DISH SERVINGS AMOUNT (PRESS 'ENTER' AFTER INPUT): ");
        dishData[4] = scanner.nextLine();

        System.out.print("INPUT DISH ORDER NUMBER (PRESS 'ENTER' AFTER INPUT): ");
        dishData[5] = scanner.nextLine();

        return dishData;
    }

    public static void printSubItem2FromItem2(ArrayList<Dish> list){
        int dishNumber = 1;

        System.out.println("LIST OF EXISTING DISHES");
        System.out.println();
        System.out.println();
        System.out.println("Number       Dish id                     Dish name                 Weight                        Price               Servings amount          Order number");

        for (Dish counter: list) {
            System.out.println("  " + dishNumber + "          " + counter.toString());
            dishNumber++;
        }
    }

    public static String[] printSubItem3FromItem2(){
        String[] updatedDish = new String[5];

        System.out.println("DISH UPDATE");
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("INPUT ID OF DISH, YOU WANT TO UPDATE (PRESS 'ENTER' AFTER INPUT): ");
        updatedDish[0] = scanner.nextLine();

        System.out.print("INPUT NEW DISH NAME. IF YOU DON'T WANT TO CHANGE IT, PUT -. PRESS 'ENTER' AFTER INPUT");
        updatedDish[1] = scanner.nextLine();

        System.out.print("INPUT NEW DISH WEIGHT. IF YOU DON'T WANT TO CHANGE IT, PUT -. PRESS 'ENTER' AFTER INPUT");
        updatedDish[2] = scanner.nextLine();

        System.out.print("INPUT NEW DISH PRICE. IF YOU DON'T WANT TO CHANGE IT, PUT -. PRESS 'ENTER' AFTER INPUT");
        updatedDish[3] = scanner.nextLine();

        System.out.print("INPUT NEW DISH SERVINGS AMOUNT. IF YOU DON'T WANT TO CHANGE IT, PUT -. PRESS 'ENTER' AFTER INPUT");
        updatedDish[4] = scanner.nextLine();

        return updatedDish;
    }

    public static String printSubItem4FromItem2(){
        String dishId;

        System.out.println("DISH DELETION");
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("INPUT ID OF DISH, YOU WANT TO DELETE (PRESS 'ENTER' AFTER INPUT): ");
        dishId = scanner.nextLine();

        return dishId;
    }

    public static String[] printSubItem1FromItem3() {
        String[] orderData = new String[5];

        System.out.println("ADDING NEW ORDER");
        System.out.println();
        System.out.print("INPUT ORDER NUMBER (PRESS 'ENTER' AFTER INPUT): ");
        Scanner scanner = new Scanner(System.in);
        orderData[0] = scanner.nextLine();

        System.out.print("INPUT DELIVERY ADDRESS (PRESS 'ENTER' AFTER INPUT): ");
        orderData[1] = scanner.nextLine();

        System.out.print("INPUT DELIVERY DATE AND TIME (PRESS 'ENTER' AFTER INPUT): ");
        orderData[2] = scanner.nextLine();

        System.out.print("INPUT CLIENT EMAIL ADDRESS (PRESS 'ENTER' AFTER INPUT): ");
        orderData[3] = scanner.nextLine();

        System.out.print("INPUT COURIER PHONE NUMBER (PRESS 'ENTER' AFTER INPUT): ");
        orderData[4] = scanner.nextLine();

        return orderData;
    }

    public static void printSubItem2FromItem3(ArrayList<Order> list){
        int orderNumber = 1;

        System.out.println("LIST OF EXISTING ORDERS");
        System.out.println();
        System.out.println();
        System.out.println("Number          Order number                     Delivery address             Delivery date and time          Clirnt email address          Courier phone number");

        for (Order counter: list) {
            System.out.println("  " + orderNumber + "          " + counter.toString());
            orderNumber++;
        }
    }

    public static String[] printSubItem3FromItem3(){
        String[] updatedOrder = new String[3];

        System.out.println("ORDER UPDATE");
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("INPUT NUMBER OF ORDER, YOU WANT TO UPDATE (PRESS 'ENTER' AFTER INPUT): ");
        updatedOrder[0] = scanner.nextLine();


        System.out.print("INPUT NEW DELIVERY ADDRESS. IF YOU DON'T WANT TO CHANGE IT, PUT -. PRESS 'ENTER' AFTER INPUT");
        updatedOrder[1] = scanner.nextLine();
        System.out.print("INPUT NEW DELIVERY DATE AND TIME. IF YOU DON'T WANT TO CHANGE IT, PUT -. PRESS 'ENTER' AFTER INPUT");
        updatedOrder[2] = scanner.nextLine();

        return updatedOrder;
    }

    public static String printSubItem4FromItem3(){
        String orderNumber;

        System.out.println("ORDER DELETION");
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("INPUT NUMBER OF ORDER, YOU WANT TO DELETE (PRESS 'ENTER' AFTER INPUT): ");
        orderNumber = scanner.nextLine();

        return orderNumber;
    }

    public static String[] printSubItem1FromItem4() {
        String[] courierData = new String[4];

        System.out.println("ADDING NEW COURIER");
        System.out.println();
        System.out.print("INPUT COURIER PHONE NUMBER (PRESS 'ENTER' AFTER INPUT): ");
        Scanner scanner = new Scanner(System.in);
        courierData[0] = scanner.nextLine();

        System.out.print("INPUT COURIER NAME (PRESS 'ENTER' AFTER INPUT): ");
        courierData[1] = scanner.nextLine();
        System.out.print("INPUT COURIER TRANSPORT KIND. USE ONLY LOWERCASE LETTERS. (PRESS 'ENTER' AFTER INPUT): ");
        courierData[2] = scanner.nextLine();

        System.out.print("INPUT COURIER RATING (PRESS 'ENTER' AFTER INPUT): ");
        courierData[3] = scanner.nextLine();

        return courierData;
    }

    public static void printSubItem2FromItem4(ArrayList<Courier> list){
        int courierNumber = 1;

        System.out.println("LIST OF EXISTING COURIERS");
        System.out.println();
        System.out.println();
        System.out.println("Number          Courier phone number          Courier first name          Courier transport kind          Courier rating");

        for (Courier counter: list) {
            System.out.println("  " + courierNumber + "                  " + counter.toString());
            courierNumber++;
        }
    }

    public static void printSubItem3FromItem4(ArrayList<String> list){
        System.out.println();
        System.out.println("LIST OF COURIERS WHO TOOK THE LEAST AMOUNT OF ORDERS");
        System.out.println();

        for (String counter: list) {
            System.out.println(counter.toString());
        }
    }

    public static String[] printSubItem4FromItem4() {
        String[] updatedCourier = new String[3];

        System.out.println("COURIER UPDATE");
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("INPUT PHONE NUMBER OF COURIER, YOU WANT TO UPDATE (PRESS 'ENTER' AFTER INPUT): ");
        updatedCourier[0] = scanner.nextLine();

        System.out.print("INPUT NEW KIND OF COURIER TRANSPORT. USE ONLY LOWERCASE LETTERS. IF YOU DON'T WANT TO CHANGE IT, PUT -. PRESS 'ENTER' AFTER INPUT");
        updatedCourier[1] = scanner.nextLine();

        System.out.print("INPUT NEW VALUE OF COURIER RATING. IF YOU DON'T WANT TO CHANGE IT, PUT -. PRESS 'ENTER' AFTER INPUT");
        updatedCourier[2] = scanner.nextLine();

        return updatedCourier;
    }

    public static String printSubItem5FromItem4(){
        String courierNumber;

        System.out.println("COURIER DELETION");
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("INPUT PHONE NUMBER OF COURIER, YOU WANT TO DELETE (PRESS 'ENTER' AFTER INPUT): ");
        courierNumber = scanner.nextLine();

        return courierNumber;
    }

    public static int printSubItem6FromItem4(){
        System.out.println("INPUT NUMBER OF RECORDS YOU WANT TO ADD INTO COURIERS TABLE: ");
        Scanner scanner = new Scanner(System.in);
        String inputRecordsNumber = scanner.nextLine();
        int recordsNumber = Integer.parseInt(inputRecordsNumber);
        return recordsNumber;
    }
}
