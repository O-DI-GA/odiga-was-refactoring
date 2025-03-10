package com.odiga.owner.entity;

import java.util.ArrayList;
import java.util.List;

import com.odiga.global.entity.BaseEntity;
import com.odiga.store.entity.Store;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Owner extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;

	private String password;

	private String name;

	@Builder.Default
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	private List<Store> stores = new ArrayList<>();

	public void addStore(Store store) {
		stores.add(store);
		store.setOwner(this);
	}
}
