package entities;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Order {
    private int orderNumber;
    private String deliveryAddress;
    private Timestamp deliveryDateTime;
    private String clientEmailAddress;
    private String courierPhoneNumber;

    public Order(int orderNumber, String deliveryAddress, Timestamp deliveryDateTime, String clientEmailAddress, String courierPhoneNumber) {
        this.orderNumber = orderNumber;
        this.deliveryAddress = deliveryAddress;
        this.deliveryDateTime = deliveryDateTime;
        this.clientEmailAddress = clientEmailAddress;
        this.courierPhoneNumber = courierPhoneNumber;
    }

    public static String completeString(String string){
        ArrayList<Character> spaces = new ArrayList<>(10);

        for (int counter = string.length(); counter < 30; counter++){
            spaces.add(' ');
        }

        int spacesNumber = spaces.size();
        char[] spacesArray = new char[spacesNumber];

        int charIndex = 0;
        for (Character counter: spaces){
            spacesArray[charIndex] = counter;
            charIndex++;
        }

        String spacesString = new String(spacesArray);
        String resultingstring = string + spacesString;

        return resultingstring;
    }

    @Override
    public String toString(){
        return "      " + completeString(Integer.toString(orderNumber)) + completeString(deliveryAddress) +
                completeString(deliveryDateTime.toString()) + completeString(clientEmailAddress) +
                completeString(courierPhoneNumber);
    }
}
