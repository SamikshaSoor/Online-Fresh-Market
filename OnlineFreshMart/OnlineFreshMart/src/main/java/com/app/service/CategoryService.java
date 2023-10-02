package com.app.service;

import java.util.List;

import com.app.entity.Category;

public interface CategoryService {
	List<Category> getAllCategories();

	boolean addCategory(Category category);
}
