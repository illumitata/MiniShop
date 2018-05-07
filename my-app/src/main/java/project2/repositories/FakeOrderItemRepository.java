package project2.repositories;

import project2.models.OrderItem;
import project2.repositories.Abstract.IOrderItemRepository;
import project2.validators.OrderItemValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FakeOrderItemRepository implements IOrderItemRepository {

    private List<OrderItem> orderItems = new ArrayList<OrderItem>();

    public List getOrderItems() {
        return orderItems;
    }

    public List getItemsByOrder(int orderId) {
        return orderItems.stream().filter(x -> x.getOrderId() == orderId).collect(Collectors.toList());
    }

    public OrderItem getOrderItem(int itemId, int orderId) {
        return orderItems.stream().filter(x -> x.getItemId() == itemId &&
                                          x.getOrderId() == orderId).findFirst().orElse(null);
    }

    public boolean orderItemExists(int itemId, int orderId) {
        return orderItems.stream().anyMatch(x -> x.getItemId() == itemId &&
                                            x.getOrderId() == orderId);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(new OrderItem(orderItem.getItemId(), orderItem.getOrderId()));
    }

    public void deleteOrderItem(int itemId, int orderId) {
        orderItems.removeIf(x -> x.getItemId() == itemId && x.getOrderId() == orderId);
    }

    public void deleteOrderItems(int orderId) {
        orderItems.removeIf(x -> x.getOrderId() == orderId);
    }

    public boolean validateOrderItem(OrderItem orderItem) {
        return OrderItemValidator.validate(orderItem);
    }
}
