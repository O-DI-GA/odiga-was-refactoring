package com.odiga.menu.entity;

import com.odiga.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    private String titleImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

//    public void setCategory(Category category) {
//        this.category = category;
//        category.addMenu(this);
//    }
}
