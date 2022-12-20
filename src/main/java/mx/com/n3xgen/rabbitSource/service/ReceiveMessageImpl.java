package mx.com.n3xgen.rabbitSource.service;

import java.nio.charset.StandardCharsets;

import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

public class ReceiveMessageImpl implements ReceiveMessage {
	private final static String QUEUE_NAME = "UniversalTest";
	static String payload;

	public String listener(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();

		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(QUEUE_NAME);

		container.setMessageListener((MessageListener) message -> {
			payload = new String(message.getBody(), StandardCharsets.UTF_8);

		});
		return payload;
	}
}
