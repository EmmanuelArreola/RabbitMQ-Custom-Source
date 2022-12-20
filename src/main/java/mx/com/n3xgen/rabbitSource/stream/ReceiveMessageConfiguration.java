package mx.com.n3xgen.rabbitSource.stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.Message;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import lombok.extern.log4j.Log4j2;
import mx.com.n3xgen.rabbitSource.bean.RabbitMQProperties;
import mx.com.n3xgen.rabbitSource.service.Receiver;
import reactor.core.publisher.Flux;

@Log4j2
@EnableConfigurationProperties({ RabbitMQProperties.class })
@Configuration
public class ReceiveMessageConfiguration {
	private final static String QUEUE_NAME = "UniversalTest";
	String payload;
	Connection connection;
	Channel channel;
	long deliveryTag;

	@Bean
	public Supplier<String> receiverMessage(RabbitMQProperties rabbitMQProperties) throws Exception {
		return () -> {
			try {
//
//				payload = "";
//				connection = null;
//				channel = null;
//				deliveryTag = 0;

				ConnectionFactory factory = new ConnectionFactory();
				factory.setHost("localhost");
				factory.setRequestedHeartbeat(0);
				factory.setShutdownExecutor(null);

				connection = factory.newConnection();
				channel = connection.createChannel();

				channel.queueDeclare(QUEUE_NAME, true, false, false, null);
//				log.info(" [*] Waiting for messages. To exit press CTRL+C");

				DeliverCallback deliverCallback = (consumerTag, delivery) -> {
					String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
					deliveryTag = delivery.getEnvelope().getDeliveryTag();
//	          	Get headers from properties
					AMQP.BasicProperties properties = delivery.getProperties();
					Map<String, Object> headers = properties.getHeaders();

//				Extract and print payload and header
					for (Map.Entry<String, Object> header : headers.entrySet()) {
						if (header.getKey().toString().equals("UniversalId")) {
//						log.info("ID nedeed: " + header.getValue());
						}
					}

					payload = message;
//				log.info(" [x] Received '" + message + "'");
				};
				channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
				});
//				channel.basicAck(deliveryTag, false);
//				channel.basicCancel(consumerTag);
				log.info(payload);
				if (payload != null) {
					return payload;
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				try {
					channel.close();
					connection.close();
				} catch (IOException | TimeoutException e) {
					e.printStackTrace();
				}
			}
			return new String("No message");
		};
	}

}
