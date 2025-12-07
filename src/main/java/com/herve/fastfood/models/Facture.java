package com.herve.fastfood.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "factures")
public class Facture {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long factureId;
    private LocalDateTime dateEmission;

    @Lob
    private byte [] pdf;

    //cmde:Cmde
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id",nullable = false)
    private Commande commande;
}
