package web_requests;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import helper_classes.WebClientListener;
import model.Product;
import model.User;
import web_requests.parameters.CreateNewProduct;
import web_requests.parameters.CreateNewUser;
import web_requests.parameters.GetProductsListParam;
import web_requests.parameters.Parameter;

/**
 * Created by fevzi on 07.10.14.
 */

public class VolleyWebClient {


    public static void callGetProducts(int page, WebClientListener l, Context c) throws JSONException, UnsupportedEncodingException {
        Parameter p = new GetProductsListParam(page);
        executeRequest(p, c, l);

    }

    public static void callCreateNewProduct(Product product, WebClientListener l, Context c) throws JSONException, UnsupportedEncodingException {
        Parameter p = new CreateNewProduct(product);
        executeRequest(p, c, l);
    }


    public static void callCreateNewUser(User user, WebClientListener l, Context c) throws JSONException, UnsupportedEncodingException {
        Parameter p = new CreateNewUser(user);
        executeRequest(p, c, l);
    }


    public static void executeRequest(final Parameter p, Context c, final WebClientListener l) throws UnsupportedEncodingException, JSONException {

        RequestQueue requestQueue = Volley.newRequestQueue(c);

        Response.Listener<JSONObject> a = new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    l.onResponse(p.parse(jsonObject.toString()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        };


         JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(getRequest(p), p.getRequestURL(), getJSONObject(p), a, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }


        };
        requestQueue.add(jsonObjectRequest);

    }


    public static int getRequest(Parameter p) throws JSONException, UnsupportedEncodingException {
        switch (p.getMethod()) {
            case Get:
                return Request.Method.GET;
            case Post:
                return Request.Method.POST;
            case Delete:
                return Request.Method.DELETE;
        }

        return 0;

    }

    private static JSONObject getJSONObject(Parameter p) throws JSONException, UnsupportedEncodingException {
        JSONObject jsonObject = null;
        if(getRequest(p)==Request.Method.POST)
        {
            jsonObject= new JSONObject(p.createJSON().toString());
        }
        return  jsonObject;
    }


}
