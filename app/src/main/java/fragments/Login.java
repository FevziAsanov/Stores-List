package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fevzi.storeslist.R;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

import web_requests.VolleyWebClient;
import web_requests.WebClient;
import activities.CreateShop;
import helper_classes.Constants;
import helper_classes.WebClientListener;
import model.User;

/**
 * Created by fevzi on 06.10.14.
 */
public class Login extends DialogFragment implements  View.OnClickListener {
    private Button button;
    private EditText name, password, password_confirm,email;
    private  User user = new User();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.login_fragment,container,false);

        button = (Button) v.findViewById(R.id.sing_in);
        name = (EditText)v.findViewById(R.id.name);
        password = (EditText) v.findViewById(R.id.password);
        password_confirm = (EditText) v.findViewById(R.id.password_confirmation);
        email = (EditText) v.findViewById(R.id.email);

        button.setOnClickListener(this);

        return v;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.sing_in:
                try {
                    check();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    public void check() throws UnsupportedEncodingException, JSONException {
        user.setEmail(email.getText().toString());
        user.setName(name.getText().toString());
        user.setPassword(password.getText().toString());
        user.setPasswordConfirm(password_confirm.getText().toString());
        if(user.getPassword().equals(user.getPasswordConfirm()))
        {
            if(user.getPassword()!=null&&user.getName()!=null&&user.getEmail()!=null&&user.getPasswordConfirm()!=null)
            {
//                WebClient.callCreateNewUser(user , new WebClientListener<String>() {
//                    @Override
//                    public void onResponse(String token) {
//                        if(token==null) {
//                            Log.d("res","NULL");
//
//                        }
//                        else{
//                            String s=token;
//                            Intent intent = new Intent(getActivity(), CreateShop.class);
//                            intent.putExtra(Constants.BUNDLE_KEY_CUR_FRAGMENT,s);
//                            startActivity(intent);}
//
//                    }
//                });

                VolleyWebClient.callCreateNewUser(user, new WebClientListener<String>() {
                    @Override
                    public void onResponse(String token) {
                        if (token == null) {
                            Log.d("res", "NULL");

                        } else {
                            String s = token;
                            Intent intent = new Intent(getActivity(), CreateShop.class);
                            intent.putExtra(Constants.BUNDLE_KEY_CUR_FRAGMENT, s);
                            startActivity(intent);
                        }

                    }
                }, getActivity());


            }
            else Toast.makeText(getActivity(),"empty field",Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(getActivity(),"mismatch passwords",Toast.LENGTH_LONG).show();
        }



    }


}