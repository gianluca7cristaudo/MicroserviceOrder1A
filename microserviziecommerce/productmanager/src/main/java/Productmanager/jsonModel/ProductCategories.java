package Productmanager.jsonModel;

import com.sun.istack.NotNull;

import java.util.ArrayList;

public class ProductCategories {

    @NotNull
    private Integer productId;

    @NotNull
    private ArrayList<String> categories;

    public Integer getProductId() {
        return productId;
    }

    public ProductCategories setProductId(Integer productId) {
        this.productId = productId;
        return this;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public ProductCategories setCategories(ArrayList<String> categories) {
        this.categories = categories;
        return this;
    }
}
