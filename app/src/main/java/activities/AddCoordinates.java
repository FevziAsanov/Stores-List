package activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import web_requests.WebClient;
import helper_classes.Constants;
import helper_classes.WebClientListener;
import model.Author;
import model.Product;

import com.example.fevzi.storeslist.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by fevzi on 02.10.14.
 */
public class AddCoordinates extends FragmentActivity{

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private Marker marker;
    private  String [] shop;
    private  double latitude ,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_coordinates);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        Intent intent = getIntent();

        shop = intent.getStringArrayExtra(Constants.BUNDLE_KEY_CUR_FRAGMENT);

        try {
            Thread.sleep(600);
            map = mapFragment.getMap();
            map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                    latitude=  marker.getPosition().latitude;
                    longitude=marker.getPosition().longitude;
                }

                @Override
                public void onMarkerDragEnd(Marker marker) {


                    latitude=  marker.getPosition().latitude;
                    longitude=marker.getPosition().longitude;
                }
            });

            inits();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_product, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void inits()
    {

        marker = map.addMarker(new MarkerOptions().position(new LatLng(0,0)).title(shop[0]));
        marker.setDraggable(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {




        Product p  =  new Product();

        p.setTitle(shop[0]);;
        p.setLng((int) longitude);
        p.setLat((int) latitude);
        p.setDescription(shop[1]);
        p.setAvatar(null);
        Author a =  new Author();
        a.setToken(shop[2]);
        p.setAuthor(a);


        WebClient.callCreateNewProduct(p,new WebClientListener<String>() {
            @Override
            public void onResponse(String p) {
           // Toast.makeText(getApplicationContext(),"Product created",Toast.LENGTH_LONG).show();

                Log.d("JSON", " "  + p);
            }
        });

        return super.onOptionsItemSelected(item);
    }
}
