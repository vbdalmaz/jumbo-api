package com.jumbo.api.utils;

public abstract class JumboAPIStringConfig {

	/* PARAMS */
	public static final String PARAM_STORE_ID = "/{storeId}";

	/* STORE */
	public static final String STORE_BASE_URL = "/store";

	public static final String STORE_BY_ID_URL = STORE_BASE_URL + PARAM_STORE_ID;
	public static final String STORE_NEAR_URL = STORE_BASE_URL + "/near";
	public static final String STORE_GET_ALL_URL = STORE_BASE_URL + "/all";

	/* MESSAGES */
	public static final String EXCEPTION_MESSAGE = "We had problem try again in a few minutes";
	public static final String STORE_ERROR_EXCEPTION_MESSAGE = "Your Store is not valid check your input";

	/* Page */
	public static final int PAGE_MAX_SIZE = 15;
}
