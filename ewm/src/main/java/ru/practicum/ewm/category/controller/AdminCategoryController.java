package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.service.CategoryService;

import javax.validation.Valid;

import static ru.practicum.dto.Common.CommonForPaths.BY_ID_PATH;
import static ru.practicum.dto.Common.Messages.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        log.info(RECEIVED_POST, "/admin/categories");
        return categoryService.createCategory(newCategoryDto);
    }

    @DeleteMapping(BY_ID_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable long id) {
        log.info(RECEIVED_DELETE, "/admin/categories", id);
        categoryService.deleteCategory(id);
    }

    @PatchMapping(BY_ID_PATH)
    public CategoryDto updateCategory(@PathVariable long id,
                                      @Valid @RequestBody NewCategoryDto newCategoryDto) {
        log.info(RECEIVED_PATCH, "/admin/categories", id);
        return categoryService.updateCategory(id, newCategoryDto);
    }
}
