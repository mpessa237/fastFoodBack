package com.herve.fastfood.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menus")
public class Menu {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;
    private String nom;
    private String description;
    private Double prix;
    private String image;
    private Boolean disponible = true;
    @Enumerated(EnumType.STRING)
    private Categorie categorie;
}
