package fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import  android.support.v4.app.Fragment;

import db.DBAdapter;
import web_requests.ControllerForProduct;
import web_requests.WebClient;
import helper_classes.Constants;
import helper_classes.ListProduct;

import model.Product;
import com.example.fevzi.storeslist.R;

import activities.DescriptionActivity;

/**
 * Created by fevzi on 29.09.14.
 */
public class FragmentStores extends Fragment implements ListProduct {

    private ListView listView;
    private List<Product> list = new ArrayList<Product>();
    private DBAdapter dbAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView)v.findViewById(R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), DescriptionActivity.class);

                int id  = list.get(i).getId();

                intent.putExtra(Constants.NAME,id);
                startActivity(intent);
            }
        });

        dbAdapter = new DBAdapter(getActivity());

        setList(dbAdapter.getAllProducts());

        WebClient.callGetProducts(1,new ControllerForProduct(getActivity(),this));

        //setList(dbAdapter.getAllProducts());

        return v;
    }

    @Override
    public void setList(final List<Product> productList) {
        if(getActivity()!=null)
        getActivity().runOnUiThread(new Runnable() {

          @Override
           public void run() {
                String []s =new String[productList.size()];

                Log.d("Product"," "+productList.size());
                for (int i = 0; i <productList.size() ; i++) {

                    s[i]=productList.get(i).getTitle();

                }
                ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(
                        s.length);
                Map<String, Object> m;
                for (int i = 0; i < s.length; i++) {
                    m = new HashMap<String, Object>();
                    m.put(Constants.ATTRIBUTE_NAME_TEXT, s[i]);
                    data.add(m);
                }
                String[] from = { Constants.ATTRIBUTE_NAME_TEXT };
                int[] to = { R.id.text};

                SimpleAdapter sAdapter = new SimpleAdapter(getActivity(), data, R.layout.item,from, to);

                list=productList;

                listView.setAdapter(sAdapter);
                sAdapter.notifyDataSetChanged();

            }
        });

    }

}
