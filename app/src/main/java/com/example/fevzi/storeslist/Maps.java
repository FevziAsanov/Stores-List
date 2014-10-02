package com.example.fevzi.storeslist;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fevzi on 29.09.14.
 */
public class Maps extends Fragment implements Helper {

    private SupportMapFragment mapFragment;

    private GoogleMap map;
    View   view;
    private Marker [] marker;
    private ArrayList<Product> products ;
    private SQLHelper sqlHelper;
    private SupportMapFragment fragment;
    private ArrayList<HashMap<Integer,Integer>> hashMaps = new ArrayList<HashMap<Integer, Integer>>();
   private static boolean h = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


         view = inflater.inflate(R.layout.map_fragment, container, false);

         FragmentManager fm = getFragmentManager();

        mapFragment= (SupportMapFragment) fm.findFragmentById(R.id.map);

        sqlHelper= new SQLHelper(getActivity());

    //    new Picking(this,getActivity()).execute();
       readingFromDB();


        return view;
    }

    private void init() {

        onClickTest();
    }

    public void onClickTest() {
            marker = new Marker[products.size()];
        for (int i = 0; i <products.size() ; i++) {

            marker[i]= map.addMarker(new MarkerOptions().position(new LatLng(products.get(i).getLat(), products.get(i).getLng())).title(products.get(i).getTitle()));

            HashMap<Integer,Integer> hashMap = new HashMap<Integer, Integer>();
            hashMap.put(Integer.getInteger(marker[i].getId()), products.get(i).getId());

            hashMaps.add(hashMap);
        }


    }

    @Override
    public void callback(List<Product> productList) {


        products=(ArrayList<Product>)productList;
        new Thread(new Runnable() {
            @Override
            public void run() {


                try {

                    Thread.sleep(500);

                    map = mapFragment.getMap();
                    if(getActivity()!=null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            init();

                            map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                @Override
                                public void onInfoWindowClick(Marker marker) {
                                    for (int i = 0; i <hashMaps.size() ; i++) {


                                        if(hashMaps.get(i).get(Integer.getInteger(marker.getId()))!=null)
                                        {
                                            Intent intent = new Intent(getActivity(), DescriptionActivity.class);

                                            intent.putExtra(Constants.NAME, products.get(i).getDescription());
                                            startActivity(intent);
                                            break;


                                        }


                                    }

                                }
                            });
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }



    @Override
    public void onDestroy() {

        Fragment fragment = (getFragmentManager().findFragmentById(R.id.map));
        if (fragment.isResumed()) {
            getFragmentManager().beginTransaction().remove(fragment).commit();
        }


        super.onDestroy();


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

                product.setTitle(c.getString(c.getColumnIndex("title")));
                product.setDescription(c.getString(c.getColumnIndex("description")));
                product.setId(c.getInt(c.getColumnIndex("id")));
                product.setLat(c.getInt(c.getColumnIndex("lat")));
                product.setLng(c.getInt(c.getColumnIndex("lng")));

                products.add(product);

            } while (c.moveToPrevious());
        }

        c.close();


        callback(products);
        db.close();



    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}
