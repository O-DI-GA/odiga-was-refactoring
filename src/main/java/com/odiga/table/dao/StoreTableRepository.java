package com.odiga.table.dao;

import com.odiga.table.entity.StoreTable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreTableRepository extends JpaRepository<StoreTable, Long> {

    List<StoreTable> findByStoreId(Long storeId);

    boolean existsByTableNumberAndStoreId(int tableNumber, Long storeId);
}
