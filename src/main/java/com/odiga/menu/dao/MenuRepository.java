package com.odiga.menu.dao;

import com.odiga.menu.entity.Menu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByCategoryId(Long categoryId);
}
