package com.jumbo.api.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.jumbo.api.handler.ResponseError;
import com.jumbo.api.model.Location;
import com.jumbo.api.model.Store;
import com.jumbo.api.model.enumeration.StoreLocationType;;

public class ObjectCreator {
	
	private static Store store1;
	private static Store store2;
	
	public static ResponseError createResponseError(Integer erroCode, String message) {
		return new ResponseError(erroCode, message);
	}
	
	public static ResponseError createResponseErrorForTest(Integer erroCode, String message) {
		ResponseError error =  new ResponseError();
		error.setCode(erroCode);
		error.setMessage(message);
		
		return error;
	}

	public static Store createStore(String uuid, String city, String postalCode, String street, String street2,
			String street3, String addressName, Location location, String complexNumber, Boolean showWarningMessage,
			String todayOpen, StoreLocationType locationType, Boolean collectionPoint, String sapStoreID,
			String todayClose) {
		return new Store(uuid, city, postalCode, street, street2, street3, addressName, location, complexNumber,
				showWarningMessage, todayOpen, locationType, collectionPoint, sapStoreID, todayClose);
	}

	public static Store getStore1() {
		if(store1 == null)
			store1 = createStore("EOgKYx4XFiQAAAFJa_YYZ4At", "'s Gravendeel", "3295 BD", "Kerkstraat", "37", "",
				"Jumbo 's Gravendeel Gravendeel Centrum", getLocation(), "33249", new Boolean(true), "08:00",
				StoreLocationType.SupermarktPuP, new Boolean(true), "3605", "20:00");
		
		return store1;
	}
	
	public static Store getStore2() {
		if(store2 == null)
			store2 = createStore("s2IKYx4X0UUAAAFIhpoYwKxK", "Roosendaal", "4703 GX", "Rembrandtgalerij", "103-109", "",
				"Jumbo Roosendaal Rembrandtgalerij", createLocation("Point", new double[] { 4.443489,  51.534129 }), "30474", new Boolean(true), "08:00",
				StoreLocationType.Supermarkt, new Boolean(true), "6392", "20:00");
		
		return store2;
	}
	
	public static List<Store> getStoreList(){
		List<Store> list = new ArrayList<Store>();
		list.add(getStore1());
		list.add(getStore2());
		
		return list;
	}
	
	public static Page<Store> getStoresPage(){
		return new PageImpl<Store>(getStoreList(), getPageRequest(), getStoreList().size());
	}
	
	public static Page<Store> getStore1Page(){
		List<Store> list = new ArrayList<Store>();
		list.add(getStore1());
		
		return new PageImpl<Store>(list, getPageRequest(), list.size());
	}
	
	public static MultiValueMap<String, String> urlNearParameters(Pageable pageable, StoreLocationType storeLocationType, double longitude,
			double latitude, long minDistance, long maxDistance) {
				MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
				parameters.add("page", "" + 0);
				parameters.add("size", "" + 10);
				parameters.add("longitude", "" + longitude);
				parameters.add("latitude", "" + latitude);
				parameters.add("minDistance", "" + minDistance);
				parameters.add("maxDistance", "" + maxDistance);
				
				if(storeLocationType != null)
					parameters.add("storeLocationType", storeLocationType.getDescription());
				
		return parameters;
	}

	public static Location createLocation(String type, double[] coordinates) {
		return new Location(type, coordinates);
	}

	public static Location getLocation() {
		return createLocation("Point", new double[] { 4.615551, 51.778461 });
	}
	
	public static PageRequest getPageRequest() {
		return new PageRequest(0, 10);
	}
}
