package picks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
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

import DB.DBAdapter;
import helper_classes.Constants;
import helper_classes.ListProduct;
import model.Author;
import model.Product;

/**
 * Created by fevzi on 30.09.14.
 */
//public class Picking extends AsyncTask<Void, Void, ArrayList<Product>> {
//
//
//    private int total_page = 0;
//    private int per_page = 0;
//
//    private ArrayList<Product> products =new ArrayList<Product>();
//    private ListProduct helper;
//    private DBAdapter sqlHelper;
//
//
//
//    public Picking(ListProduct helper, Context context) {
//
//        this.helper=helper;
//        sqlHelper= new DBAdapter(context);
//    }
//
//
//    @Override
//    protected void onPostExecute(ArrayList<Product> products) {
//        super.onPostExecute(products);
//        Log.d("L", products.get(1).getDescription());
//       // helper.callback(products);
//        helper.setList(sqlHelper.readingFromDB());
//
//
//
//    }
//
//
//    @Override
//    protected ArrayList<Product>  doInBackground(Void... voids) {
//
//        String json2 = getJSON(1);
//        if(json2!=null) {
//
//            sqlHelper.deleteAll();
//
//            try {
//
//                jObj = new JSONObject(json2);
//                JSONObject jo = jObj.getJSONObject("pagination");
//                total_page = jo.getInt("total_page");
//                per_page = jo.getInt("per_page");
//
//
//                for (int i = 1; i < total_page + 1; i++) {
//
//                    try {
//                        json2 = getJSON(i);
//                        jObj = new JSONObject(json2);
//                        JSONArray ja = jObj.getJSONArray("products");
//                        for (int j = 0; j < per_page; j++) {
//
//
//                            Product product = new Product();
//                            Author author = new Author();
//
//                            jObj = ja.getJSONObject(j);
//                            product.setId(jObj.getInt("id"));
//                            product.setTitle(jObj.getString("title"));
//                            product.setDescription(jObj.getString("description"));
//                            product.setLat(jObj.getInt("lat"));
//                            product.setLng(jObj.getInt("long"));
//                            product.setAvatar(jObj.getString("avatar"));
//
//
//                            JSONObject jObjAuthor = jObj.getJSONObject("author");
//                            author.setEmail(jObjAuthor.getString("email"));
//                            author.setName(jObjAuthor.getString("name"));
//                            author.setToken(jObjAuthor.getString("token"));
//
//                            product.setAuthor(author);
//
//                            products.add(product);
//
//                        }
//                    } catch (JSONException e1) {
//                        e1.printStackTrace();
//                    }
//
//                }
//
//
//            } catch (JSONException e1) {
//                e1.printStackTrace();
//            }
//
//            sqlHelper.insert(products);
//
//        }
//        return products;
//    }
//
//    public String getJSON(int i) {
//
//return  null;
//    }
//
//}
