package com.jumbo.api.model;

import java.io.Serializable;

public class Location implements Serializable {

	private static final long serialVersionUID = 2019020801L;
	
	public Location() {
		super();
	}
	
	public Location(String type, double[] coordinates) {
		super();
		this.type = type;
		this.coordinates = coordinates;
	}

	private String type;
	private double[] coordinates;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double[] getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(double[] coordinates) {
		this.coordinates = coordinates;
	}
}
