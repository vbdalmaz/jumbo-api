package com.jumbo.api.controller;

import static com.jumbo.api.utils.JumboAPIStringConfig.STORE_BASE_URL;
import static com.jumbo.api.utils.JumboAPIStringConfig.STORE_GET_ALL_URL;
import static com.jumbo.api.utils.JumboAPIStringConfig.STORE_NEAR_URL;
import static com.jumbo.api.utils.ObjectCreator.getPageRequest;
import static com.jumbo.api.utils.ObjectCreator.getStore1;
import static com.jumbo.api.utils.ObjectCreator.getStore1Page;
import static com.jumbo.api.utils.ObjectCreator.getStoresPage;
import static com.jumbo.api.utils.ObjectCreator.urlNearParameters;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumbo.api.exception.StoreDuplicatedException;
import com.jumbo.api.model.Store;
import com.jumbo.api.repository.StoreRepository;
import com.jumbo.api.service.StoreService;


@RunWith(SpringRunner.class)
@WebMvcTest(StoreController.class)
public class StoreControllerTest {
	
	@MockBean
	@Qualifier("storeService")
	private StoreService service;
	
    @MockBean
    private StoreRepository storeRepository;
	
	@Autowired
	private MockMvc mvc;
	
	private ObjectMapper objectMapper;
	private Store testStore;
	
	@Before
	public void setup() {
		objectMapper = new ObjectMapper();
		testStore = getStore1();
	}
	
	@Test
	public void shouldReturnAJsonWithStatusOk() throws Exception {
		when(service.getStore(testStore.getUuid())).thenReturn(testStore);

		mvc.perform(get(STORE_BASE_URL + "/" + testStore.getUuid()  ).contentType(APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_UTF8_VALUE)).andExpect(content().json(objectMapper.writeValueAsString(testStore)));
	}
	
	@Test
	public void shouldReturnStatus204DueToAStoreThatDoesNotExist() throws Exception {
		when(service.getStore(testStore.getUuid())).thenReturn(null);

		mvc.perform(get(STORE_BASE_URL + "/" + testStore.getUuid()).contentType(APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isNoContent());
	}

	@Test
	public void shouldReturnStatus200DueToAEmptyList() throws Exception {
		when(service.getAllStores(getPageRequest())).thenReturn(new PageImpl<>(new ArrayList<>(), getPageRequest(), 0));

		mvc.perform(get(STORE_GET_ALL_URL).contentType(APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk());
	}
	
	@Test
	public void shouldCreateAStore() throws Exception {
		when(service.createStore(testStore)).thenReturn(testStore);

		mvc.perform(post(STORE_BASE_URL)
				.content(objectMapper.writeValueAsString(testStore))
				.contentType(APPLICATION_JSON_UTF8_VALUE)).andExpect(status().is2xxSuccessful());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void shouldNotCreateAStoreBecauseAlreadyExists() throws Exception {
		when(service.createStore(any())).thenThrow(StoreDuplicatedException.class);

		mvc.perform(post(STORE_BASE_URL)
				.content(objectMapper.writeValueAsString(testStore))
				.contentType(APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldDeleteStore() throws Exception {
		mvc.perform(delete(STORE_BASE_URL + "/" + testStore.getUuid() )
				.content(objectMapper.writeValueAsString(testStore))
				.contentType(APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk()); 
	}
	
	@Test
	public void shouldUpdateStore() throws Exception {
		when(service.updateStore(testStore)).thenReturn(testStore);

		mvc.perform(put(STORE_BASE_URL)
				.content(objectMapper.writeValueAsString(testStore))
				.contentType(APPLICATION_JSON_UTF8_VALUE)).andExpect(status().is2xxSuccessful());
	}
	
	@Test
	public void shouldBringBothStores() throws Exception {
		when(service.getStoresNearMe(getPageRequest(), testStore.getLocation().getCoordinates()[0], testStore.getLocation().getCoordinates()[1], 0, 500)).thenReturn(getStoresPage());

		mvc.perform(get(STORE_NEAR_URL).params(urlNearParameters(getPageRequest(), null, testStore.getLocation().getCoordinates()[0], testStore.getLocation().getCoordinates()[1], 0, 500))
				.content(objectMapper.writeValueAsString(testStore))
				.contentType(APPLICATION_JSON_UTF8_VALUE));
		
		verify(service, times(1)).getStoresNearMe(getPageRequest(), testStore.getLocation().getCoordinates()[0], testStore.getLocation().getCoordinates()[1], 0, 500);
	}
	
	@Test
	public void shouldBringJustOneStore() throws Exception {
		when(service.getStoresByTypeNearMe(getPageRequest(), testStore.getLocationType(), testStore.getLocation().getCoordinates()[0], testStore.getLocation().getCoordinates()[1], 0, 500)).thenReturn(getStore1Page());

		mvc.perform(get(STORE_NEAR_URL).params(urlNearParameters(getPageRequest(), testStore.getLocationType(), testStore.getLocation().getCoordinates()[0], testStore.getLocation().getCoordinates()[1], 0, 500))
				.content(objectMapper.writeValueAsString(testStore))
				.contentType(APPLICATION_JSON_UTF8_VALUE));
		
		verify(service, times(1)).getStoresByTypeNearMe(getPageRequest(), testStore.getLocationType(), testStore.getLocation().getCoordinates()[0], testStore.getLocation().getCoordinates()[1], 0, 500);
				
	}
	

}