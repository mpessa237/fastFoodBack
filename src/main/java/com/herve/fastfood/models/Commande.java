package com.herve.fastfood.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "commandes")
public class Commande {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commandeId;
    private LocalDateTime dateCommande;
    @Enumerated(EnumType.STRING)
    private StatusCommande statusCommande;
    private Double totalCommande;

    public void ajouterArticle(ArticleCommande articleCommande){
        articleCommande.setCommande(this);
        this.totalCommande += articleCommande.getPrixUnitaire() * articleCommande.getQuantite();
    }


    //+utilisateur: Utilisateur
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id",nullable = false)
    private Utilisateur utilisateur;
    //+articles: List~ArticleCommande~
    @OneToMany(mappedBy = "commande",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ArticleCommande> articles = new ArrayList<>();
    //facture
    @OneToOne(mappedBy = "commande",cascade = CascadeType.ALL,orphanRemoval = true)
    private Facture facture;
}
