package fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import db.DBAdapter;
import helper_classes.Constants;
import helper_classes.ListProduct;
import model.Product;
import com.example.fevzi.storeslist.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import activities.DescriptionActivity;

/**
 * Created by fevzi on 29.09.14.
 */
public class Maps extends Fragment implements ListProduct {

    private SupportMapFragment mapFragment;

    private GoogleMap map;
    private Marker [] markers;
    private ArrayList<Product> products ;
    private DBAdapter sqlHelper;
    private  int productID[];
    private static boolean h = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        FragmentManager fm = getFragmentManager();

        mapFragment= (SupportMapFragment) fm.findFragmentById(R.id.map);

        sqlHelper= new DBAdapter(getActivity());

        setList(sqlHelper.getAllProducts());


        return view;
    }

    private void init() {

        setMarkers();
    }

    public void setMarkers() {
        markers = new Marker[products.size()];
        productID = new int [products.size()+1];


        for (int i = 0; i <products.size() ; i++) {

            markers[i]= map.addMarker(new MarkerOptions().position(new LatLng(products.get(i).getLat(), products.get(i).getLng())).title(products.get(i).getTitle()));

            productID[i]  =products.get(i).getId();


        }


    }

    @Override
    public void setList(List<Product> productList) {


        products=(ArrayList<Product>)productList;
        new Thread(new Runnable() {
            @Override
            public void run() {


                try {

                    Thread.sleep(600);


                    map = mapFragment.getMap();
                    if(getActivity()!=null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            init();

                            map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                @Override
                                public void onInfoWindowClick(Marker marker) {

                                    transToDescActivity(marker);

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

public void  transToDescActivity(Marker marker)
{

    Intent intent = new Intent(getActivity(), DescriptionActivity.class);
    for(int i=0;i<products.size();i++)
    {
        if(markers[i].getId().equals( marker.getId())) {
            int id = productID[i];

            intent.putExtra(Constants.NAME, id);
            startActivity(intent);
            break;
        }

    }

}
    @Override
    public void onDestroy() {

        Fragment fragment = (getFragmentManager().findFragmentById(R.id.map));
        if (fragment.isResumed()) {
            getFragmentManager().beginTransaction().remove(fragment).commit();
        }
        super.onDestroy();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}
