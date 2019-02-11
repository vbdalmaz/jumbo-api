package com.jumbo.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jumbo.api.controller.StoreController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JumboAPIJavaApplicationTest {
	
    @Autowired
    private StoreController controller;
    
    
    @Test
    public void contextLoads() throws Exception {

    }
    
    @Test
    public void contextLoadsController() throws Exception {
        assertThat(controller).isNotNull();
    }    
}