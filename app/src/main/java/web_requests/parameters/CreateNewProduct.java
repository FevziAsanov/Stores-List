package web_requests.parameters;

import org.json.JSONException;
import org.json.JSONObject;

import model.Product;

/**
 * Created by fevzi on 06.10.14.
 */
public class CreateNewProduct extends Parameter<String> {


    private Product product;
    public CreateNewProduct(Product product)
    {
        this.product = product;
    }
    @Override
    public String parse(String s) throws JSONException {



        return s;
    }

    public JSONObject createJSON() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        JSONObject jObj = new JSONObject();
        jsonObject.put("token",product.getAuthor().getToken());
        jObj.put("title",product.getTitle());
        jObj.put("description",product.getDescription());
        jObj.put("lat",product.getLat());
        jObj.put("long",product.getLng());
        jObj.put("avatar",product.getAvatar());

        jsonObject.put("product",jObj);


        return jsonObject;
    }

    @Override
    public String getRequestURL() {
        return "http://protected-wave-2984.herokuapp.com/api/create_product.json";
    }

    @Override
    public Method getMethod() {
        return Method.Post;
    }
}
