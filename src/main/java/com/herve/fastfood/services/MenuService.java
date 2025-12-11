package com.herve.fastfood.services;

import com.herve.fastfood.dtos.MenuRequest;
import com.herve.fastfood.dtos.MenuResponse;
import com.herve.fastfood.mappers.MenuMapper;
import com.herve.fastfood.models.Menu;
import com.herve.fastfood.repositories.MenuRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
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

    public MenuResponse getMenuById(Long menuId){
        Menu menu = menuRepo.findById(menuId)
                .orElseThrow(()-> new RuntimeException("Menu non trouvé avec l'ID : " + menuId));
        return menuMapper.toDto(menu);
    }

    public MenuResponse updateMenu(Long menuId,MenuRequest menuRequest,MultipartFile image){
        Menu existingMenu = menuRepo.findById(menuId)
                .orElseThrow(()-> new RuntimeException("Menu non trouvé avec l'ID : " + menuId));
        menuMapper.updateEntityFromDto(menuRequest,existingMenu);

        if (image != null && !image.isEmpty()) {
            if (!Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
                throw new RuntimeException("Le fichier doit être une image");
            }
            String imageUrl = fileStorageService.storeFile(image, "menu_" + UUID.randomUUID());
            existingMenu.setImage(imageUrl);
        }

        Menu updatedMenu = menuRepo.save(existingMenu);
        return menuMapper.toDto(updatedMenu);
    }

    public Page<MenuResponse> getAllMenus(Pageable pageable){
        Page<Menu> menus = menuRepo.findAll(pageable);
        return menus.map(menuMapper::toDto);
    }

}
