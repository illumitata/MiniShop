package project2.repositories;

import project2.models.Order;;
import project2.repositories.Abstract.IOrderRepository;
import project2.validators.OrderValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FakeOrderRepository implements IOrderRepository {

    private int idCounter = 0;
    private List<Order> orders = new ArrayList<Order>();

    public List getOrders() {
        return orders;
    }

    public List getOrdersByCustomer(int customerId) {
        return orders.stream().filter(x -> x.getCustomerId() == customerId).collect(Collectors.toList());
    }

    public Order getOrder(int id) {
        return orders.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    public boolean orderExists(int id) {
        return orders.stream().anyMatch(x -> x.getId() == id);
    }

    public boolean orderExists(int id, int customerId) {
        return orders.stream().anyMatch(x -> x.getId() == id &&
                                        x.getCustomerId() == customerId);
    }

    public void addOrder(Order order) {
        orders.add(new Order(idCounter, order));
        idCounter++;
    }

    public void deleteOrder(int id) {
        orders.removeIf(x -> x.getId() == id);
        // to add: delete also all OrderItems contains id!
    }

    public void deleteCustomerOrders(int customerId) {
        orders.removeIf(x -> x.getCustomerId() == customerId);
        // to add: delete also all OrderItems contains id!
    }

    public boolean validateOrder(Order order) {
        return OrderValidator.validate(order);
    }
}
