package admirwalker.com.github.restservices.repositories;

import admirwalker.com.github.restservices.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

// Spring Data JPA repositories are interfaces with methods supporting creating, reading, updating, and deleting records against a back end data store.
// By declaring this interface, we can create, update, delete and find employees
// <domainType,idType>
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
