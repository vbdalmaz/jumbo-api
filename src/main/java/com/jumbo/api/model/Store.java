package com.jumbo.api.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.jumbo.api.model.enumeration.StoreLocationType;

@Document(collection = "stores")
public class Store implements Serializable {

	private static final long serialVersionUID = 2019020801L;
	
	public Store() {
		super();
	}
	
	public Store(String uuid, String city, String postalCode, String street, String street2, String street3,
			String addressName, Location location, String complexNumber, Boolean showWarningMessage, String todayOpen,
			StoreLocationType locationType, Boolean collectionPoint, String sapStoreID, String todayClose) {
		super();
		this.uuid = uuid;
		this.city = city;
		this.postalCode = postalCode;
		this.street = street;
		this.street2 = street2;
		this.street3 = street3;
		this.addressName = addressName;
		this.location = location;
		this.complexNumber = complexNumber;
		this.showWarningMessage = showWarningMessage;
		this.todayOpen = todayOpen;
		this.locationType = locationType;
		this.collectionPoint = collectionPoint;
		this.sapStoreID = sapStoreID;
		this.todayClose = todayClose;
	}

	@Id
	private String uuid;
	private String city;
	private String postalCode;
	private String street;
	private String street2;
	private String street3;
	private String addressName;
	private Location location;
	private String complexNumber;
	private Boolean showWarningMessage;
	private String todayOpen;
	private StoreLocationType locationType;
	private Boolean collectionPoint;
	private String sapStoreID;
	private String todayClose;

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getStreet3() {
		return street3;
	}
	public void setStreet3(String street3) {
		this.street3 = street3;
	}
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getComplexNumber() {
		return complexNumber;
	}
	public void setComplexNumber(String complexNumber) {
		this.complexNumber = complexNumber;
	}
	public Boolean getShowWarningMessage() {
		return showWarningMessage;
	}
	public void setShowWarningMessage(Boolean showWarningMessage) {
		this.showWarningMessage = showWarningMessage;
	}
	public String getTodayOpen() {
		return todayOpen;
	}
	public void setTodayOpen(String todayOpen) {
		this.todayOpen = todayOpen;
	}
	public StoreLocationType getLocationType() {
		return locationType;
	}
	public void setLocationType(StoreLocationType locationType) {
		this.locationType = locationType;
	}
	public Boolean getCollectionPoint() {
		return collectionPoint;
	}
	public void setCollectionPoint(Boolean collectionPoint) {
		this.collectionPoint = collectionPoint;
	}
	public String getSapStoreID() {
		return sapStoreID;
	}
	public void setSapStoreID(String sapStoreID) {
		this.sapStoreID = sapStoreID;
	}
	public String getTodayClose() {
		return todayClose;
	}
	public void setTodayClose(String todayClose) {
		this.todayClose = todayClose;
	}
}