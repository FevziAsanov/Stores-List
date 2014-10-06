package WebRequests;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import DB.DBAdapter;
import helper_classes.WebClientListener;
import model.Product;
import model.ResultProduct;

/**
 * Created by fevzi on 03.10.14.
 */
public class ControllerForProduct implements WebClientListener<ResultProduct> {
    private  WebClient webClient = new WebClient();
    ArrayList<Product> resultProducts = new ArrayList<Product>();
    private int page=1 ;
    private DBAdapter dbAdapter;

    public ControllerForProduct(Context context)
    {
        dbAdapter = new DBAdapter(context);
    }
    @Override
    public void onResponse(ResultProduct resultProduct) {

        resultProducts.addAll(resultProduct.getProducts());

        if(resultProduct.getTotal_page()<=page) {

            dbAdapter.deleteAll();
            dbAdapter.insert(resultProducts);

            return;
        }
        else {

            getProducts();
        }
    }

    void getProducts()
    {
        page++;
        webClient.callGetProducts(page,this);

    }


}
