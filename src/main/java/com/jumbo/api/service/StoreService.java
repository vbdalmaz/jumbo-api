package com.jumbo.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jumbo.api.model.Store;
import com.jumbo.api.model.enumeration.StoreLocationType;

public interface StoreService {

	Page<Store> getStoresNearMe(Pageable pageable, double longitude, double latitude, long minDistance,
			long maxDistance);

	Page<Store> getStoresByTypeNearMe(Pageable pageable, StoreLocationType storeLocationType, double longitude,
			double latitude, long minDistance, long maxDistance);

	Store getStore(String storeId);

	Store createStore(Store store);

	Store updateStore(Store store);

	void deleteStore(String storeId);

	Page<Store> getAllStores(Pageable pageable);
}
