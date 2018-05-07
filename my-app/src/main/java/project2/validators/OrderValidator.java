package project2.validators;

import project2.models.Order;

public class OrderValidator {
    public static boolean validate(Order o) {
        if(o.getId() < 0 || o.getCustomerId() < 0)
            return false;
        return true;
    }
}
