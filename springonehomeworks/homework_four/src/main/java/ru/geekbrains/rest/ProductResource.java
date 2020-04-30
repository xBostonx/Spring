package ru.geekbrains.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entity.Product;
import ru.geekbrains.rest.exceptions.IdIsPresentException;
import ru.geekbrains.rest.exceptions.NotFoundException;
import ru.geekbrains.service.ProductService;

import java.util.List;

@RequestMapping("api/v01/product")
@RestController
public class ProductResource {

    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<Product> findAll() {
        return productService.getAllProduct();
    }

    @GetMapping(path = "/{id}/id", produces = "application/json")
    public Product findById(@PathVariable("id") long id) {
        return productService.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public void addNewProduct(@RequestBody Product product) {
        if (product.getId() != null) {
            throw new IdIsPresentException();
        }
        productService.save(product);
    }

    @PutMapping
    public void editProduct(@RequestBody Product product) {
        productService.save(product);
    }

    @DeleteMapping(path = "/{id}/id", produces = "application/json")
    public void deleteProduct(@PathVariable("id") long id) {
        if (!productService.findById(id).isPresent()) {
            throw new NotFoundException();
        }
        productService.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundHandeler(NotFoundException exc) {
        return new ResponseEntity<>("Not found!", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> idIsPresentHandler(IdIsPresentException exc) {
        return new ResponseEntity<>("Delete field \"ID\" in your request!", HttpStatus.BAD_REQUEST);
    }
}
