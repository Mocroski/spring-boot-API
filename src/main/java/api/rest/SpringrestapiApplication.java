package api.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EntityScan(basePackages = {"api.rest.model"}) //le todas as classe desse package e cria as tabelas "entity" automaticament
@ComponentScan(basePackages = {"api.*"})
@EnableJpaRepositories(basePackages = {"api.rest.repository"})
@EnableTransactionManagement
@EnableWebMvc //em um projeto spring é possivel trabalhar com rest e mvc ao memso tempo
@RestController //saber que é um projeto rest e os controllers irao retornar json
public class SpringrestapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringrestapiApplication.class, args);
	}

}
