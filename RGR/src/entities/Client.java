package entities;

import java.util.ArrayList;

public class Client {
    private String clientEmailAddress;
    private String clientName;
    private String clientPhoneNumber;

    public Client(String clientEmailAddress, String clientName, String clientPhoneNumber) {
        this.clientEmailAddress = clientEmailAddress;
        this.clientName = clientName;
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public static String completeString(String string){ // method for correct table input
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
        return completeString(clientEmailAddress) + "     " +
                completeString(clientName) + "     " + completeString(clientPhoneNumber);
    }
}
