package admirwalker.com.github.restservices;

import admirwalker.com.github.restservices.models.Employee;
import admirwalker.com.github.restservices.models.Order;
import admirwalker.com.github.restservices.repositories.EmployeeRepository;
import admirwalker.com.github.restservices.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    // Spring Boot will run ALL CommandLineRunner beans once the application context is loaded
    // This runner will request a copy of the EmployeeRepository you just created.
    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository) {

        return args -> {
            employeeRepository.save(new Employee("Bossy","Jenkins","Boss"));
            employeeRepository.save(new Employee("Working","Ruler","Worker"));

            employeeRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));


            orderRepository.save(new Order("Dell laptop", Status.COMPLETED));
            orderRepository.save(new Order("Huawei P20", Status.IN_PROGRESS));

            orderRepository.findAll().forEach(order -> {
                log.info("Preloaded " + order);
            });

        };
    }
}