package activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import helper_classes.Constants;
import com.example.fevzi.storeslist.R;

/**
 * Created by fevzi on 02.10.14.
 */
public class CreateShop extends Activity implements View.OnClickListener {

    private EditText title, description;
    private Button enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_shop_activity);

        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);

        enter = (Button) findViewById(R.id.btn);


    }



    @Override
    public void onClick(View v) {


        if(title.getText().length()>0 && description.getText().length()>0) {

            String s[] = { title.getText().toString(),description.getText().toString()};
            Intent intent = new Intent(this, AddCoordinates.class);
            intent.putExtra(Constants.BUNDLE_KEY_CUR_FRAGMENT, s);
            startActivity(intent);
        }
      else
            Toast.makeText(this,"Not all fields are entered",Toast.LENGTH_LONG).show();



    }
}
