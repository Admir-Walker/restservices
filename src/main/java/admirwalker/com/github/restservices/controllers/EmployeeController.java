package admirwalker.com.github.restservices.controllers;

import admirwalker.com.github.restservices.assemblers.EmployeeModelAssembler;
import admirwalker.com.github.restservices.exceptions.EmployeeNotFoundException;
import admirwalker.com.github.restservices.repositories.EmployeeRepository;
import admirwalker.com.github.restservices.models.Employee;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// @RestController indicates that the data returned by each method will be written straight into the response body instead of rendering a template.
@RestController
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final EmployeeModelAssembler assembler;

    // EmployeeRepository and EmployeeModelAssembler are injected by constructor into the controller
    public EmployeeController(EmployeeRepository employeeRepository, EmployeeModelAssembler assembler) {
        this.employeeRepository = employeeRepository;
        this.assembler = assembler;
    }


    // HTTP GET
    /*
    @GetMapping("/employees")
    List<Employee> all(){
        return employeeRepository.findAll();
    }
     */

    // CollectionModel<> is another Spring HATEOAS container;
    // it’s aimed at encapsulating collections of resources—instead of a single resource entity
    // like EntityModel<> from earlier. CollectionModel<>, too, lets you include links.
    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>> all(){
        // CollectionModel<T> Spring HATEOAS container, aimed at encapsulating collection of resources
        // Not collection of employees, but collection of employee resources
        List<EntityModel<Employee>> employees = employeeRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(employees, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).all()).withSelfRel());
    }

    // HTTP POST
    @PostMapping("employees")
    public ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee){
        EntityModel<Employee> entityModel = assembler.toModel(employeeRepository.save(newEmployee));
        // ResponseEntity HTTP 201 Created status message
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    // This is not rest, this is RPC (Remote Procedure Call)
    /*
    // HTTP GET
    @GetMapping("/employees/{id}")
    Employee one(@PathVariable Long id) throws EmployeeNotFoundException {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }
    */

    // HTTP GET
    // The return type of the method has changed from Employee to EntityModel<Employee>.
    @GetMapping("/employees/{id}")
    public EntityModel<Employee> one(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return assembler.toModel(employee);
    }
    /*
    EntityModel<T> is a generic container from Spring HATEOAS that includes not only the data but a collection of links.
    linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel() asks that Spring HATEOAS build a link to the EmployeeController 's one() method,
    and flag it as a self link.
    linkTo(methodOn(EmployeeController.class).all()).withRel("employees") asks Spring HATEOAS to build a link to the aggregate root, all(), and call it "employees".
    */
    // HTTP PUT
    @PutMapping("/employees/{id}")
    public ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id){
        Employee updatedEmployee = employeeRepository.findById(id).map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setRole(newEmployee.getRole());
            return employeeRepository.save(employee);
        }).orElseGet(() -> {
            newEmployee.setId(id);
            return employeeRepository.save(newEmployee);
        });
        EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
    // HTTP DELETE
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        employeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
