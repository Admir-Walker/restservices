package admirwalker.com.github.restservices.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Order not found " + id);
    }
}
