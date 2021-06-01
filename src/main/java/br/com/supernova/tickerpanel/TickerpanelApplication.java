package br.com.supernova.tickerpanel;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TickerpanelApplication {

	public static void main(String[] args) {
		SpringApplication.run(TickerpanelApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(){
		return new OpenAPI().info(new Info()
				.title("REST API - Tickers Panel")
				.description("Bootcamp Santander project for the creation of a Panel of Tickers to monitor the actions of companies on the Stock Exchange")
				.contact(infoContact())
				.version("1.0")
				.termsOfService("http://swagger.io/terms")
				.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}

	@Bean
	public Contact infoContact(){
		return new Contact()
				.name("William Derek Dias")
				.email("williamdkdevops@gmail.com")
				.url("https://github.com/willdkdevj");
	}
}
