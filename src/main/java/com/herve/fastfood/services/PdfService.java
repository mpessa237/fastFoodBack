package com.herve.fastfood.services;

import com.herve.fastfood.models.ArticleCommande;
import com.herve.fastfood.models.Commande;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte [] generatePdf(Commande commande){
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            // Ajouter des informations à la facture
            document.add(new Paragraph("Facture #" + commande.getCommandeId()));
            document.add(new Paragraph("Date: " + java.time.LocalDateTime.now()));
            document.add(new Paragraph("Client: " + commande.getUtilisateur().getNom()));
            document.add(new Paragraph("Statut: " + commande.getStatusCommande()));
            document.add(new Paragraph("Total: " + commande.getTotalCommande() + " €"));

            // Ajouter les articles de la commande
            document.add(new Paragraph("\nArticles:"));
            for (ArticleCommande article : commande.getArticles()) {
                document.add(new Paragraph(
                        article.getMenu().getNom() +
                                " - Quantité: " + article.getQuantite() +
                                " - Prix unitaire: " + article.getPrixUnitaire() +
                                " - Total: " + (article.getQuantite() * article.getPrixUnitaire())
                ));
            }

            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }

        return baos.toByteArray();
    }
}
