package web_requests;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import web_requests.parameters.CreateNewProduct;
import web_requests.parameters.CreateNewUser;
import web_requests.parameters.GetProductsListParam;
import web_requests.parameters.Parameter;
import helper_classes.WebClientListener;
import model.Product;
import model.User;

/**
 * Created by fevzi on 03.10.14.
 */
public class WebClient {


   public WebClient() {

    }




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






    public static String executeRequest(Parameter p) throws UnsupportedEncodingException, JSONException {
        HttpRequestBase request = (HttpRequestBase) getRequest(p);

        DefaultHttpClient httpClient = new DefaultHttpClient();

        InputStream stream;
        HttpResponse httpResponse = null;
        String json = null;
        try {

            httpResponse = httpClient.execute(request);
            Log.d("code ", "" + httpResponse.getStatusLine().getStatusCode());
            HttpEntity httpEntity = httpResponse.getEntity();
            stream = httpEntity.getContent();
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(
                        stream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                stream.close();
                json = sb.toString();

            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
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


