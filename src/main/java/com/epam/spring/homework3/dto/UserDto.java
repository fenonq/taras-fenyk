package com.epam.spring.homework3.dto;

import com.epam.spring.homework3.dto.group.OnCreate;
import com.epam.spring.homework3.dto.group.OnUpdate;
import com.epam.spring.homework3.model.Dish;
import com.epam.spring.homework3.model.enums.Roles;
import com.epam.spring.homework3.validation.ValidUsername;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Null(message = "{validation.message.id.null}", groups = OnCreate.class)
    @NotNull(message = "{validation.message.id.notNull}", groups = OnUpdate.class)
    private Long id;

    @NotBlank(message = "{validation.message.firstName.notBlank}", groups = OnCreate.class)
    private String firstName;

    @NotBlank(message = "{validation.message.secondName.notBlank}", groups = OnCreate.class)
    private String lastName;

    @ValidUsername(message = "{validation.message.userName.unique}")
    @Null(message = "{validation.message.userName.null}", groups = OnUpdate.class)
    @NotBlank(message = "{validation.message.userName.notBlank}", groups = OnCreate.class)
    private String username;

    @Null(message = "{validation.message.password.null}", groups = OnUpdate.class)
    @NotBlank(message = "{validation.message.password.notBlank}", groups = OnCreate.class)
    private String password;

    @Null(message = "{validation.message.role.null}", groups = OnUpdate.class)
    @NotNull(message = "{validation.message.role.notNull}", groups = OnCreate.class)
    private Roles role;

    @Null(message = "{validation.message.cart.null}")
    private List<Dish> cart;

}
