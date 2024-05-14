package com.jaredsantiag.backendcartapp.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @NotBlank
    @Size(max = 100)
    private String street;

    @NotBlank
    @Size(max = 80)
    private String number;

    @NotBlank
    @Size(max = 100)
    private String suburb;

    @NotNull
    @Column(name="post_code")
    private Integer postCode;

    @NotBlank
    @Size(max = 80)
    private String city;

    @NotBlank
    @Size(max = 40)
    private String state;

    @NotBlank
    @Size(max = 60)
    private String country;
}
