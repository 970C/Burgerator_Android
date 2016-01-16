package com.example.luis.burgerator;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Interacts with the database API at http://burger-dev.elasticbeanstalk.com
 *
 */
public class BurgerDB {

    private String mEndpoint = "http://burger-dev.elasticbeanstalk.com";
    private String mRequestType = "POST";
    private RequestQueue mRequestQueue;

    public BurgerDB(Context context){
        /*
        mRequestQueue = Volley.newRequestQueue(context);


        StringRequest request = new StringRequest(
            Request.Method.POST,
            mEndpoint,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("BurgerDBLog", response.toString());
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("BurgerDBLog", error.getMessage());
                }
            })

            {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("ID", "45");
                parameters.put("USER", "Yuri");
                parameters.put("messages", text);
                parameters.put("extra", "2am");

                return parameters;
            }

        };
        requestQueue.add(request);
        */
    }

    public Object getRecentBurgers(){


        return null;
    }

}
