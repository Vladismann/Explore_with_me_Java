package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.dto.Common.CommonForPaths.BY_ID_PATH;
import static ru.practicum.dto.Common.Messages.RECEIVED_GET;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/categories")
@Validated
public class PublicCategoryController {

    private final CategoryService categoryService;

    @GetMapping(BY_ID_PATH)
    public CategoryDto getCategory(@PathVariable long id) {
        log.info(RECEIVED_GET, "/categories", id);
        return categoryService.getCategoryById(id);
    }

    @GetMapping()
    public List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                           @RequestParam(defaultValue = "10") @Positive int size) {
        log.info(RECEIVED_GET, "/categories");
        return categoryService.getCategories(from, size);
    }
}
