package com.herve.fastfood.services;

import com.herve.fastfood.dtos.MenuRequest;
import com.herve.fastfood.dtos.MenuResponse;
import com.herve.fastfood.mappers.MenuMapper;
import com.herve.fastfood.models.Menu;
import com.herve.fastfood.repositories.MenuRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class MenuService {
    private final MenuRepo menuRepo;
    private final MenuMapper menuMapper;
    private final FileStorageService fileStorageService;

    public MenuService(MenuRepo menuRepo, MenuMapper menuMapper, FileStorageService fileStorageService) {
        this.menuRepo = menuRepo;
        this.menuMapper = menuMapper;
        this.fileStorageService = fileStorageService;
    }


    @Transactional
    public MenuResponse createMenu(MenuRequest menuRequest, MultipartFile image) {
        // 2. Création de l'entité Menu
        Menu menu = menuMapper.toEntity(menuRequest);

        // 3. Gestion de l'image
        if (image != null && !image.isEmpty()) {
            String imageUrl = fileStorageService.storeFile(image, "menu_" + UUID.randomUUID());
            menu.setImage(imageUrl);
        }

        // 4. Sauvegarde en base de données
        Menu savedMenu = menuRepo.save(menu);

        // 5. Conversion en DTO et retour
        return menuMapper.toDto(savedMenu);
    }

}
