package admirwalker.com.github.restservices.assemblers;

import admirwalker.com.github.restservices.controllers.EmployeeController;
import admirwalker.com.github.restservices.models.Employee;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

// Component annotation - assembler will be automatically created when app starts
@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {
    // RepresentationModelAssembler<T,G>
    // EntityModel generic container - includes not only the data but a collection of links
    @Override
    public EntityModel<Employee> toModel(Employee employee) {
        return EntityModel.of(employee,
                // build me a link to "one" method
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
                // build me a link to "all" method
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).all()).withRel("employees"));
    }
}
