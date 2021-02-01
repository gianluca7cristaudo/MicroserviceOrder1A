package Productmanager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import product.Product;

import java.util.Optional;

public interface ProductRepository extends CrudRepository <Product,Integer>, PagingAndSortingRepository<Product, Integer> {
    public Optional<Product> findByIdAndQuantityGreaterThanEqual(Integer id, Integer quantity);
}