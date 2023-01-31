package com.the.basic.tech.info.activemq.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import com.the.basic.tech.info.activemq.models.Company;

@Component
public class JmsPublisher {
	private static final Logger logger = LoggerFactory.getLogger(JmsPublisher.class);
	@Autowired
	JmsTemplate jmsTemplate;
	@Value("${jsa.activemq.topic}")
	String topic;

	public void send(Company apple) throws java.lang.Exception {
		logger.info(" send  method has started:");
		try {
			if (topic != null)
				jmsTemplate.convertAndSend(topic, apple);
			logger.info("Message : {} published to topic: {} successfully.", apple.toString(), topic);

		} catch (NullPointerException e) {
			System.out.println("null popinter exception got caught");
		}
	}

}
