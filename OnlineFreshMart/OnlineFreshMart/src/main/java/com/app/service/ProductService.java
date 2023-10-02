package com.app.service;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.app.entity.Product;

public interface ProductService {
	 
	
	void addProduct(Product product, MultipartFile productImmage);
	
	List<Product> getAllProducts();

    Product getProductById(int productId);

    List<Product> getProductsByCategory(int categoryId);

    void fetchProductImage(String productImageName, HttpServletResponse response);

    void deleteProduct(int productId);
    
    Product getProductByCategory(int productId, int categoryId);
    
    void updateProductStockAndPrice(int productId, int quantity, BigDecimal price);

}
