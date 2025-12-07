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
@Table(name = "articleCommandes")
public class ArticleCommande {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleCommandeId;
    private Integer quantite = 1;
    private Double prixUnitaire;

    //+commande: Commande
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id",nullable = false)
    private Commande commande;

    //+menu: Menu
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id",nullable = false)
    private Menu menu;
}
