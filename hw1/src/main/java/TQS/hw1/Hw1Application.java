package TQS.hw1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class Hw1Application {

	public static void main(String[] args) {
		SpringApplication.run(Hw1Application.class, args);
	}

}
