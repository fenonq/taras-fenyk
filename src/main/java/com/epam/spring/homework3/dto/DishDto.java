package com.epam.spring.homework3.dto;

import com.epam.spring.homework3.dto.group.OnCreate;
import com.epam.spring.homework3.dto.group.OnUpdate;
import com.epam.spring.homework3.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishDto {

    @Null(message = "{validation.message.id.null}", groups = OnCreate.class)
    @NotNull(message = "{validation.message.id.notNull}", groups = OnUpdate.class)
    private Long id;

    @NotBlank(message = "{validation.message.name.notBlank}", groups = OnCreate.class)
    private String name;

    @NotBlank(message = "{validation.message.description.notBlank}", groups = OnCreate.class)
    private String description;

    @NotNull(message = "{validation.message.category.notNull}", groups = OnCreate.class)
    private Category category;

    @Min(value = 1, message = "{validation.message.price.min}")
    @Max(value = 9999, message = "{validation.message.price.max}")
    private int price;

    @Min(value = 1, message = "{validation.message.weight.min}")
    @Max(value = 9999, message = "{validation.message.weight.max}")
    private int weight;

}
