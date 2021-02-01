package Productmanager.exceptionHandler;

public class CategoryNotFoundException extends Exception{
    public CategoryNotFoundException(){
        super("Category not found!");
    }
}
