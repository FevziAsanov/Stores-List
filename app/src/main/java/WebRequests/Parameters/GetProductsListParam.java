package WebRequests.Parameters;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import DB.DBAdapter;
import WebRequests.WebClient;
import helper_classes.Constants;
import model.Author;
import model.Product;
import model.ResultProduct;

/**
 * Created by fevzi on 03.10.14.
 */
public class GetProductsListParam extends Parameter<List<Product>> {
    int page;
     public GetProductsListParam( int page)
     {
         this.page=page;
     }
    @Override
    public ResultProduct parse(String s) throws JSONException {




        ArrayList<Product> products = new ArrayList<Product>();


        ResultProduct res_products =  new ResultProduct();

                JSONObject jObj;
                jObj = new JSONObject(s);
                JSONArray ja = jObj.getJSONArray("products");
                JSONObject jo2 = jObj.getJSONObject("pagination");
                int per_page;

                     res_products.setTotal_page(jo2.getInt("total_page"));
                per_page = jo2.getInt("per_page");

                for (int j = 0; j < per_page; j++) {


                    ResultProduct res_product = new ResultProduct();
                    Product product = new Product();
                    Author author = new Author();

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


                }
        res_products.setProduct(products);




        return res_products;
    }

    @Override
    public Method getMethod() {

        return Method.Get;
    }

    @Override
    public String getRequestURL() {
        return Constants.URL +"product_list.json?page=" + page;
    }
}
