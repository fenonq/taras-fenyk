package com.epam.spring.homework3.model;

import com.epam.spring.homework3.model.enums.Roles;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQuery(
        name = "User.findByUsername",
        query = "SELECT u from User u where u.username = :username")
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private Roles role;

    @Column(name = "active")
    private Boolean active = true;

    @ManyToMany
    @JoinTable(name = "customer_cart",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private List<Dish> cart = new ArrayList<>();

}
