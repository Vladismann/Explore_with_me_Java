package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.CategoryMapper;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repo.CategoryRepo;
import ru.practicum.ewm.common.CommonMethods;
import ru.practicum.ewm.common.CustomPageRequest;
import ru.practicum.ewm.event.repo.EventRepo;
import ru.practicum.ewm.exceptions.WrongConditionsException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final EventRepo eventRepo;

    @Override
    public CategoryDto createCategory(NewCategoryDto dto) {
        Category category = categoryRepo.save(CategoryMapper.newCategoryDtoToCategory(dto));
        log.info("Добавлена категория: {}", category);
        return CategoryMapper.categoryToCategoryDto(category);
    }

    @Override
    public void deleteCategory(long id) {
        CommonMethods.checkObjectIsExists(id, categoryRepo);
        if (!eventRepo.findAllByCategoryId(id).isEmpty()) {
            throw new WrongConditionsException("The category is not empty");
        }
        categoryRepo.deleteById(id);
        log.info("Удалена категория: {}", id);
    }

    @Override
    public CategoryDto updateCategory(long id, NewCategoryDto dto) {
        CommonMethods.checkObjectIsExists(id, categoryRepo);
        Category category = categoryRepo.getReferenceById(id);
        category.setName(dto.getName());
        log.info("Обновлена категория: {}", category);
        return CategoryMapper.categoryToCategoryDto(categoryRepo.save(category));
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryDto getCategoryById(long id) {
        CommonMethods.checkObjectIsExists(id, categoryRepo);
        Category category = categoryRepo.getReferenceById(id);
        log.info("Запрошена категория: {}", category);
        return CategoryMapper.categoryToCategoryDto(category);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        Pageable pageable = new CustomPageRequest(from, size);
        List<CategoryDto> categories = CategoryMapper.categoryToCategoryDto(categoryRepo.findAll(pageable));
        log.info("Запрошен список категорий в размере: {}", categories.size());
        return categories;
    }
}
