package activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import helper_classes.Constants;
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
            inits();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void inits()
    {

       Marker m = map.addMarker(new MarkerOptions().position(new LatLng(0,0)).title(shop[0]));
        m.setDraggable(true);

    }

}
