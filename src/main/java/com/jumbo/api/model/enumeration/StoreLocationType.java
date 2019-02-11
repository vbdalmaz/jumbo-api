package com.jumbo.api.model.enumeration;

public enum StoreLocationType {
    //@formatter:off
	PuP("PuP"),
	SupermarktPuP("SupermarktPuP"),
	Supermarkt("Supermarkt");
    //@formatter:on
	
    private String description;

    StoreLocationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static StoreLocationType parse(String description) {
        if (description == null || description.isEmpty()) {
            return null;
        }

        for (StoreLocationType storeLocationType : values()) {
            if (storeLocationType.description.equals(description)) {
                return storeLocationType;
            }
        }
        return null;
    }
}