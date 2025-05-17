package com.odiga.table.entity;

import com.odiga.common.entity.BaseEntity;
import com.odiga.order.entity.TableOrder;
import com.odiga.store.entity.Store;
import com.odiga.table.enums.TableStatus;
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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(uniqueConstraints = {
    @UniqueConstraint(name = "UNIQUE_TABLE_STORE", columnNames = {"TABLE_NUMBER", "STORE_ID"})
})
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class StoreTable extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int maxSeat;

    private int tableNumber;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORE_ID", nullable = false)
    private Store store;

    @Builder.Default
    @OneToMany(mappedBy = "storeTable")
    private List<TableOrder> tableOrders = new ArrayList<>();

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private TableStatus tableStatus = TableStatus.EMPTY;
}
