package mx.com.n3xgen.rabbitSource.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
//@NoArgsConstructor
@Data
@ConfigurationProperties(prefix = "connection.properties")
@Validated
public class RabbitMQProperties {}
