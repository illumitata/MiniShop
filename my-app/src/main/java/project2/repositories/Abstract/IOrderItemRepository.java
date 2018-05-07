package project2.repositories.Abstract;

import project2.models.OrderItem;
import java.util.List;

public interface IOrderItemRepository {
    List getOrderItems();
    List getItemsByOrder(int orderId);
    OrderItem getOrderItem(int itemId, int orderId);
    boolean orderItemExists(int itemId, int orderId);
    void addOrderItem(OrderItem orderItem);
    void deleteOrderItem(int itemId, int orderId);
    void deleteOrderItems(int orderId);
    boolean validateOrderItem(OrderItem orderItem);
}