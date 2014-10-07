package web_requests;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

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

    public static void callGetProducts(int page, WebClientListener l) {
        Parameter p = new GetProductsListParam(page);

        makeRequestAsync(p,l);

    }

    public static  void callCreateNewProduct(Product product, WebClientListener l)
    {
        Parameter p = new CreateNewProduct(product);
        makeRequestAsync(p,l);

    }


    public static void callCreateNewUser(User user, WebClientListener l)
    {
        Parameter p = new CreateNewUser(user);
        makeRequestAsync(p,l);
    }

    private static void makeRequestAsync(final Parameter p, final WebClientListener l) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    makeRequestSync(p, l);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private static   void makeRequestSync(Parameter p, WebClientListener l ) throws JSONException, UnsupportedEncodingException {


        l.onResponse(p.parse(executeRequest(p)));


    }
    public static String executeRequest(Parameter p){

        return null;
    }


    public static Object getRequest(Parameter p) throws JSONException, UnsupportedEncodingException {
        switch (p.getMethod()) {
            case Get:
                return new HttpGet(p.getRequestURL());
            case Post:

                HttpPost httpPost = new HttpPost(p.getRequestURL());
                StringEntity stringEntity = new StringEntity(p.createJSON().toString());
                httpPost.setHeader("Content-Type", "application/json");
                httpPost.setEntity(stringEntity);

                return httpPost;
            case Delete:
                return new HttpDelete();
        }

        return null;

    }


}
