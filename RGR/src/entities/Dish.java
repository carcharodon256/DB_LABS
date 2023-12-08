package entities;

import java.util.ArrayList;

public class Dish {
    private int dishId;
    private String dishName;
    private int weight;
    private int price;
    private int servingsAmount;
    private int order_number;

    public Dish(int dishId, String dishName, int weight, int price, int servingsAmount, int order_number) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.weight = weight;
        this.price = price;
        this.servingsAmount = servingsAmount;
        this.order_number = order_number;
    }

    public static String completeString(String string){
        ArrayList<Character> spaces = new ArrayList<>(10);

        for (int counter = string.length(); counter < 25; counter++){
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
        return completeString(Integer.toString(dishId)) + completeString(dishName) +
                "     " + completeString(Integer.toString(weight)) + "     " +
                completeString(Integer.toString(price)) +
                completeString(Integer.toString(servingsAmount)) +
                order_number;
    }
}
