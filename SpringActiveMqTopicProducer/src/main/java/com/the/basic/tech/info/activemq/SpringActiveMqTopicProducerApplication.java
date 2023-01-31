package com.the.basic.tech.info.activemq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.the.basic.tech.info.activemq.jms.JmsPublisher;
import com.the.basic.tech.info.activemq.models.Company;
import com.the.basic.tech.info.activemq.models.Product;

@SpringBootApplication
public class SpringActiveMqTopicProducerApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(SpringActiveMqTopicProducerApplication.class);
	@Autowired
	JmsPublisher publisher;

	public static void main(String[] args) {
		SpringApplication.run(SpringActiveMqTopicProducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("run method has started");
		/*
		 * Apple company & products
		 */
		// initial company and products
		Product iphone7 = new Product("Iphone 7");
		Product iPadPro = new Product("IPadPro");
		List<Product> appleProducts = new ArrayList<Product>(Arrays.asList(iphone7, iPadPro));

		Company apple = new Company("Apple", appleProducts);
		Optional<String> message1 = Optional.ofNullable("apple");
		if (message1.isPresent()) {
			// send message to ActiveMQ
			publisher.send(apple);
		} else {
			System.out.println("messgae is empty:");
		}
		/*
		 * Samsung company and products
		 */
		Product galaxyS8 = new Product("Galaxy S8");
		Product gearS3 = new Product("Gear S3");

		List<Product> samsungProducts = new ArrayList<Product>(Arrays.asList(galaxyS8, gearS3));

		Company samsung = new Company("Samsung", samsungProducts);
		Optional<String> message2 = Optional.ofNullable("samsung");
		if (message2.isPresent()) {
			// send message to ActiveMQ
			publisher.send(samsung);
		} else {
			System.out.println("messgae is empty:");
		}
	}
}
