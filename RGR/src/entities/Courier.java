package entities;

import java.util.ArrayList;

public class Courier {
    private String courierPhoneNumber;
    private String courierName;
    private String transportKind;
    private double courierRating;

    public Courier(String courierPhoneNumber, String courierFirstName, String transportKind, double courier_rating) {
        this.courierPhoneNumber = courierPhoneNumber;
        this.courierName = courierFirstName;
        this.transportKind = transportKind;
        this.courierRating = courier_rating;
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
    public String toString() {
        return completeString(courierPhoneNumber) + completeString(courierName) + completeString(transportKind)
                + completeString(Double.toString(courierRating));
    }
}
