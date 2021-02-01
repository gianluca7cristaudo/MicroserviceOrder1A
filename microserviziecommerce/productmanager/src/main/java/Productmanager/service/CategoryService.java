package Productmanager.service;

import Productmanager.exceptionHandler.CategoryNotFoundException;
import Productmanager.jsonModel.ProductCategories;
import Productmanager.repository.CategoryRepository;
import Productmanager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import product.Category;
import product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation= Propagation.REQUIRED)
public class CategoryService {

    @Autowired
    ProductRepository proRepo;

    @Autowired
    CategoryRepository catRepo;


    private static final String PRODUCT_MANAGER_URL="http://productmanager:3333";

    public Category addCategory(Category category) {
        try {
            return catRepo.save(new Category().setCategory(category.getCategory()));
        }catch(Exception e){
            return null;
        }
    }

    public String addCategoryToProduct(ProductCategories newCategories){
        Optional<Product> productOptional = proRepo.findById(newCategories.getProductId());
            if (productOptional.isPresent()==false) return "the product is not present!!";
        Product product = productOptional.get();
        String categoryCheck="";
        List<Category> listCategories = new ArrayList<>();
            for (String entry : newCategories.getCategories()){
                    if(entry=="")return "Invalid format!!";

                Optional<Category>  category = catRepo.findCategoryByCategory(entry);
                    if (category.isPresent()==true){
                        product.addCategory(category.get());
                    }else{
                        categoryCheck+=entry + "\n";
                    }
            }
        proRepo.save(product);
        return "Added!!\n" +  (categoryCheck=="" ? "" : "Not present categories:\n" + categoryCheck);
    }

    public Category getCategory(String name) throws CategoryNotFoundException {
        Optional<Category> categoryOptional = catRepo.findCategoryByCategory(name);
            if (categoryOptional.isPresent()==false){
                throw new CategoryNotFoundException();
            }
        return categoryOptional.get();
    }

    public Iterable<Category> getAll() {
        return catRepo.findAll();
    }

    public String deleteCategory(Integer id) {
        try {
            catRepo.deleteById(id);
            return "Category has been deleted";
        }catch(Exception e){
            return "Category is not present or has already been deleted";
        }
    }

    public Category updateCategory (Category category) {
        Category tmp = catRepo.findById(category.getId()).orElse(null);
        if (tmp != null) {
            tmp.setCategory(category.getCategory());
        }
        return catRepo.save(tmp);
    }

}
