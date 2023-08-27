package ru.practicum.ewm.category.service;

import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(NewCategoryDto dto);

    void deleteCategory(long id);

    CategoryDto updateCategory(long id, NewCategoryDto dto);

    CategoryDto getCategoryById(long id);

    List<CategoryDto> getCategories(int from, int size);
}
