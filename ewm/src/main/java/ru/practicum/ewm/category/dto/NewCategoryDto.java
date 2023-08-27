package ru.practicum.ewm.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCategoryDto {

    @NotBlank(message = "Specify the category name")
    @Size(min = 1, max = 50, message = "Allowable size of name 1-50 symbols")
    private String name;
}
