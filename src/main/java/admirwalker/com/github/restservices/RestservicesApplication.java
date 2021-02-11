package admirwalker.com.github.restservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
@SpringBootApplication is a meta-annotation that pulls in component scanning, autoconfiguration, and property support.
In essence, it will fire up a servlet container and serve up our service.
*/
@SpringBootApplication
public class RestservicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestservicesApplication.class, args);
    }

}
