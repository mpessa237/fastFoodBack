package com.herve.fastfood.mappers;

import com.herve.fastfood.dtos.ArticleCommandeRequest;
import com.herve.fastfood.dtos.CommandeRequest;
import com.herve.fastfood.dtos.CommandeResponse;
import com.herve.fastfood.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommandeMapper {

    private final ArticleCommandeMapper articleCommandeMapper;

    // Convertit CommandeRequest en entité Commande
    public Commande toEntity(CommandeRequest commandeRequest, Utilisateur utilisateur, List<Menu> menus) {
        if (commandeRequest == null || utilisateur == null || menus == null) {
            return null;
        }

        Commande commande = new Commande();
        commande.setUtilisateur(utilisateur);
        commande.setStatusCommande(StatusCommande.EN_ATTENTE);
        commande.setTotalCommande(0.0);

        // Ajoute les articles à la commande
        for (ArticleCommandeRequest articleCommandeRequest : commandeRequest.getArticles()) {
            Menu menu = menus.stream()
                    .filter(m -> m.getMenuId().equals(articleCommandeRequest.getMenuId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Menu non trouvé : " + articleCommandeRequest.getMenuId()));

            ArticleCommande articleCommande = articleCommandeMapper.toEntity(articleCommandeRequest, menu);
            commande.getArticles().add(articleCommande);
            articleCommande.setCommande(commande);
            commande.setTotalCommande(commande.getTotalCommande() + articleCommande.getPrixUnitaire() * articleCommande.getQuantite());
        }

        return commande;
    }

    // Convertit entité Commande en CommandeResponse
    public CommandeResponse toDto(Commande commande) {
        if (commande == null) {
            return null;
        }

        CommandeResponse commandeResponse = new CommandeResponse();
        commandeResponse.setCommandeId(commande.getCommandeId());
        commandeResponse.setUtilisateurId(commande.getUtilisateur().getUtilisateurId());
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
