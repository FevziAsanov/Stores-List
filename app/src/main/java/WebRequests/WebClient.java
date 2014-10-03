package WebRequests;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import WebRequests.Parameters.GetProductsListParam;
import WebRequests.Parameters.Parameter;
import helper_classes.Listener;
import model.ResultProduct;

/**
 * Created by fevzi on 03.10.14.
 */
public class WebClient {


   public WebClient() {

    }




    public static void callGetProducts(int page, Listener l) {
        Parameter p = new GetProductsListParam(page);

        makeRequestAsync(p,l);

    }

    private static void makeRequestAsync(final Parameter p, final Listener l) {
         new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    makeRequestSync(p, l);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private static   void makeRequestSync(Parameter p, Listener l ) throws JSONException {


                  l.onResponse(p.parse(executeRequest(p)));


    }


    private static boolean save() {

        return false;
    }

    public static String executeRequest(Parameter p) {
        HttpRequestBase request = (HttpRequestBase) getRequest(p);
        DefaultHttpClient httpClient = new DefaultHttpClient();


        InputStream stream;
        HttpResponse httpResponse = null;
        String json = null;
        try {
            httpResponse = httpClient.execute(request);
            HttpEntity httpEntity = httpResponse.getEntity();
            stream = httpEntity.getContent();
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(
                        stream, "iso-8859-1"), 8);
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

    public static Object getRequest(Parameter p) {
        switch (p.getMethod()) {
            case Get:
                return new HttpGet(p.getRequestURL());
            case Post:
                return new HttpPost();
        }

        return null;

    }

}


