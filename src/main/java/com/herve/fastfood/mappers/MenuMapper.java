package com.herve.fastfood.mappers;

import com.herve.fastfood.dtos.MenuRequest;
import com.herve.fastfood.dtos.MenuResponse;
import com.herve.fastfood.models.Menu;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuMapper {

    public Menu toEntity(MenuRequest menuRequest){

        if (menuRequest == null){
            return null;
        }

        Menu menu = new Menu();
        menu.setNom(menuRequest.getNom());
        menu.setDescription(menuRequest.getDescription());
        menu.setPrix(menuRequest.getPrix());
        menu.setCategorie(menuRequest.getCategorie());
        menu.setDisponible(menuRequest.getDisponible() != null ? menuRequest.getDisponible() : true);
        return menu;
    }

    public MenuResponse toDto(Menu menu){
        if (menu == null){
            return null;
        }
        MenuResponse menuResponse = new MenuResponse();
        menuResponse.setNom(menu.getNom());
        menuResponse.setMenuId(menu.getMenuId());
        menuResponse.setDescription(menu.getDescription());
        menuResponse.setPrix(menu.getPrix());
        menuResponse.setImage(menu.getImage());
        menuResponse.setDisponible(menu.getDisponible());
        menuResponse.setCategorie(menu.getCategorie());

        return menuResponse;
    }

    public List<MenuResponse> toDoList(List<Menu> menus){
        if (menus ==null){
            return new ArrayList<>();
        }
        return menus.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(MenuRequest menuRequest, Menu menu) {
        if (menuRequest == null || menu == null) {
            return;
        }
        menu.setNom(menuRequest.getNom());
        menu.setDescription(menuRequest.getDescription());
        menu.setPrix(menuRequest.getPrix());
        menu.setCategorie(menuRequest.getCategorie());
        menu.setDisponible(menuRequest.getDisponible() != null ? menuRequest.getDisponible() : menu.getDisponible());
    }
}
