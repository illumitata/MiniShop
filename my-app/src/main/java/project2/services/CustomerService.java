package project2.services;

import project2.models.Customer;
import project2.models.Order;
import project2.repositories.Abstract.ICustomerRepository;
import project2.repositories.Abstract.IOrderRepository;
import project2.repositories.FakeCustomerRepository;
import project2.repositories.FakeOrderRepository;
import project2.validators.CustomerValidator;
import project2.validators.OrderValidator;

import java.util.*;

public class CustomerService {

    private ICustomerRepository customerRepository;
    private IOrderRepository orderRepository;

    public CustomerService(ICustomerRepository customerRepository, IOrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    public boolean addCustomer(String firstName, String lastName, String email) {
        if (customerRepository.customerExists(email))
            return false;
        Customer c = new Customer(firstName, lastName, email);
        if (!customerRepository.validateCustomer(c))
            throw new IllegalArgumentException("Customer has some wrong data!");
        customerRepository.addCustomer(c);
        return true;
    }

    public boolean deleteCustomer(int id) {
        if (!customerRepository.customerExists(id))
            throw new IllegalArgumentException("Customer with ID: " + id + ", does not exist!");
        customerRepository.deleteCustomer(id);
        // add that customer orders also delete!
        orderRepository.deleteCustomerOrders(id);
        return true;
    }

    public boolean updateCustomer(int id, String firstName, String lastName, String email) {
        if (!customerRepository.customerExists(id))
            throw new IllegalArgumentException("Customer with ID: " + id + ", does not exist!");
        Customer updated = new Customer(firstName, lastName, email);
        if (!customerRepository.validateCustomer(updated))
            throw new IllegalArgumentException("Customer has some wrong data!");
        Customer cFromId = customerRepository.getCustomer(id);
        Customer cFromEmail = customerRepository.getCustomer(email);
        if (cFromEmail == null || cFromId == cFromEmail) {
            customerRepository.updateCustomer(id, updated);
            return true;
        } else
            throw new IllegalArgumentException("Some other customer has that email!");
    }

    public List<Customer> getCustomers() {
        return customerRepository.getCustomers();
    }
}
