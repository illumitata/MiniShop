package project2.models;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OrderItem {
    private int itemId;
    private int orderId;

    public OrderItem() {

    }

    public OrderItem(int itemId, int orderId) {
        this.itemId = itemId;
        this.orderId = orderId;
    }

    // Database model
    public OrderItem(OrderItem orderitem) {
        this.itemId = orderitem.getItemId();
        this.orderId = orderitem.getOrderId();
    }
}
