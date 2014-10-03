package activities;




import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import helper_classes.Constants;
import fragments.FragmentStores;
import fragments.Maps;
import com.example.fevzi.storeslist.R;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    private FragmentStores storesFragment = new FragmentStores();
    private Maps mapFragment = new Maps();
    private   ActionBar.Tab tab1, tab2;
    private int state=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ActionBar actionBar =  getSupportActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        tab1 = actionBar.newTab().setText("Stores");
        tab2 = actionBar.newTab().setText("Map");

        tab1.setTabListener(this);
        tab2.setTabListener(this);

        actionBar.addTab(tab1);
        actionBar.addTab(tab2);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(this,CreateShop.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

        if (tab.getText().equals("Stores")){

            state=0;
            fragmentTransaction.replace(R.id.fragment_container, storesFragment);
        } else if (tab.getText().equals("Map") ){
            fragmentTransaction.replace(R.id.fragment_container, mapFragment);
            state=1;
        }



    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
//
//
//        fragmentTransaction.remove(fragment);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(Constants.BUNDLE_KEY_CUR_FRAGMENT,state);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        state=savedInstanceState.getInt(Constants.BUNDLE_KEY_CUR_FRAGMENT);
        getSupportActionBar().selectTab(getSupportActionBar().getTabAt(state));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {


        super.onDestroy();
    }
}
