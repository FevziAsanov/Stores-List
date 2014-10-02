package com.example.fevzi.storeslist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by fevzi on 30.09.14.
 */
public class Picking extends AsyncTask<Void, Void, ArrayList<Product>> {

    private InputStream stream = null;
    private int total_page = 0,ref_auth;
    private int per_page = 0;
    private DefaultHttpClient httpClient;
    private JSONObject jObj = null;
    private ArrayList<Product> products =new ArrayList<Product>();
    private Helper helper;
    private SQLHelper sqlHelper;
    private ContentValues cvPr = new ContentValues();
    private ContentValues cvAth = new ContentValues();
    private final String LOG_TAG = "myLogsInPicker";

    public Picking(Helper helper, Context context) {
        httpClient = new DefaultHttpClient();
        this.helper=helper;
        sqlHelper= new SQLHelper(context);
    }


    @Override
    protected void onPostExecute(ArrayList<Product> products) {
        super.onPostExecute(products);
        Log.d("L", products.toString());
       // helper.callback(products);
        readingFromDB();


    }


    @Override
    protected ArrayList<Product>  doInBackground(Void... voids) {

        String json2 = getJSON(1);
        if(json2!=null) {

            SQLiteDatabase db = sqlHelper.getWritableDatabase();

            int clearCount = db.delete("Product", null, null);
            Log.d(LOG_TAG, "deleted rows count = " + clearCount);
            clearCount = db.delete("Author", null, null);
            Log.d(LOG_TAG, "deleted rows count = " + clearCount);

            ref_auth = 0;



            try {

                jObj = new JSONObject(json2);
                JSONObject jo = jObj.getJSONObject("pagination");
                total_page = jo.getInt("total_page");
                per_page = jo.getInt("per_page");


                for (int i = 1; i < total_page + 1; i++) {

                    try {
                        json2 = getJSON(i);
                        jObj = new JSONObject(json2);
                        JSONArray ja = jObj.getJSONArray("products");
                        for (int j = 0; j < per_page; j++) {


                            Product product = new Product();
                            Author author = new Author();

                            ref_auth++;

                            jObj = ja.getJSONObject(j);
                            product.setId(jObj.getInt("id"));
                            product.setTitle(jObj.getString("title"));
                            product.setDescription(jObj.getString("description"));
                            product.setLat(jObj.getInt("lat"));
                            product.setLng(jObj.getInt("long"));
                            product.setAvatar(jObj.getString("avatar"));


                            JSONObject jObjAuthor = jObj.getJSONObject("author");
                            author.setEmail(jObjAuthor.getString("email"));
                            author.setName(jObjAuthor.getString("name"));
                            author.setToken(jObjAuthor.getString("token"));

                            product.setAuthor(author);

                            products.add(product);


                            cvAth.put("email", author.getEmail());
                            cvAth.put("name", author.getName());
                            cvAth.put("token", author.getToken());


                            cvPr.put("id", product.getId());
                            cvPr.put("lng", product.getLng());
                            cvPr.put("lat", product.getLat());
                            cvPr.put("title", product.getTitle());
                            cvPr.put("avatar", product.getAvatar());
                            cvPr.put("description", product.getDescription());
                            cvPr.put("author_ID", ref_auth);

                            long rowID = db.insert("Author", null, cvAth);
                        //    Log.d(LOG_TAG, "row inserted, ID = " + rowID);

                            rowID = db.insert("Product", null, cvPr);
                     //       Log.d(LOG_TAG, "row inserted, ID = " + rowID);


                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }

                }
                sqlHelper.close();

            } catch (JSONException e1) {
                e1.printStackTrace();
            }

        }
        return products;
    }

    public String getJSON(int i) {

        HttpGet httpGet = new HttpGet("http://protected-wave-2984.herokuapp.com/api/product_list.json?page=" + i);
        HttpResponse httpResponse = null;
        String json=null;
        try {
            httpResponse = httpClient.execute(httpGet);
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

    public void readingFromDB()
    {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        ArrayList<Product> products = new ArrayList<Product>();
        Cursor c = db.query("Product",null, null, null, null, null, null);

        if (c.moveToLast()) {


            do {
                Product product = new Product();

 /*               Log.d(LOG_TAG,
                                ", title = " + c.getString(titleColIndex) +
                                ", description = " + c.getString(descColIndex));*/

                product.setTitle(c.getString(c.getColumnIndex("title")));
                product.setDescription(c.getString(c.getColumnIndex("description")));
                product.setId(c.getInt(c.getColumnIndex("id")));
                product.setLat(c.getInt(c.getColumnIndex("lat")));
                product.setLng(c.getInt(c.getColumnIndex("lng")));

                products.add(product);

            } while (c.moveToPrevious());
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();


        helper.callback(products);
        db.close();



    }

}
