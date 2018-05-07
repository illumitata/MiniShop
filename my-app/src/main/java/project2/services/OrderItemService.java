package project2.services;

import project2.models.Order;
import project2.models.Item;
import project2.models.OrderItem;
import project2.repositories.Abstract.IOrderRepository;
import project2.repositories.Abstract.IItemRepository;
import project2.repositories.Abstract.IOrderItemRepository;
import project2.repositories.FakeOrderRepository;
import project2.repositories.FakeItemRepository;
import project2.repositories.FakeOrderItemRepository;
import project2.validators.OrderValidator;
import project2.validators.ItemValidator;
import project2.validators.OrderItemValidator;

import java.util.*;

public class OrderItemService {

    private IOrderRepository orderRepository;
    private IItemRepository itemRepository;
    private IOrderItemRepository orderItemRepository;

    public OrderItemService(IOrderRepository orderRepository, IItemRepository itemRepository,
                            IOrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.itemRepository = itemRepository;
    }

    public boolean addOrderItem(int itemId, int orderId) {
        // can't add orderitem if there is no such order or item
        if (!itemRepository.itemExists(itemId) || !orderRepository.orderExists(orderId))
            return false;
        OrderItem oi = new OrderItem(itemId, orderId);
        if (!orderItemRepository.validateOrderItem(oi))
            return false;
        orderItemRepository.addOrderItem(oi);
        return true;
    }

    public boolean deleteOrderItem(int itemId, int orderId) {
        if (!orderItemRepository.orderItemExists(itemId, orderId))
            throw new IllegalArgumentException("OrderItem with itemID: " + itemId + " and orderID" + orderId +
                                               ", does not exist!");
        orderItemRepository.deleteOrderItem(itemId, orderId);
        return true;
    }

    public List<OrderItem> getOrderItems() {
        return orderItemRepository.getOrderItems();
    }

    public List<OrderItem> getItemsByOrder(int orderId) {
        return orderItemRepository.getItemsByOrder(orderId);
    }
}
