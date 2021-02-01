package Productmanager.controller;

import Productmanager.exceptionHandler.ProductNotFoundException;
import Productmanager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import product.Product;

import java.util.List;

@Controller
@RequestMapping(path = "/product")
public class ProductController {

    @Autowired
    ProductService service;

    @PostMapping(path = "/add")
    public @ResponseBody
    Product addProduct(@RequestBody Product product) {
        return service.addProduct(product);
    }

    @GetMapping(path = "/id/{id}")
    public @ResponseBody
    Product getProduct(@PathVariable Integer id) throws ProductNotFoundException {
        return service.getProduct(id);
    }

    @GetMapping(value = "all")
    public @ResponseBody
    Iterable<Product> getAll() {
        return service.getAll();
    }

    @GetMapping( value = "all",params = { "per_page", "page" } )
    public @ResponseBody
    ResponseEntity<List<Product>> getAllFiltered(
            @RequestParam(name="page",defaultValue = "0") Integer pageNo,
            @RequestParam(name="per_page",defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy)
    {
        List<Product> list = service.getAllProduct(pageNo, pageSize, sortBy);
        return new ResponseEntity<List<Product>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/del/{id}")
    public @ResponseBody
    String deleteProduct(@PathVariable Integer id) {
        return service.deleteProduct(id);
    }

    @PutMapping(path="/update")
    public @ResponseBody
    Product updateProduct (@RequestBody Product product) {
        return service.updateProduct(product);
    }

}
