package com.herve.fastfood.controllers;

import com.herve.fastfood.dtos.MenuRequest;
import com.herve.fastfood.dtos.MenuResponse;
import com.herve.fastfood.mappers.MenuMapper;
import com.herve.fastfood.models.Menu;
import com.herve.fastfood.repositories.MenuRepo;
import com.herve.fastfood.services.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;
    private final MenuRepo menuRepo;
    private final MenuMapper menuMapper;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // Seuls les ADMIN peuvent accéder à cet endpoint
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MenuResponse> createMenu(
            @Valid @ModelAttribute MenuRequest menuRequest,
            @RequestParam(required = false)MultipartFile image
            ){
        MenuResponse menuResponse = menuService.createMenu(menuRequest, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(menuResponse);
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponse> getMenuById(@PathVariable Long menuId){
        MenuResponse menuResponse = menuService.getMenuById(menuId);
        return ResponseEntity.ok(menuResponse);
    }

    @GetMapping
    public ResponseEntity<Page<MenuResponse>> getAllMenus(@PageableDefault(size = 10) Pageable pageable) {
        Page<Menu> menus = menuRepo.findAll(pageable);
        Page<MenuResponse> menuResponses = menus.map(menuMapper::toDto);
        return ResponseEntity.ok(menuResponses);
    }

    @PatchMapping("/{menuId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<MenuResponse> updateMenu(@PathVariable Long menuId,
                                                   @Valid @ModelAttribute MenuRequest menuRequest,
                                                   @RequestParam(required = false) MultipartFile image){
        MenuResponse menuResponse = menuService.updateMenu(menuId, menuRequest, image);
        return ResponseEntity.ok(menuResponse);
    }

}
