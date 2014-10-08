package web_requests.parameters;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import helper_classes.Constants;
import model.User;

/**
 * Created by fevzi on 06.10.14.
 */
public class CreateNewUser extends Parameter<String>{

   public    User user;
    public  CreateNewUser(User user)
    {
        this.user= user;
    }
    @Override
    public String parse(String s) throws JSONException {

        JSONObject  jsonObject =  new JSONObject(s);
        String token=null;

            JSONObject jsonObject1 = jsonObject.getJSONObject("user");
            if (jsonObject1.getBoolean("success")) {
                token = jsonObject1.getString("token");
                return token;
            } else

                return token;
    }

  public JSONObject createJSON() throws JSONException {
      JSONObject jsonObject = new JSONObject();
       Log.d("User " ,user.getEmail() + " " + user.getPassword() + " " + user.getPasswordConfirm() +" " + user.getName());
      jsonObject.put("email",user.getEmail());
      jsonObject.put("password",user.getPassword());
      jsonObject.put("password_confirmation",user.getPasswordConfirm());
      jsonObject.put("username",user.getName());

      return jsonObject;
  }

    @Override
    public String getRequestURL() {
        return Constants.URL+"create_user.json" ;
    }

    @Override
    public Method getMethod() {
        return Method.Post;
    }
}
