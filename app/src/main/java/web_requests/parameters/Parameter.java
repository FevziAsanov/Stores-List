package web_requests.parameters;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fevzi on 03.10.14.
 */
public abstract class  Parameter<T> {

   public abstract T parse(String s) throws JSONException;
   public abstract String getRequestURL();
   public abstract Method getMethod();
   public  JSONObject createJSON() throws JSONException {
       return null;
   }

    public enum Method {
        Get,Post,Delete
    }
}
