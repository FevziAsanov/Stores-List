package com.example.fevzi.storeslist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by fevzi on 30.09.14.
 */
public class DescriptionActivity extends Activity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);
        Intent intent = getIntent();

        String fName = intent.getStringExtra(Constants.NAME);
        ((TextView)findViewById(R.id.dscrpt)).setText(fName);


    }
}
