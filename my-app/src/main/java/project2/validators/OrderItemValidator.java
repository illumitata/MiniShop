package project2.validators;

import project2.models.OrderItem;

public class OrderItemValidator {
    public static boolean validate(OrderItem oi) {
        if(oi.getItemId() < 0 || oi.getOrderId() < 0)
            return false;
        return true;
    }
}
