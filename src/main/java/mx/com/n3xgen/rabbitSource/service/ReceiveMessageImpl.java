package mx.com.n3xgen.rabbitSource.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import mx.com.n3xgen.rabbitSource.bean.RabbitMQProperties;

public class ReceiveMessageImpl implements ReceiveMessage{
	private final static String QUEUE_NAME = "UniversalTest";
	
	static String finalData;

	@Override
	public String listener(String payload, RabbitMQProperties rabbitmqproperties) throws IOException, TimeoutException, InterruptedException {
		rabbitmqproperties = null;
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		try
		{
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			System.out.println("[*] Waiting for messages. To exit press CTRL+C");

			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
					finalData = new String(body, "UTF-8");
					System.out.println(String.format("Received  «%s»", finalData));
				}
			};
			channel.basicConsume(QUEUE_NAME, true, consumer);
			Thread.sleep(20000);
		} finally {
			channel.close();
			connection.close();
		}
		
		return finalData;
	}
}
