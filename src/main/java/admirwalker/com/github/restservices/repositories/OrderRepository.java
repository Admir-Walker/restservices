package admirwalker.com.github.restservices.repositories;

import admirwalker.com.github.restservices.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
