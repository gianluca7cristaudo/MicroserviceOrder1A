package Productmanager.repository;

import org.springframework.data.repository.CrudRepository;
import product.Category;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category,Integer> {
    public Optional<Category> findCategoryByCategory(String category);
}