
package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.entity.Category;
import com.app.repository.CategoryRepository;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;

	// Method to retrieve all categories from the repository
	@Override
	public List<Category> getAllCategories() {
		return categoryRepo.findAll();
	}

	// Method to add a new category to the repository
	@Override
	public boolean addCategory(Category category) {
		//Save the category to the Repository
		Category addedCategory = categoryRepo.save(category);
		// Return true if the category was successfully added, otherwise return false
		return addedCategory != null;
	}
}
