package project2.validators;

import project2.models.Customer;

public class CustomerValidator {
    public static boolean validate(Customer c) {
        if(c.getFirstName() == null || c.getFirstName().isEmpty() ||
                c.getLastName() == null || c.getLastName().isEmpty() ||
                c.getEmail() == null || c.getEmail().isEmpty())
            return false;
        return true;
    }
}
