package burgerator.util;
import android.content.Context;
import android.widget.Toast;

import java.util.*;


public class Burgerator {

    //map of all burgerator params
    HashMap<String, String> burgerMap;

    public Burgerator(){
        burgerMap = new HashMap<>();

        //populate map with keys
        burgerMap.put("useremail", null);
        burgerMap.put("restaurantId", null);
        burgerMap.put("burgerName", null);
        burgerMap.put("restaurantName", null);
        burgerMap.put("restaurantImageUrl", null);
        burgerMap.put("restaurantAddress", null);
        burgerMap.put("restaurantCity", null);
        burgerMap.put("restaurantZip", null);
        burgerMap.put("cheese", null);
        burgerMap.put("fries", null);
        burgerMap.put("size", null);
        burgerMap.put("bun", null);
        burgerMap.put("ratio", null);
        burgerMap.put("price", null);
        burgerMap.put("taste", null);
        burgerMap.put("ambience", null);
        burgerMap.put("friesrate", null);
        burgerMap.put("bunrate", null);
        burgerMap.put("donenessRequested", null);
        burgerMap.put("donenessReceived", null);
        burgerMap.put("presentation", null);
        burgerMap.put("toppings", null);
        burgerMap.put("sauces", null);
        burgerMap.put("juicyness", null);
        burgerMap.put("service", null);
        burgerMap.put("wycbftb", null);
        burgerMap.put("comment", null);
        burgerMap.put("image", null);
        burgerMap.put("imageUrl", null);
        burgerMap.put("date", null);
    }

    public void setVal(String _key, String _val){
        try{
            burgerMap.put(_key, _val);
        }catch(Exception e){
            System.out.println("Error adding k/v to burgerMap; " + e);
        }
    }

    public HashMap<String, String> getBurgerMap(){
        return this.burgerMap;
    }

    /**
     * Function to validate the data entered by the user
     * Requirements:
     *      Must Select: restaurant, ratio,cheese,taste,toppings, bun, WYCBFTB
     *      Must take photo
     * @param _c context to display toast in
     * @param imageUrl a url to the image the user has taken
     * @return true if validation is successful and false if not
     */
    public boolean validate(Context _c, String imageUrl){
        boolean result = false;
        String resultMessage = "";

        //TODO: uncomment and finish implmenting
        //if(validateRestaurant()){
            if(validateBurgerAttributes(imageUrl)){
                result = true;
                resultMessage = "data validated";
            }else{resultMessage = "Burger data empty";}
        //}else{resultMessage = "restaurant data empty";}

        Toast.makeText(_c,resultMessage,Toast.LENGTH_LONG).show();

        return result;
    }

    /**
     * Function to validate the burger data entered by the user
     * Requirements:
     *      Must contain: ratio,cheese,taste,toppings, bun, WYCBFTB, photo url
     * @param imageUrl a url to the image the user has taken
     * @return true if validation is successful and false if not
     */
    private boolean validateBurgerAttributes(String imageUrl){
        boolean result = false;
        if( !burgerMap.get("cheese").isEmpty() &&
            !burgerMap.get("ratio").isEmpty() &&
            !burgerMap.get("taste").isEmpty() &&
            !burgerMap.get("toppings").isEmpty() &&
            !burgerMap.get("bunrate").isEmpty() &&
            !burgerMap.get("wycbftb").isEmpty() &&
            !imageUrl.isEmpty()){
            result = true;
        }
        return result;
    }

    /**
     * Function to validate the restaurant data entered by the user
     * Requirements:
     *      Must contain: "restaurantId","burgerName","restaurantName","restaurantZip",
     *      "restaurantImageUrl","restaurantAddress","restaurantCity"
     * @return true if validation is successful and false if not
     */
    private boolean validateRestaurant(){
        boolean result = false;
        if( !burgerMap.get("restaurantId").isEmpty() &&
                !burgerMap.get("burgerName").isEmpty() &&
                !burgerMap.get("restaurantName").isEmpty() &&
                !burgerMap.get("restaurantZip").isEmpty() &&
                !burgerMap.get("restaurantImageUrl").isEmpty() &&
                !burgerMap.get("restaurantAddress").isEmpty() &&
                !burgerMap.get("restaurantCity").isEmpty()){
            result = true;
        }
        return result;
    }
}
