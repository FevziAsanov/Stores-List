package fragments;


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

import DB.DBAdapter;
import WebRequests.Controller;
import WebRequests.Parameters.GetProductsListParam;
import WebRequests.WebClient;
import helper_classes.Constants;
import helper_classes.ListProduct;

import model.Product;
import com.example.fevzi.storeslist.R;

import activities.DescriptionActivity;
import model.ResultProduct;

/**
 * Created by fevzi on 29.09.14.
 */
public class FragmentStores extends Fragment implements ListProduct {

    private ListView listView;
    private List<Product> list = new ArrayList<Product>();
    private DBAdapter sqlHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView)v.findViewById(R.id.list);
        Log.d("DDD","fragment");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), DescriptionActivity.class);

                String [] s  = {list.get(i).getDescription(), list.get(i).getAuthor().getName(), list.get(i).getAuthor().getEmail()};

                intent.putExtra(Constants.NAME,s);
                startActivity(intent);
            }
        });


        sqlHelper= new DBAdapter(getActivity());

        setList(sqlHelper.readingFromDB());
    //   webClient = new WebClient(); //webClient.newInstance();

       WebClient.callGetProducts(1,new Controller(getActivity()));
      //  new Picking(this,getActivity()).execute();

        setList(sqlHelper.readingFromDB());


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
