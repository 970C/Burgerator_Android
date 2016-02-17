package burgerator.util;

import android.content.*;
import android.content.SharedPreferences;

import java.util.*;

/**
 * Created by Jonathan on 2/13/2016.
 * data object for storing / retrieving user data
 */
public class UserStorage {
    private SharedPreferences sharedPrefs;
    Map<String, String> entries;
    /**
     * default constructor - creates an empty hashmap
     */
    public UserStorage() {
        this.entries = new HashMap<>();
    }

    /**
     * sets a new file for sharedPrefs if it DNE, otherwise it modifies an existing file.
     * file secuity is set to only be accessed by app.
     * @param _fName name of the SharedPrefs file
     * @param m map of key value pairs to store in file
     */
    public void setSharedPrefs(Context c, String _fName, Map<String, String> m){
        sharedPrefs = c.getSharedPreferences( _fName, Context.MODE_PRIVATE );
        Set keyList = m.keySet();
        Iterator itr = keyList.iterator();
        SharedPreferences.Editor editor = sharedPrefs.edit();

        while(itr.hasNext()){
            String key =  itr.next().toString();
            editor.putString(key, m.get(key));
            editor.commit();
        }

    }

    /**
     *
     * @return a map of k/v pairs from the desired file
     */
    public Map getSharedPrefs(Context c, String _fName){
        sharedPrefs = c.getSharedPreferences( _fName, Context.MODE_PRIVATE );
        return c.getSharedPreferences( _fName, Context.MODE_PRIVATE ).getAll();
    }

}
