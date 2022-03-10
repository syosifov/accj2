package net.yosifov.accounting.accj;

import net.yosifov.accounting.accj.utils.Business;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Accj2Application  implements CommandLineRunner {
	static final Logger logger =
			LoggerFactory.getLogger(Accj2Application.class);

	@Autowired
	private Business business;

	public static void main(String[] args) {
		SpringApplication.run(Accj2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		business.install("Company 1", "Address 1", "1111111111",2022);
		logger.info("Application installed");
	}
}
