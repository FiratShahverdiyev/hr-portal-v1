package az.hrportal.hrportalapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HrportalApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(HrportalApiApplication.class, args);
    }
}
