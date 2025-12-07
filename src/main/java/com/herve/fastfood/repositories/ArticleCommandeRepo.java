package com.herve.fastfood.repositories;

import com.herve.fastfood.models.ArticleCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCommandeRepo extends JpaRepository<ArticleCommande,Long> {
}
