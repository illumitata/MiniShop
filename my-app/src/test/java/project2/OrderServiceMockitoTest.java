package project2;

import extensions.MockitoExtension;

import project2.models.Order;
import project2.models.Customer;
import project2.models.Item;
import project2.repositories.Abstract.IOrderItemRepository;
import project2.repositories.Abstract.ICustomerRepository;
import project2.repositories.Abstract.IOrderRepository;
import project2.services.OrderService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SuppressWarnings("deprecation")
@ExtendWith(MockitoExtension.class)
public class OrderServiceMockitoTest {

    IOrderRepository orderRepository;
    IOrderItemRepository orderItemRepository;
    ICustomerRepository customerRepository;

    OrderService orderService;

    @BeforeEach
    public void setUp() {
        orderRepository = Mockito.mock(IOrderRepository.class);
        orderItemRepository = Mockito.mock(IOrderItemRepository.class);
        customerRepository = Mockito.mock(ICustomerRepository.class);
        orderService = new OrderService(orderRepository, customerRepository, orderItemRepository);
    }

    @Test
    public void addOrderValidReturnsTrue() {
        Order order = new Order();
        doReturn(true).when(customerRepository).customerExists(order.getCustomerId());
        doReturn(true).when(orderRepository).validateOrder(any(Order.class));

        boolean result = orderService.addOrder(order.getCustomerId());
        assertThat(result).isTrue();
    }

    @Test
    public void addOrderWithInvalidDataThrowsException() {
        Order order = new Order();
        doReturn(true).when(customerRepository).customerExists(order.getCustomerId());
        doReturn(false).when(orderRepository).validateOrder(any(Order.class));

        assertThrows(IllegalArgumentException.class, () -> { orderService.addOrder(order.getCustomerId()); });
    }

    @Test
    public void addOrderThatCustomerDoesNotExistsReturnsFalse() {
        Order order = new Order();
        doReturn(false).when(customerRepository).customerExists(order.getCustomerId());

        boolean result = orderService.addOrder(order.getCustomerId());
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void deleteOrderThatExistsReturnsTrue() {
        doReturn(true).when(orderRepository).orderExists(1);

        boolean result = orderService.deleteOrder(1);
        assertEquals(true, result);
    }

    @Test
    public void deleteOrderThatDoesNotExistThrowsException() {
        doReturn(false).when(orderRepository).orderExists(2);

        assertThatThrownBy(() -> { orderService.deleteOrder(2); }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void getOrdersByCustomerThatExistsReturnsListWithCorrectNumberOfElements() {
        Customer customer = new Customer();
        Order orderOne = new Order();
        Order orderTwo = new Order();
        Order orderThree = new Order();
        doReturn(Arrays.asList(orderOne, orderTwo, orderThree)).when(orderRepository)
                                                               .getOrdersByCustomer(customer.getId());

        assertThat(orderService.getOrdersByCustomer(customer.getId()).size()).isEqualTo(3);
    }

    @Test
    public void getOrdersByCustomerThatDoesNotExistsReturnsEmptyList() {
        doReturn(Arrays.asList()).when(orderRepository).getOrdersByCustomer(0);

        assertThat(orderService.getOrdersByCustomer(0)).isInstanceOf(List.class);
    }

    @AfterEach
    public void tearDown() {
        orderRepository = null;
        orderItemRepository = null;
        customerRepository = null;
        orderService = null;
    }
}
