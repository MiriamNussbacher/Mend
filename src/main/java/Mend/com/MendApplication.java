package Mend.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@ComponentScan(basePackages = {"Mend.api, Mend.service, Mend.repository, Mend.converter, Mend.config, Mend.interceptor"})
@EnableJpaRepositories(basePackages = "Mend.repository")
@EnableTransactionManagement
@EntityScan(basePackages = "Mend.domain")
@SpringBootApplication
@EnableAsync
public class MendApplication {

	private static final Logger logger = LoggerFactory.getLogger(MendApplication.class);

	public static void main(String[] args) {
		logger.info("app is up!");

		SpringApplication.run(MendApplication.class, args);
	}

}
