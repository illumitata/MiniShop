// suplements the ServicesFakes for OrderItem, mainly validation
package project2;

import extensions.EasyMockExtension;

import project2.models.OrderItem;
import project2.models.Order;
import project2.models.Item;
import project2.repositories.Abstract.IOrderItemRepository;
import project2.repositories.Abstract.IOrderRepository;
import project2.repositories.Abstract.IItemRepository;
import project2.services.OrderItemService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.easymock.EasyMock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(EasyMockExtension.class)
public class OrderItemServiceEasyMockTest {

    IOrderItemRepository orderItemRepository;
    IItemRepository itemRepository;
    IOrderRepository orderRepository;

    OrderItemService orderItemService;

    @BeforeEach
    public void setUp() {
        orderItemRepository = EasyMock.createNiceMock(IOrderItemRepository.class);
        itemRepository = EasyMock.createNiceMock(IItemRepository.class);
        orderRepository = EasyMock.createNiceMock(IOrderRepository.class);
        orderItemService = new OrderItemService(orderRepository, itemRepository, orderItemRepository);
    }

    @Test
    public void addOrderItemValidReturnsTrue() {
        OrderItem orderItem = new OrderItem();

        expect(orderRepository.validateOrder(anyObject(Order.class))).andReturn(true);
        expect(orderRepository.orderExists(0)).andReturn(true);
        replay(orderRepository);

        expect(itemRepository.validateItem(anyObject(Item.class))).andReturn(true);
        expect(itemRepository.itemExists(0)).andReturn(true);
        replay(itemRepository);

        expect(orderItemRepository.validateOrderItem(anyObject(OrderItem.class))).andReturn(true);
        replay(orderItemRepository);

        boolean result = orderItemService.addOrderItem(0, 0);
        assertThat(result).isTrue();
    }

    @Test
    public void addOrderItemWithInvalidDataReturnsFalse() {
        OrderItem orderItem = new OrderItem();
        expect(orderItemRepository.validateOrderItem(anyObject(OrderItem.class))).andReturn(false);
        replay(orderItemRepository);

        boolean result = orderItemService.addOrderItem(orderItem.getItemId(), orderItem.getOrderId());
        assertThat(result).isFalse();
    }

    @Test
    public void addOrderItemThatOrderDoesNotExistsReturnsFalse() {
        OrderItem orderItem = new OrderItem();

        expect(orderRepository.validateOrder(anyObject(Order.class))).andReturn(true);
        expect(orderRepository.orderExists(0)).andReturn(false);
        replay(orderRepository);

        expect(itemRepository.validateItem(anyObject(Item.class))).andReturn(true);
        expect(itemRepository.itemExists(0)).andReturn(true);
        replay(itemRepository);

        expect(orderItemRepository.validateOrderItem(anyObject(OrderItem.class))).andReturn(true);
        replay(orderItemRepository);

        boolean result = orderItemService.addOrderItem(0, 0);
        assertThat(result).isFalse();
    }

    @Test
    public void addOrderItemThatSomehowAllPassButNotValidationReturnsFalse() {
        OrderItem orderItem = new OrderItem();

        expect(orderRepository.validateOrder(anyObject(Order.class))).andReturn(true);
        expect(orderRepository.orderExists(0)).andReturn(true);
        replay(orderRepository);

        expect(itemRepository.validateItem(anyObject(Item.class))).andReturn(true);
        expect(itemRepository.itemExists(0)).andReturn(true);
        replay(itemRepository);

        expect(orderItemRepository.validateOrderItem(anyObject(OrderItem.class))).andReturn(false);
        replay(orderItemRepository);

        boolean result = orderItemService.addOrderItem(0, 0);
        assertThat(result).isFalse();
    }

    @AfterEach
    public void tearDown() {
        orderItemRepository = null;
        itemRepository = null;
        orderRepository = null;
        orderItemService = null;
    }
}
