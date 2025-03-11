package com.odiga.order.entity;

import com.odiga.common.entity.BaseEntity;
import com.odiga.table.entity.StoreTable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TableOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORE_TABLE_ID")
    private StoreTable storeTable;

    @OneToMany(mappedBy = "tableOrder", cascade = CascadeType.ALL)
    private List<TableOrderMenu> tableOrderMenus = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private TableOrderStatus tableOrderStatus;
}
