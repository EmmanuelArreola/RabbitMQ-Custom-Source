package mx.com.n3xgen.rabbitSource.stream;

import java.util.function.Supplier;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import lombok.extern.log4j.Log4j2;
import mx.com.n3xgen.rabbitSource.bean.RabbitMQProperties;
import mx.com.n3xgen.rabbitSource.service.ReceiveMessage;
import mx.com.n3xgen.rabbitSource.service.ReceiveMessageImpl;

@Log4j2
@EnableConfigurationProperties({ RabbitMQProperties.class })
@Configuration
public class ReceiveMessageConfiguration {
//	private final static String QUEUE_NAME = "UniversalTest";
	String payload;
//	Connection connection;
//	Channel channel;
	long deliveryTag;

	@Bean
	public Supplier<String> receiverMessage(RabbitMQProperties rabbitMQProperties) throws Exception {
		return () -> {
			log.info("Starting method");
//			simpleRabbitListenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.AUTO);
			CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
			ReceiveMessage receiveMessage = new ReceiveMessageImpl();
			
			
			cachingConnectionFactory.setHost("localhost");

			try {
				payload = receiveMessage.listener(cachingConnectionFactory);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return payload;
		};
	}

}
