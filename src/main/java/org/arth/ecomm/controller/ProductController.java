package org.arth.ecomm.controller;

import lombok.RequiredArgsConstructor;
import org.arth.ecomm.model.Product;
import org.arth.ecomm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor
public class ProductController {


    private final ProductService prodService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(prodService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> prod = prodService.getProductById(id);
        return prod.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImageById(@PathVariable Long id) {
        Optional<Product> prod = prodService.getProductById(id);
        return prod.map(product -> new ResponseEntity<>(product.getImageData(), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
        try {
            Product savedProd = prodService.addOrUpdateProduct(product, imageFile);
            return new ResponseEntity<>(savedProd, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestPart("product") Product product,
                                           @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            Product updatedProd = prodService.addOrUpdateProduct(product, imageFile);
            return new ResponseEntity<>(updatedProd, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if(!prodService.isProductExist(id)) return ResponseEntity.notFound().build();
        prodService.deleteProduct(id);
        return ResponseEntity.ok("Successfully deleted!");

    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> getSearchedProducts(@RequestParam("name") String keyword) {
        if(keyword == null || keyword.isEmpty()) return ResponseEntity.ok(List.of());
        return ResponseEntity.ok(prodService.getSearchedProducts(keyword));
    }

}
