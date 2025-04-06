package com.odiga.store.dao;

import com.odiga.store.entity.Store;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByOwnerId(Long ownerId);
}
