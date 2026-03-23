package org.arth.ecomm.service;

import lombok.RequiredArgsConstructor;
import org.arth.ecomm.model.Product;
import org.arth.ecomm.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository prodRepo;

    public boolean isProductExist(Long id) {
        return prodRepo.existsById(id);
    }

    public List<Product> getAllProducts() {
        return prodRepo.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return prodRepo.findById(id);
    }

    public Product addOrUpdateProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());

        return prodRepo.save(product);
    }

    public void deleteProduct(Long prodId) {

        prodRepo.deleteById(prodId);

    }


    public List<Product> getSearchedProducts(String keyword) {
        return prodRepo.searchProducts(keyword);
    }
}
