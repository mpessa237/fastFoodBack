package com.herve.fastfood.repositories;

import com.herve.fastfood.models.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepo extends JpaRepository<Menu,Long> {

    Page <Menu> findAll(Pageable pageable);
}
