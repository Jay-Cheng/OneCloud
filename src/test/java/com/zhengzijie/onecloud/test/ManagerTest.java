package com.zhengzijie.onecloud.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.zhengzijie.onecloud.config.RootConfig;
import com.zhengzijie.onecloud.config.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebConfig.class,RootConfig.class } )
@SuppressWarnings("unused")
public class ManagerTest {
    
}
