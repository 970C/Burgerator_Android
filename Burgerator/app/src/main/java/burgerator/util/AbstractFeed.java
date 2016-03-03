package burgerator.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Jonathan on 3/2/2016.
 */
public abstract class AbstractFeed<E> {

    protected JSONObject json;

    protected List<E> list;

    protected abstract void addObjectsToList(JSONObject _obj);

    /**
     * adds a new JSONObject to be parsed and added to the list of burgers
     *@param _obj is a JSONObject
     */
    public void addJSON (JSONObject _obj){
        try{
            addObjectsToList(_obj);
        }catch (Exception e) {
            Log.d("Feed.addJSON ", e.getMessage());
        }
    }

    /**
     * Deprecated - included for compatibility, sets the JSONObject
     * @param _obj is a JSONObject
     */
    public void setJSON (JSONObject _obj) {
        try{
            addObjectsToList(_obj);
        }catch (Exception e) {
            Log.d("setJSON", e.getMessage());
        }
    }

    /**
     * returns the size of the ArrayList
     * @return size
     */
    public int size(){
        return this.list.size();
    }

    /**
     * returns a <E>element by index
     * @param _index int index for burgers ArrayList
     * @return Burger b
     */
     abstract <E> E get(int _index);

     abstract List<E> getAll();

}
