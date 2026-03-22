package org.arth.ecomm.service;

import org.arth.ecomm.model.Product;
import org.arth.ecomm.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository prodRepo;

    public List<Product> getAllProducts() {

        return prodRepo.findAll();
    }



    public Optional<Product> getProductById(Long id) {
        return prodRepo.findById(id);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {

        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());

        return prodRepo.save(product);
    }

    @Autowired
    public void setProdRepo(ProductRepository prodRepo) {
        this.prodRepo = prodRepo;
    }


}
