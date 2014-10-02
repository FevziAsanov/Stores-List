package com.example.fevzi.storeslist;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import static com.example.fevzi.storeslist.R.layout.*;

/**
 * Created by fevzi on 29.09.14.
 */
public class FragmentStores extends Fragment implements Helper{

    private ListView listView;
    private List<Product> list = new ArrayList<Product>();
    private SQLHelper sqlHelper;
    final String LOG_TAG = "myLogsInFragment";

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

                intent.putExtra(Constants.NAME, list.get(i).getDescription());
                startActivity(intent);
            }
        });


        sqlHelper= new SQLHelper(getActivity());
        readingFromDB();

        new Picking(this,getActivity()).execute();

        readingFromDB();


        return v;
    }

    @Override
    public void callback(final List<Product> productList) {
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

    public void readingFromDB()
    {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        ArrayList<Product> products = new ArrayList<Product>();
        Cursor c = db.query("Product",null, null, null, null, null, null);

        if (c.moveToLast()) {

            int  titleColIndex = c.getColumnIndex("title");
            int  descColIndex = c.getColumnIndex("description");
            do {
                Product product = new Product();

//               Log.d(LOG_TAG,
//                                ", title = " + c.getString(titleColIndex) +
//                                ", description = " + c.getString(descColIndex));

                product.setTitle(c.getString(titleColIndex));
                product.setDescription(c.getString(descColIndex));

                products.add(product);

            } while (c.moveToPrevious());
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();


        callback(products);
        db.close();



    }

}
