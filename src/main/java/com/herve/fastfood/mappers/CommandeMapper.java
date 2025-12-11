package com.herve.fastfood.mappers;

import com.herve.fastfood.dtos.ArticleCommandeRequest;
import com.herve.fastfood.dtos.CommandeRequest;
import com.herve.fastfood.dtos.CommandeResponse;
import com.herve.fastfood.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommandeMapper {

    private final ArticleCommandeMapper articleCommandeMapper;

    // Convertit CommandeRequest en entité Commande
    public Commande toEntity(CommandeRequest commandeRequest, Utilisateur utilisateur) {
        Commande commande = new Commande();
        commande.setUtilisateur(utilisateur);
        commande.setDateCommande(LocalDateTime.now());
        commande.setStatusCommande(StatusCommande.EN_ATTENTE);
        commande.setTotalCommande(0.0);

        return commande;
    }



    // Convertit entité Commande en CommandeResponse
    public CommandeResponse toDto(Commande commande) {
        if (commande == null) {
            return null;
        }

        CommandeResponse commandeResponse = new CommandeResponse();
        commandeResponse.setCommandeId(commande.getCommandeId());
        commandeResponse.setNom(commande.getUtilisateur().getNom());
        commandeResponse.setArticles(articleCommandeMapper.toDtoList(commande.getArticles()));
        commandeResponse.setStatusCommande(commande.getStatusCommande());
        commandeResponse.setDateCommande(commande.getDateCommande());
        commandeResponse.setTotalCommande(commande.getTotalCommande());
        return commandeResponse;
    }

    // Convertit une liste d'entités Commande en liste de CommandeResponse
    public List<CommandeResponse> toDtoList(List<Commande> commandes) {
        if (commandes == null) {
            return new ArrayList<>();
        }
        return commandes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
