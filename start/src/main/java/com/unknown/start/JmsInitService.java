package com.unknown.start;

import com.unknown.jms.JmsProduct;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
@Slf4j
public class JmsInitService extends ApplicationObjectSupport implements ApplicationRunner {


    @Autowired
    private JmsProduct jmsProduct;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("**************************initRankCache****************************");
        jmsProduct.sendMessage(new ActiveMQQueue("jms_service"), "测试");
        log.info("**************************initRankCache****************************");
    }
}
