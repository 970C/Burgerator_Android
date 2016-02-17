package burgerator.util;

//package burgeratorBackend;

import android.util.Log;

import org.json.JSONObject;



public class User {
    //User class is a persistent wrapper for a jsonobject within a context of User

    private JSONObject json;

    //remove when controller ready
    private static final User USER = new User();

    public User(){

        //this.json = new JSONObject();
        /*try {
            json.put("useremail", "not set");
        } catch (JSONException e) {
            Log.e("Burgerator","Cant put the useremail");
        }*/
    }

    public static User instance(){
        return USER;
    }


    public void setUser(JSONObject j){
        this.json = j;
    }

    //getters for email, usesrname, quote...

    //return a string value for a particular key if it exists
    private String getVal(String k){
        String result = "";
        String l = "User: get " + k;
        try{
            result = json.getJSONObject("result").getJSONObject("content").getString(k);
            //Log.getInstance().addLog(l + " success: " + result + "\n");
            //return result;
        }catch (Exception e){
            //Log.d("Burgerator getVal Exception", e.toString());
            //Log.getInstance().addLog("error " + l + ", " + e + "\n");
        }
        return result;
    }

    public String getEmail(){

        return getVal("useremail");
    }

    public String getUserName(){
        return getVal("username");
    }

    public String getUserPassword(){
        return getVal("userpassword");
    }
    public String getQuote(){
        return getVal("quote");
    }
    public String getUserlocation(){
        return getVal("userlocation");
    }
    public String getZip(){
        return getVal("userzip");
    }
    public String getPhoto(){
        return getVal("userphoto");
    }
    public String getPound(){
        return getVal("userpound");
    }
    public String getTitle(){
        return getVal("burgertitle");
    }
    public int getSize(){
        return json.length();
    }
    public String getResult(){
        return getVal("result");
    }

    public String toString(){
        return json.toString();
    }



}
