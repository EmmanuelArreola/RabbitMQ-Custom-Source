package mx.com.n3xgen.rabbitSource.stream;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

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
	private String payload = "Payload";
	@Bean
	public Supplier<String> receiverMessage(RabbitMQProperties rabbitMQProperties){
		return () -> {
			
			log.info("Starting method");
			ReceiveMessage receivemessage = new ReceiveMessageImpl();
			try {
				receivemessage.listener(payload, rabbitMQProperties);
			} catch (IOException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			}
			return "It worked?";
		};
	}
}
