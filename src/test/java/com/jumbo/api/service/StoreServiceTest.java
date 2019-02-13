package com.jumbo.api.service;

import static com.jumbo.api.utils.ObjectCreator.getPageRequest;
import static com.jumbo.api.utils.ObjectCreator.getStore1;
import static com.jumbo.api.utils.ObjectCreator.getStore2;
import static com.jumbo.api.utils.ObjectCreator.getStoresPage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.jumbo.api.exception.StoreDuplicatedException;
import com.jumbo.api.exception.StoreInvalidException;
import com.jumbo.api.repository.StoreRepository;
import com.jumbo.api.service.impl.StoreServiceImpl;


@RunWith(SpringRunner.class)
public class StoreServiceTest {

    @TestConfiguration
    static class StoreServiceImplTestContextConfiguration {
  
    		@Bean
    		@Qualifier("storeServiceImpl")
        public StoreService storeService() {
            return new StoreServiceImpl();
        }
    }
	
    @MockBean
    private StoreRepository storeRepository;
    
    @Autowired
    private StoreService storeService;
    
	@Test
	public void shouldReturnAList() throws Exception {
		when(storeRepository.findAll(getPageRequest())).thenReturn(getStoresPage());
		
		assertThat(storeService.getAllStores(getPageRequest())).isEqualTo(getStoresPage());
	}
	
	@Test
	public void shouldReturnAStore() throws Exception {

		when(storeRepository.findByUuid(getStore1().getUuid())).thenReturn(getStore1());
		
		assertThat(storeService.getStore(getStore1().getUuid())).isEqualTo(getStore1());
	}
	
	@Test
	public void shouldReturnAPageStoreWithoutType() throws Exception {

		storeService.getStoresNearMe(getPageRequest(), getStore1().getLocation().getCoordinates()[0], getStore1().getLocation().getCoordinates()[1], 0, 5000);
		
		verify(storeRepository, times(1)).findStoresNear(getPageRequest(), getStore1().getLocation().getCoordinates()[0], getStore1().getLocation().getCoordinates()[1], 0, 5000);
	}
	
	@Test
	public void shouldReturnAPageStoreType() throws Exception {

		storeService.getStoresByTypeNearMe(getPageRequest(),getStore1().getLocationType() ,getStore1().getLocation().getCoordinates()[0], getStore1().getLocation().getCoordinates()[1], 0L, 5000L);
		
		verify(storeRepository, times(1)).findStoresNearAndByType(getPageRequest(), getStore1().getLocationType(), getStore1().getLocation().getCoordinates()[0], getStore1().getLocation().getCoordinates()[1], 0, 5000);
	}
	
	
	@Test
	public void shouldCreateStore() throws Exception {
		when(storeRepository.findByUuid(getStore2().getUuid())).thenReturn(null);
		
		storeService.createStore(getStore2());
		
		verify(storeRepository, times(1)).save(getStore2());
	}
	
	@Test(expected = StoreDuplicatedException.class)
	public void shouldNotCreateStore() throws Exception {
		when(storeRepository.findByUuid(getStore2().getUuid())).thenReturn(getStore2());
		
		storeService.createStore(getStore2());
		
		verify(storeRepository, times(0)).save(getStore2());
	}
	
	@Test
	public void shouldUpdateStore() throws Exception {
		when(storeService.getStore(getStore2().getUuid())).thenReturn(getStore2());
		
		storeService.updateStore(getStore2());
		
		verify(storeRepository, times(1)).save(getStore2());
	}
	
	@Test
	public void shouldDeleteStore() throws Exception {
		when(storeRepository.findByUuid(getStore1().getUuid())).thenReturn(getStore1());
		
		storeService.deleteStore(getStore1().getUuid());
		
		verify(storeRepository, times(1)).delete(getStore1());
	}
	
	@Test(expected = StoreInvalidException.class)
	public void shouldNotUpdateDueASkuThatDoesNotExist() throws Exception {

		when(storeService.getStore(anyString())).thenReturn(null);
		storeService.updateStore(getStore1());
	}
	
	@Test(expected = StoreInvalidException.class)
	public void shouldNotDeleteDueASkuThatDoesNotExist() throws Exception {
		
		when(storeService.getStore(anyString())).thenReturn(null);
		storeService.deleteStore(getStore1().getUuid());
	}
}