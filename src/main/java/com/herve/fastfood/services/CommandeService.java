package com.herve.fastfood.services;

import com.herve.fastfood.dtos.ArticleCommandeRequest;
import com.herve.fastfood.dtos.CommandeRequest;
import com.herve.fastfood.dtos.CommandeResponse;
import com.herve.fastfood.mappers.CommandeMapper;
import com.herve.fastfood.models.*;
import com.herve.fastfood.repositories.CommandeRepo;
import com.herve.fastfood.repositories.MenuRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommandeService {

    private final CommandeRepo commandeRepo;
    private final CommandeMapper commandeMapper;
    private final MenuRepo menuRepo;

    @Transactional
    public CommandeResponse createCommande(CommandeRequest commandeRequest, Utilisateur utilisateur){
        Commande commande = commandeMapper.toEntity(commandeRequest,utilisateur);

        for (ArticleCommandeRequest articleRequest : commandeRequest.getArticles()) {
            Menu menu = menuRepo.findById(articleRequest.getMenuId())
                    .orElseThrow(() -> new RuntimeException("Menu non trouvé avec l'ID : " + articleRequest.getMenuId()));

            ArticleCommande articleCommande = new ArticleCommande();
            articleCommande.setQuantite(articleRequest.getQuantite());
            articleCommande.setPrixUnitaire(menu.getPrix());
            articleCommande.setMenu(menu);
            articleCommande.setCommande(commande);

            commande.ajouterArticle(articleCommande);
            commande.getArticles().add(articleCommande);
        }

        Commande savedCommande = commandeRepo.save(commande);
        return commandeMapper.toDto(savedCommande);

    }

    @Transactional
    public CommandeResponse confirmCommande(Long commandeId){
        Commande commande = commandeRepo.findById(commandeId)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID : " + commandeId));

        commande.setStatusCommande(StatusCommande.EN_PREPARATION);
        Commande updatedCommande = commandeRepo.save(commande);

        return commandeMapper.toDto(updatedCommande);
    }
}
