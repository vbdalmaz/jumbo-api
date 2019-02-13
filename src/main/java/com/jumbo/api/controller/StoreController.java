package com.jumbo.api.controller;

import static com.jumbo.api.utils.JumboAPIStringConfig.STORE_BASE_URL;
import static com.jumbo.api.utils.JumboAPIStringConfig.STORE_GET_ALL_URL;
import static com.jumbo.api.utils.JumboAPIStringConfig.STORE_BY_ID_URL;
import static com.jumbo.api.utils.JumboAPIStringConfig.PAGE_MAX_SIZE;
import static com.jumbo.api.utils.JumboAPIStringConfig.STORE_NEAR_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jumbo.api.model.Store;
import com.jumbo.api.model.enumeration.StoreLocationType;
import com.jumbo.api.service.StoreService;;

@RestController
public class StoreController extends BaseController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private StoreService storeService;

	@CrossOrigin(origins = "*")
	@RequestMapping(path = STORE_GET_ALL_URL, method = { GET }, produces = { APPLICATION_JSON_UTF8_VALUE })
	public @ResponseBody ResponseEntity<PagedResources<Store>> getAllStores(
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "size", defaultValue = "10", required = false) int size) {
		LOG.info("Getting all stores");
		size = (size > PAGE_MAX_SIZE || size <= 0) ? PAGE_MAX_SIZE : size;
		
		return createResponse(buildPagedResources(storeService.getAllStores(new PageRequest(page, size))));
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(path = STORE_BY_ID_URL, method = { GET }, produces = { APPLICATION_JSON_UTF8_VALUE })
	public @ResponseBody ResponseEntity<Store> getStoreByID(@PathVariable("storeId") @NotBlank String storeId) {
		LOG.info("Getting store : {}", storeId);

		return createResponse(storeService.getStore(storeId));
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(path = STORE_NEAR_URL, method = { GET }, produces = { APPLICATION_JSON_UTF8_VALUE })
	public @ResponseBody ResponseEntity<PagedResources<Store>> getStoresNearMe(
			@RequestParam(name = "storeLocationType", required = false) StoreLocationType storeLocationType,
			@RequestParam(name = "longitude") double longitude,
			@RequestParam(name = "latitude") double latitude,
			@RequestParam(name = "minDistance", defaultValue = "0", required = false) long minDistance,
			@RequestParam(name = "maxDistance", defaultValue = "50000", required = false) long maxDistance,
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "size", defaultValue = "10", required = false) int size) {
		LOG.info("Finding store near");
		size = (size > PAGE_MAX_SIZE || size <= 0) ? PAGE_MAX_SIZE : size;
		
		if (storeLocationType != null) {
			return createResponse(buildPagedResources(storeService.getStoresByTypeNearMe(new PageRequest(page, size),
					storeLocationType, longitude, latitude, minDistance, maxDistance)));
		}
		return createResponse(buildPagedResources(storeService.getStoresNearMe(new PageRequest(page, size), longitude,
				latitude, minDistance, maxDistance)));
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(path = STORE_BASE_URL, method = { POST }, produces = { APPLICATION_JSON_UTF8_VALUE }, consumes = {
			APPLICATION_JSON_UTF8_VALUE })
	public @ResponseBody ResponseEntity<Store> createStore(@RequestBody @NotBlank Store store) {
		LOG.info("Creating store : {}", store);

		return createResponse(storeService.createStore(store));
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(path = STORE_BASE_URL, method = { PUT }, produces = { APPLICATION_JSON_UTF8_VALUE }, consumes = {
			APPLICATION_JSON_UTF8_VALUE })
	public @ResponseBody ResponseEntity<Store> updateStore(@RequestBody @NotBlank Store store) {
		LOG.info("Updating store : {}", store);
		return createResponse(storeService.updateStore(store));
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(path = STORE_BY_ID_URL, method = { DELETE }, produces = { APPLICATION_JSON_UTF8_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public void deleteStore(@PathVariable("storeId") @NotBlank String storeId) {
		LOG.info("Deleting store : {}", storeId);
		storeService.deleteStore(storeId);
	}
}
