package Productmanager.exceptionHandler;

public class ProductNotFoundException extends Exception{
    public ProductNotFoundException(){
        super("Product not found!");
    }
}

