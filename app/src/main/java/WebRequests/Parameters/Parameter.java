package WebRequests.Parameters;

import org.json.JSONException;

import DB.DBAdapter;
import model.ResultProduct;

/**
 * Created by fevzi on 03.10.14.
 */
public abstract class Parameter<T> {

    public abstract ResultProduct parse(String s) throws JSONException;
   public abstract String getRequestURL();
   public abstract Method getMethod();

    public enum Method {
        Get,Post
    }
}
