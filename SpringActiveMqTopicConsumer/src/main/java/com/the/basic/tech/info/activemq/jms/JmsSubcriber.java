package com.the.basic.tech.info.activemq.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.the.basic.tech.info.activemq.models.Company;


@Component
public class JmsSubcriber {
	private static final Logger logger = LoggerFactory.getLogger(JmsSubcriber.class);
	
	@JmsListener(destination = "${jsa.activemq.topic}")
	public void receive(Company msg){
		logger.info("receive method has started");
		logger.info("Recieved Message:  {}", msg.toString());
	}
}
