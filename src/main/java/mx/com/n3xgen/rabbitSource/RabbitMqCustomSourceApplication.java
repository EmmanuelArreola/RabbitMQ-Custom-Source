package mx.com.n3xgen.rabbitSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitMqCustomSourceApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(RabbitMqCustomSourceApplication.class, args);
	}
}
