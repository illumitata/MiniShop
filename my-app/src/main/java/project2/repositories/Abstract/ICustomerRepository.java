package project2.repositories.Abstract;

import project2.models.Customer;
import java.util.List;

public interface ICustomerRepository {
    List getCustomers();
    Customer getCustomer(int id);
    // 'cause firstName and lastName can occur many times being the same
    // email and id are main methods to recognize a customer
    Customer getCustomer(String firstName, String lastName, String email);
    Customer getCustomer(String email);
    boolean customerExists(int id);
    // not sure about first and last option only, think about it later
    boolean customerExists(String firstName, String lastName);
    boolean customerExists(String firstName, String lastName, String email);
    boolean customerExists(String email);
    void addCustomer(Customer customer);
    void deleteCustomer(int id);
    void updateCustomer(int id, Customer customer);
    boolean validateCustomer(Customer customer);
}