package mx.com.n3xgen.rabbitSource.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import mx.com.n3xgen.rabbitSource.bean.RabbitMQProperties;

public interface ReceiveMessage {
	public String listener(String payload, RabbitMQProperties rabbitmqproperties) throws IOException, TimeoutException, InterruptedException;
}
