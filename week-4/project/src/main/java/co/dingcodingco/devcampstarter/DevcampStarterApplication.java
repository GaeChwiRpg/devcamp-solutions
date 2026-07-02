package co.dingcodingco.devcampstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DevcampStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevcampStarterApplication.class, args);
    }
}
