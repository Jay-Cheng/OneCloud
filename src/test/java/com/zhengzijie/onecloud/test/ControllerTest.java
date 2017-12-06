package com.zhengzijie.onecloud.test;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.zhengzijie.onecloud.config.OneCloudWebInitializer;
import com.zhengzijie.onecloud.config.RootConfig;
import com.zhengzijie.onecloud.config.WebConfig;
import com.zhengzijie.onecloud.web.LoginController;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebConfig.class,RootConfig.class } )
@SuppressWarnings("unused")
public class ControllerTest {
    
    @Autowired
    private WebApplicationContext webAppContext;
    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }
    
    
    @Autowired
    private LoginController loginController;
    /**
     * 测试（200）与Run on server（404）结果不一致，
     * 如果没有在WebConfig配置类里加上ComponentScan则Controller不会被扫描到，
     * 但测试时Controller却被扫描到了
     */
//    @Test
//    public void testLoginController() throws Exception {
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//        mockMvc.perform(MockMvcRequestBuilders.post("/authentication")).andExpect(MockMvcResultMatchers.view().name("login"));;
//    }
}
