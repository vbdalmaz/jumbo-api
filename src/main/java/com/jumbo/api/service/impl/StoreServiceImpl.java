package com.jumbo.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jumbo.api.exception.StoreDuplicatedException;
import com.jumbo.api.exception.StoreInvalidException;
import com.jumbo.api.model.Store;
import com.jumbo.api.model.enumeration.StoreLocationType;
import com.jumbo.api.repository.StoreRepository;
import com.jumbo.api.service.StoreService;

@Service
public class StoreServiceImpl implements StoreService {
	
	@Autowired
	private StoreRepository storeRepository;

	@Override
	public Page<Store> getStoresNearMe(Pageable pageable, double longitude, double latitude, long minDistance, long maxDistance) {
		return storeRepository.findStoresNear(pageable, longitude, latitude, minDistance, maxDistance);
	}

	@Override
	public Page<Store> getStoresByTypeNearMe(Pageable pageable, StoreLocationType storeLocationType, double longitude, double latitude,
			long minDistance, long maxDistance) {
		return storeRepository.findStoresNearAndByType(pageable, storeLocationType, longitude, latitude, minDistance, maxDistance);
	}

	@Override
	public Store getStore(String storeId) {
		return storeRepository.findByUuid(storeId);
	}

	@Override
	public Store createStore(Store store) {
		if(getStore(store.getUuid()) != null) {
			throw new StoreDuplicatedException("Store already exists");
		}
		
		return storeRepository.save(store);
	}

	@Override
	public Store updateStore(Store store) {
		if(getStore(store.getUuid()) == null) {
			throw new StoreInvalidException("Store does not exist");
		}
		
		return storeRepository.save(store);
	}

	@Override
	public void deleteStore(String storeId) {
		if(getStore(storeId) == null) {
			throw new StoreInvalidException("Store does not exist");
		}
		
		storeRepository.delete(this.getStore(storeId));
	}

	@Override
	public Page<Store> getAllStores(Pageable pageable) {
		return storeRepository.findAll(pageable);
	}

}
