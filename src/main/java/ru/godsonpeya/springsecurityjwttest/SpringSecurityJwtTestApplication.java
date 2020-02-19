package ru.godsonpeya.springsecurityjwttest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.godsonpeya.springsecurityjwttest.repositories.UserReopository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserReopository.class)
public class SpringSecurityJwtTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtTestApplication.class, args);
	}

}
