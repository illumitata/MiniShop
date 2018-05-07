package project2.services;

import project2.models.Order;
import project2.models.Customer;
import project2.models.OrderItem;
import project2.repositories.Abstract.IOrderRepository;
import project2.repositories.Abstract.ICustomerRepository;
import project2.repositories.Abstract.IOrderItemRepository;
import project2.repositories.FakeOrderRepository;
import project2.repositories.FakeCustomerRepository;
import project2.repositories.FakeOrderItemRepository;
import project2.validators.OrderValidator;
import project2.validators.CustomerValidator;
import project2.validators.OrderItemValidator;

import java.util.*;

public class OrderService {

    private IOrderRepository orderRepository;
    private ICustomerRepository customerRepository;
    private IOrderItemRepository orderItemRepository;

    public OrderService(IOrderRepository orderRepository, ICustomerRepository customerRepository,
                        IOrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public boolean addOrder(int customerId) {
        // can't add order if there is no such user
        if (!customerRepository.customerExists(customerId))
            return false;
        Order o = new Order(customerId);
        if (!orderRepository.validateOrder(o))
            throw new IllegalArgumentException("Order has some wrong data!");
        orderRepository.addOrder(o);
        return true;
    }

    public boolean deleteOrder(int id) {
        if (!orderRepository.orderExists(id))
            throw new IllegalArgumentException("Order with ID: " + id + ", does not exist!");
        orderRepository.deleteOrder(id);
        // Deletes also all orders with that item? I think it shouldn't
        orderItemRepository.deleteOrderItems(id);
        return true;
    }

    public List<Order> getOrders() {
        return orderRepository.getOrders();
    }

    public List<Order> getOrdersByCustomer(int customerId) {
        return orderRepository.getOrdersByCustomer(customerId);
    }
}
