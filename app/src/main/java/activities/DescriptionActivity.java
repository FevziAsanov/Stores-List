package activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import helper_classes.Constants;
import com.example.fevzi.storeslist.R;

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

        String []fName = intent.getStringArrayExtra(Constants.NAME);
        ((TextView)findViewById(R.id.dscrpt)).setText(fName[0]);
        ((TextView)findViewById(R.id.author)).setText("Author:  \n"+ fName[1]  + "\n" + fName[2]);

    }
}
