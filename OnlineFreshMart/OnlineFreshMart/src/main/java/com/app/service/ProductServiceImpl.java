package com.app.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.app.entity.Product;
import com.app.global_exceptions.ImageNotFoundException;
import com.app.repository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private StorageService storageService;

	// Add a new product along with its image
	@Override
	public void addProduct(Product product, MultipartFile productImmage) {
		// Store the product image and get the generated image name
		String productImageName = storageService.store(productImmage);
 
		 // Set the image name for the product and save it in the repository
		product.setImageName(productImageName);

		this.productRepo.save(product);
	}

	// Retrieve all products
	@Override
	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}

	// Retrieve a product by its ID
	@Override
	public Product getProductById(int productId) {
		Optional<Product> optional = productRepo.findById(productId);
		return optional.orElse(null);
	}

	// Retrieve products by a specific category
	@Override
	public List<Product> getProductsByCategory(int categoryId) {
		return productRepo.findByCategoryId(categoryId);
	}

	// Fetch and stream the product image to the client's response
	@Override
	public void fetchProductImage(String productImageName, HttpServletResponse response) {
		System.out.println("Loading file: " + productImageName);
		//Load the image 
		Resource resource = storageService.load(productImageName);
		if (resource != null) {
			try (InputStream in = resource.getInputStream()) {
				ServletOutputStream out = response.getOutputStream();
				FileCopyUtils.copy(in, out);
			} catch (IOException e) {
				// Handle the case when the image is not found
				 new ImageNotFoundException("Image Not Found");
			}
		}

	}

	
	// Delete a product by its ID
	@Override
	public void deleteProduct(int productId) {
		Optional<Product> optionalProduct = productRepo.findById(productId);
		if (optionalProduct.isPresent()) {
			Product product = optionalProduct.get();

			// Delete the associated image from the storage
			storageService.delete(product.getImageName());

			// Delete the product from the repository
			productRepo.delete(product);
		} else {
			throw new  EntityNotFoundException("Product with ID " + productId + " not found.");
		}

	}
	
	// Retrieve a product by its ID and category
	@Override
	public Product getProductByCategory(int productId, int categoryId) {
		return productRepo.findByIdAndCategoryId(productId, categoryId);
	}

	// Update product stock and price based on the given quantity and price
	@Override
	public void updateProductStockAndPrice(int productId, int quantity, BigDecimal price) {
		 Optional<Product> optionalProduct = productRepo.findById(productId);
		    if (optionalProduct.isPresent()) {
		        Product product = optionalProduct.get();
		        product.setQuantity(product.getQuantity() - quantity);
		        product.setPrice(product.getPrice().subtract(price));
		        productRepo.save(product);
		    }
		
	}

}
