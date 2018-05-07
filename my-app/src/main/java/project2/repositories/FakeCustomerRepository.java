package project2.repositories;

import project2.models.Customer;
import project2.repositories.Abstract.ICustomerRepository;
import project2.validators.CustomerValidator;
import java.util.ArrayList;
import java.util.List;

public class FakeCustomerRepository implements ICustomerRepository {

    private int idCounter = 0;
    private List<Customer> customers = new ArrayList<Customer>();

    public List getCustomers() {
        return customers;
    }

    public Customer getCustomer(int id) {
        return customers.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    public Customer getCustomer(String firstName, String lastName, String email) {
        return customers.stream().filter(x -> x.getFirstName().equals(firstName) &&
                                         x.getLastName().equals(lastName) &&
                                         x.getEmail().equals(email)).findFirst().orElse(null);
    }

    public Customer getCustomer(String email) {
        return customers.stream().filter(x -> x.getEmail().equals(email)).findFirst().orElse(null);
    }

    public boolean customerExists(int id) {
        return customers.stream().anyMatch(x -> x.getId() == id);
    }

    public boolean customerExists(String firstName, String lastName) {
        return customers.stream().anyMatch(x -> x.getFirstName().equals(firstName) &&
                                           x.getLastName().equals(lastName));
    }

    public boolean customerExists(String firstName, String lastName, String email) {
        return customers.stream().anyMatch(x -> x.getFirstName().equals(firstName) &&
                                           x.getLastName().equals(lastName) &&
                                           x.getEmail().equals(email));
    }

    public boolean customerExists(String email) {
        return customers.stream().anyMatch(x -> x.getEmail().equals(email));
    }

    public void addCustomer(Customer customer) {
        customers.add(new Customer(idCounter, customer));
        idCounter++;
    }

    public void deleteCustomer(int id) {
        customers.removeIf(x -> x.getId() == id);
    }

    public void updateCustomer(int id, Customer customer) {
        Customer c = customers.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
        if(c != null) {
            customers.set(customers.indexOf(c), new Customer(id, customer));
        }
    }

    public boolean validateCustomer(Customer customer) {
        return CustomerValidator.validate(customer);
    }
}
