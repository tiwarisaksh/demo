package com.the.basic.tech.info.activemq.config;

import java.util.Optional;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;



@Configuration
public class ConnectionFactoryConfig {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionFactoryConfig.class);
	@Value("${jsa.activemq.broker.url}")
	String brokerUrl;
	
	@Value("${jsa.activemq.borker.username}")
	String userName;
	
	@Value("${jsa.activemq.borker.password}")
	String password;

	/*
	 * Initial ConnectionFactory
	 */
    @Bean
    public ConnectionFactory connectionFactory(){
    	logger.info("  connectionFactory method has started:");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        Optional<String> optional = Optional.ofNullable("brokerUrl");
    	if (optional.isPresent()) {
			connectionFactory.setBrokerURL(brokerUrl);
		} else {
			System.out.println("value is null");
		}
		Optional<String> optional1 = Optional.ofNullable("userName");
		if (optional1.isPresent()) {
			connectionFactory.setUserName(userName);
		} else {
			System.out.println("value is null");
		}
		Optional<String> optional2 = Optional.ofNullable("password");
		if (optional2.isPresent()) {
			connectionFactory.setPassword(password);
		} else {
			System.out.println("value is null");
		}
		/*
		 * connectionFactory.setBrokerURL(brokerUrl);
		 * connectionFactory.setUserName(userName);
		 * connectionFactory.setPassword(password);
		 */
        return connectionFactory;
    }
    
	@Bean // Serialize message content to json using TextMessage
	public MessageConverter jacksonJmsMessageConverter() {
	    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
	    converter.setTargetType(MessageType.TEXT);
	    converter.setTypeIdPropertyName("_type");
	    return converter;
	}
    
	@Bean
	public JmsListenerContainerFactory<?> jsaFactory(ConnectionFactory connectionFactory,
	                                                DefaultJmsListenerContainerFactoryConfigurer configurer) {
	    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	    factory.setPubSubDomain(true);
	    factory.setMessageConverter(jacksonJmsMessageConverter());
	    configurer.configure(factory, connectionFactory);
	    return factory;
	}
}
