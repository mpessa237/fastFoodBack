package com.herve.fastfood.controllers;

import com.herve.fastfood.dtos.MenuRequest;
import com.herve.fastfood.dtos.MenuResponse;
import com.herve.fastfood.services.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
@PreAuthorize("hasAuthority('ROLE_ADMIN')") // Seuls les ADMIN peuvent accéder à cet endpoint
public class MenuController {

    private final MenuService menuService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MenuResponse> createMenu(
            @Valid @ModelAttribute MenuRequest menuRequest,
            @RequestParam(required = false)MultipartFile image
            ){
        MenuResponse menuResponse = menuService.createMenu(menuRequest, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(menuResponse);
    }

}
