package model;

import java.util.List;

/**
 * Created by fevzi on 03.10.14.
 */
public class ResultProduct
{
    private List<Product> products;
    private int cur_page;
    private int total_page;

    public List<Product>  getProducts() {
        return products;
    }

    public int getCur_page() {
        return cur_page;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setCur_page(int cur_page) {
        this.cur_page = cur_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public void setProduct(List<Product> products) {

        this.products = products;
    }
}
