package com.jumbo.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.jumbo.api.model.Store;
import com.jumbo.api.model.enumeration.StoreLocationType;

public interface StoreRepository extends MongoRepository<Store, String> {
	
	Store findByUuid(String uuid);
	Store findByLocationType(StoreLocationType StoreLocationType);
	List<Store> findAll();
	
	@Query("{ location: { $near : { $geometry: {coordinates: [ ?0, ?1 ] }, $minDistance: ?2 , $maxDistance: ?3 }}}")
	Page<Store> findStoresNear(Pageable pageable, double longitude,double latitude, long minDistance, long maxDistance);
	
	@Query("{ $and: [ {locationType: ?0}, {location: { $near : { $geometry: {coordinates: [ ?1, ?2 ] }, $minDistance: ?3 , $maxDistance: ?4 }}}]}")
	Page<Store> findStoresNearAndByType(Pageable pageable, StoreLocationType storeLocationType, double longitude, double latitude, long minDistance, long maxDistance);
}
