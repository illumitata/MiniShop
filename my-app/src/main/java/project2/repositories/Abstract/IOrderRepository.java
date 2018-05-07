package project2.repositories.Abstract;

import project2.models.Order;
import java.util.List;

public interface IOrderRepository {
    List getOrders();
    List getOrdersByCustomer(int customerId);
    Order getOrder(int id);
    boolean orderExists(int id);
    boolean orderExists(int id, int customerId);
    void addOrder(Order order);
    void deleteOrder(int id);
    void deleteCustomerOrders(int customerId);
    boolean validateOrder(Order order);
}