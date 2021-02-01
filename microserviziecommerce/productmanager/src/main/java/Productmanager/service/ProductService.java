package Productmanager.service;

import Productmanager.exceptionHandler.ProductNotFoundException;
import Productmanager.jsonModel.OrderValidation;
import com.google.gson.Gson;
import Productmanager.jsonModel.OrderCompleted;
import Productmanager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import product.Product;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional (propagation= Propagation.REQUIRED)
public class ProductService {

    @Value("${kafkaTopicOrders}")
    private String topicOrders;

    @Value("${kafkaTopicLogging}")
    private String topicLogging;

    @Value("${kafkaTopicNotifications}")
    private String topicNotifications;

    @Autowired
    private KafkaTemplate<String,String> template;

    @Autowired
    ProductRepository repository;

    @Autowired
    CategoryService catService;

    public List<Product> getAllProduct(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Product> pagedResult = repository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return null;
        }
    }

        public Product addProduct( Product product) {
            try {
                return repository.save(product);
            }catch(Exception e){
                return null;
            }

        }

        public Product getProduct(Integer id) throws ProductNotFoundException {
            Optional<Product> productOptional = repository.findById(id);
                if (productOptional.isPresent()==false){
                    throw new ProductNotFoundException();
                }
            return productOptional.get();
        }

        public Iterable<Product> getAll() {
            return repository.findAll();
        }

        public String deleteProduct(Integer id) {
            try {
                repository.deleteById(id);
                return "Product has been deleted";
            }catch(Exception e){
                return "Product is not present or has already been deleted";
            }
        }

        public Product updateProduct (Product product) {
            Product tmp = repository.findById(product.getId()).orElse(null);
            if (tmp != null) {
                tmp.setBrand(product.getBrand());
                tmp.setDescription(product.getDescription());
                tmp.setModel(product.getModel());
                tmp.setPrice(product.getPrice());
                tmp.setQuantity(product.getQuantity());
            }
            return repository.save(tmp);
        }

        public void updateProductByOrder(OrderCompleted order) {
            List<Map<Integer,Integer>> unavailable=new ArrayList<>();
            OrderValidation orderValidation = new OrderValidation();
            String value;
            Double total=0.0;
            Integer status=0;

                for(Map<Integer,Integer> entry: order.getProducts()){
                    Optional<Product> optionalProduct = repository.findByIdAndQuantityGreaterThanEqual(entry.keySet().iterator().next(),entry.values().iterator().next());
                        if(optionalProduct.isPresent()==false){
                            unavailable.add(entry);
                        }else{
                            total = total + (entry.values().iterator().next() * optionalProduct.get().getPrice());
                        }
               
                }

                if( unavailable.isEmpty()==false && total!=order.getTotal()) {
                    status = -3;
                }else if( unavailable.isEmpty()==false ) {
                    status = -1;
                }else if( total.doubleValue() != order.getTotal().doubleValue() ) {
                    status = -2;
                }else{
                    Product updatedProduct;
                        for(Map<Integer,Integer> entry: order.getProducts()){
                            updatedProduct = repository.findById(entry.keySet().iterator().next()).get();
                            repository.save(updatedProduct.setQuantity( updatedProduct.getQuantity() - entry.values().iterator().next() ));
                        }
                }

        orderValidation.setOrderId(order.getOrderId());
        orderValidation.setStatus(status);
        orderValidation.setExtraArgs(unavailable);
        orderValidation.setTimestamp(Instant.now(Clock.systemUTC()).toString());

        value = new Gson().toJson(orderValidation,OrderValidation.class);
        pushOnTopic(topicOrders,"order_validation",value);
        pushOnTopic(topicNotifications,"order_validation",value);
            if(status==-2 || status==-3) {
                pushOnTopic(topicLogging, "order_validation", value);
            }
    }

    public void pushOnTopic(String topic,String key,String value){
        template.send(topic,key,value);
    }

}
