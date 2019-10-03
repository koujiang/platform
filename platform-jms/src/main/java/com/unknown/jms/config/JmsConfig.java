package com.unknown.jms.config;

import org.apache.activemq.RedeliveryPolicy;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

/**
 * <br>(c) Copyright koujiang901123@sima.com
 * <br>@description	:
 * <br>@file_name	:null.java
 * <br>@system_name	:platform
 * <br>@author		:Administrator
 * <br>@create_time	:2019/4/9 13:26
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 * <br>@varsion		:v1.0.0
 */
@Configuration
public class JmsConfig {

    //@Bean //此处是结合springboot设置的redeliveryPolicy
    public RedeliveryPolicy redeliveryPolicy(){
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        /*
        //设置初始化重发延迟时间，默认1000毫秒
        redeliveryPolicy.setInitialRedeliveryDelay(1000);
        //设置最大重发次数，默认为6
        redeliveryPolicy.setMaximumRedeliveries(1000);
        //是否开启重发延迟大小倍数递增，默认false
        redeliveryPolicy.setUseExponentialBackOff(true);
        //重发延迟递增倍数，默认为5
        redeliveryPolicy.setBackOffMultiplier(2);
        //这个参数主要是为了防止高并发下，消息重发会在同一时刻发生，让消息在时间上消费的更加均衡，默认为false
        redeliveryPolicy.setUseCollisionAvoidance(true);
        //设置下次重发延迟波动百分比，默认0.15，此处写成15是因为源码中对参数*0.01
        redeliveryPolicy.setCollisionAvoidancePercent((short)15);
        //设置最大重发延迟，默认为-1，表示无限增大
        //redeliveryPolicy.setMaximumRedeliveryDelay(10000);
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);
        */

        //设置初始化重发延迟时间，默认1000毫秒
        redeliveryPolicy.setInitialRedeliveryDelay(1000);
        //设置最大重发次数，默认为6  600(600*1s) = 10m
        redeliveryPolicy.setMaximumRedeliveries(600);
        //是否开启重发延迟大小倍数递增，默认false
        redeliveryPolicy.setUseExponentialBackOff(false);
        //重发延迟递增倍数，默认为5
        //第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
        redeliveryPolicy.setBackOffMultiplier(2);
        //这个参数主要是为了防止高并发下，消息重发会在同一时刻发生，让消息在时间上消费的更加均衡，默认为false
        redeliveryPolicy.setUseCollisionAvoidance(true);
        //设置下次重发延迟波动百分比，默认0.15，此处写成15是因为源码中对参数*0.01
        redeliveryPolicy.setCollisionAvoidancePercent((short)15);
        //设置最大重发延迟，默认为-1，(表示无限增大)表示没有拖延只有UseExponentialBackOff(true)为true时生效
        //redeliveryPolicy.setMaximumRedeliveryDelay(10000);
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);

        /*
        RedeliveryPolicy {
        destination = null,
        collisionAvoidanceFactor = 0.15,

        maximumRedeliveries = 6,

        maximumRedeliveryDelay = -1,
        initialRedeliveryDelay = 1000,
        useCollisionAvoidance = false,
        useExponentialBackOff = false,
        backOffMultiplier = 5.0,
        redeliveryDelay = 1000}
        */
        return redeliveryPolicy;
    }

    //重试策略
    /*@Bean
    public RedeliveryPolicy redeliveryPolicy(){
        RedeliveryPolicy redeliveryPolicy=   new RedeliveryPolicy();
        //是否在每次尝试重新发送失败后,增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        //第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
        redeliveryPolicy.setBackOffMultiplier(2);

        //重发次数,默认为6次   这里设置为10次
        redeliveryPolicy.setMaximumRedeliveries(10);
        //重发时间间隔,默认为1秒
        redeliveryPolicy.setInitialRedeliveryDelay(1);
        //是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(false);
        //设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);
        return redeliveryPolicy;
    }*/

    //queue监听工厂
    @Bean(name = "queueFactory")
    public JmsListenerContainerFactory<?> queueFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some of Boot's default if necessary.
        factory.setSessionTransacted(true);
        return factory;
    }

    //topic监听工厂
    @Bean(name = "topicFactory")
    public JmsListenerContainerFactory<?> topicFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some of Boot's default if necessary.
        //配置成接收TOPIC模式的连接
        //PubSubDomain代表模式, true:发布/订阅模式，即topic , false:点对点模式，即queue
        factory.setPubSubDomain(true);
        //设置事务
        factory.setSessionTransacted(true);
        //开启持久化订阅
        factory.setSubscriptionDurable(true);
        //设置自动启动
        factory.setAutoStartup(true);
        //设置客户端ID
        factory.setClientId(Math.random()+"");
        return factory;
    }

    //配置链接方式(减少springboot重复创建session的问题 )
    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory factory) {
        //是否采用同步发送
        //factory.setUseAsyncSend(true);
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setSessionCacheSize(100);
        cachingConnectionFactory.setTargetConnectionFactory(factory);
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
        //客户端签收模式
        //jmsTemplate.setSessionAcknowledgeMode(4);
        //设置deliveryMode（持久化）, priority, timeToLive必须开启
        jmsTemplate.setExplicitQosEnabled(true);
        // 设置消息是否持久化
        jmsTemplate.setDeliveryPersistent(true);
        // 设置消息转换器
        //jmsTemplate.setMessageConverter(new MappingJackson2MessageConverter());
        // 设置消息是否以事务
        jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;
    }
}
