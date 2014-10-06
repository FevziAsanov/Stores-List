package helper_classes;

import model.Product;
import model.ResultProduct;

/**
 * Created by fevzi on 03.10.14.
 */
public interface WebClientListener<T> {
    void onResponse(T p);
}
