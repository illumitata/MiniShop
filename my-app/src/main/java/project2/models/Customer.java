package project2.models;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String email;

    public Customer() {

    }

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Database model
    public Customer(int id, Customer customer) {
        this.id = id;
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.email = customer.getEmail();
    }
}
