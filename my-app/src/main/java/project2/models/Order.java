package project2.models;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Order {
    private int id;
    private int customerId;

    public Order() {

    }

    public Order(int customerId) {
        this.customerId = customerId;
    }

    // Database model
    public Order(int id, Order order) {
        this.id = id;
        this.customerId = order.getCustomerId();
    }
}
