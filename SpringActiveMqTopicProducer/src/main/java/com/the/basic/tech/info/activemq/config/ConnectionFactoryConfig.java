package com.the.basic.tech.info.activemq.config;

import java.util.Optional;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.the.basic.tech.info.activemq.jms.JmsPublisher;

@Configuration
@EnableJms
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
	public ConnectionFactory connectionFactory() {
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

		// connectionFactory.setBrokerURL(brokerUrl);
		// connectionFactory.setUserName(userName);
		// connectionFactory.setPassword(password);
		return connectionFactory;
	}

	@Bean // Serialize message content to json using TextMessage
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	/*
	 * Used for sending Messages.
	 */
	@Bean
	public JmsTemplate jmsTemplate() {
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connectionFactory());
		template.setMessageConverter(jacksonJmsMessageConverter());
		template.setPubSubDomain(true);
		return template;
	}
}
