package mx.com.n3xgen.rabbitSource.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;

public interface ReceiveMessage {
	public String listener(ConnectionFactory connectionFactory)
			throws IOException, TimeoutException, InterruptedException;
}
