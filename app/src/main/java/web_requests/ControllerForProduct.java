package web_requests;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.ArrayList;

import db.DBAdapter;
import fragments.FragmentStores;
import helper_classes.ListProduct;
import helper_classes.WebClientListener;
import model.Product;
import model.ResultProduct;

/**
 * Created by fevzi on 03.10.14.
 */
public class ControllerForProduct implements WebClientListener<ResultProduct> {
    private  WebClient webClient = new WebClient();
    private ArrayList<Product> resultProducts = new ArrayList<Product>();
    private int page=1 ;
    private DBAdapter dbAdapter;
    private ProgressDialog dialog;
    private  FragmentStores fragmentStores;
    public ControllerForProduct(Context context, FragmentStores fragmentStores)
    {
        dbAdapter = new DBAdapter(context);
        this.fragmentStores = fragmentStores;
        dialog = new ProgressDialog(context);
        dialog.setTitle("attention");
        dialog.setMessage("updating stores");

        dialog.setButton(Dialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        if(dbAdapter.getAllProducts().size()==0) {
            dialog.show();
        }
    }
    @Override
    public void onResponse(ResultProduct resultProduct) {

        resultProducts.addAll(resultProduct.getProducts());

        if(resultProduct.getTotal_page()<=page) {

            dbAdapter.deleteAll();
            dbAdapter.insertProducts(resultProducts);
            fragmentStores.setList(resultProducts);
            dialog.dismiss();
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
