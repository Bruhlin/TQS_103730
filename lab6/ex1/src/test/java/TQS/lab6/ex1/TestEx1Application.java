package TQS.lab6.ex1;

import org.springframework.boot.SpringApplication;

public class TestEx1Application {

	public static void main(String[] args) {
		SpringApplication.from(Ex1Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
