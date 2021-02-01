package Productmanager.controller;

import Productmanager.exceptionHandler.CategoryNotFoundException;
import Productmanager.jsonModel.ProductCategories;
import Productmanager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import product.Category;

@Controller
@RequestMapping(path = "/categories")
public class CategoryController {

    @Autowired
    CategoryService service;

    @PostMapping(path = "/add")
    public @ResponseBody
    Category addProduct(@RequestBody Category category) {
        return service.addCategory(category);
    }

    @PostMapping(path = "/product")
    public @ResponseBody
    String addProduct(@RequestBody ProductCategories productRequest) {
        return service.addCategoryToProduct(productRequest);
    }

    @GetMapping(path = "/name/{name}")
    public @ResponseBody
    Category getCategory(@PathVariable String name) throws CategoryNotFoundException {
        return service.getCategory(name);
    }

    @GetMapping(value = "all")
    public @ResponseBody
    Iterable<Category> getAll() {
        return service.getAll();
    }

    @DeleteMapping(path = "/del/{id}")
    public @ResponseBody
    String deleteProduct(@PathVariable Integer id) {
        return service.deleteCategory(id);
    }

    @PutMapping(path="/update")
    public @ResponseBody
    Category updateProduct (@RequestBody Category category) {
        return service.updateCategory(category);
    }

}
