package ru.practicum.ewm.category.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.category.model.Category;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CategoryMapper {

    public Category newCategoryDtoToCategory(NewCategoryDto dto) {
        return Category.builder()
                .name(dto.getName())
                .build();
    }

    public CategoryDto categoryToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public List<CategoryDto> categoryToCategoryDto(List<Category> categories) {
        return categories.stream().map(CategoryMapper::categoryToCategoryDto).collect(Collectors.toList());
    }
}