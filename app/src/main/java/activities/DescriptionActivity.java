package activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import db.DBAdapter;
import helper_classes.Constants;
import com.example.fevzi.storeslist.R;

/**
 * Created by fevzi on 30.09.14.
 */
public class DescriptionActivity extends Activity {
    private TextView textView;
    private String description,name,email;
    private DBAdapter dbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);
        Intent intent = getIntent();
        dbAdapter = new DBAdapter(this);

        int id = intent.getIntExtra(Constants.NAME,1);
        Log.d("id ",id+" ");
        String [] s ;
        s =dbAdapter.getDescription(id);
        Log.d("Desc ",s[0]);
        ((TextView)findViewById(R.id.dscrpt)).setText(s[0]);
        ((TextView)findViewById(R.id.author)).setText("Author:  \n"+ s[1]  + "\n" + s[2]);

    }
}
