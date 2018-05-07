// all in one
package project2;

import project2.models.OrderItem;
import project2.models.Customer;
import project2.models.Order;
import project2.models.Item;
import project2.repositories.Abstract.IOrderItemRepository;
import project2.repositories.Abstract.ICustomerRepository;
import project2.repositories.Abstract.IOrderRepository;
import project2.repositories.Abstract.IItemRepository;
import project2.repositories.FakeOrderItemRepository;
import project2.repositories.FakeCustomerRepository;
import project2.repositories.FakeOrderRepository;
import project2.repositories.FakeItemRepository;
import project2.services.OrderItemService;
import project2.services.CustomerService;
import project2.services.OrderService;
import project2.services.ItemService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class ServicesFakesTest {

    IOrderItemRepository orderItemRepository;
    ICustomerRepository customerRepository;
    IOrderRepository orderRepository;
    IItemRepository itemRepository;

    OrderItemService orderItemService;
    CustomerService customerService;
    OrderService orderService;
    ItemService itemService;

    @BeforeEach
    public void setUp() {
        orderItemRepository = new FakeOrderItemRepository();
        customerRepository = new FakeCustomerRepository();
        orderRepository = new FakeOrderRepository();
        itemRepository = new FakeItemRepository();
        orderItemService = new OrderItemService(orderRepository, itemRepository, orderItemRepository);
        customerService = new CustomerService(customerRepository, orderRepository);
        orderService = new OrderService(orderRepository, customerRepository, orderItemRepository);
        itemService = new ItemService(itemRepository);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void addCustomerThatIsValidIsAddedToTheList() {
        customerService.addCustomer("Jan", "Ptak", "abc@xyz.pl");

        assertThat(customerService.getCustomers().get(0).getEmail()).startsWith("abc");
    }

    @Test
    public void getCustomersReturnsAllCustomersFromTheList() {
        customerService.addCustomer("Jan", "Ptak", "abc@xyz.pl");
        customerService.addCustomer("Patryk", "Kot", "xyz@abc.pl");

        List result = customerService.getCustomers();
        assertEquals(2, result.size());
    }

    @Test
    public void addCustomerThatIsInvalidThrowsException() {
        assertThatThrownBy(() -> { customerService.addCustomer("", null, ""); })
                                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateCustomerThatExistsOnListAndDataIsValidPreformUpdate() {
        customerService.addCustomer("Pszemek", "Pszemkowski", "cda@ug.pl");
        customerService.updateCustomer(0, "Przemysław", "Przemkowski", "cda@ug.pl");

        assertThat(customerService.getCustomers().get(0).getFirstName()).contains("Przemysław");
    }

    @Test
    public void updateCustomerWithNewEmail() {
        customerService.addCustomer("Pszemek", "Pszemkowski", "zwu@ug.pl");
        customerService.addCustomer("Andrzej", "Andrzejowski", "xyz@ug.pl");
        customerService.updateCustomer(1, "Andrzej", "Andrzejowski", "cda@ug.pl");
        assertThat(customerService.getCustomers().get(1).getEmail()).contains("cda");
    }

    @Test
    public void deleteCustomerThatExistsOnListPreformRemove() {
        customerService.addCustomer("Pszemek", "Pszemkowski", "cda@ug.pl");
        customerService.deleteCustomer(0);

        assertThat(customerService.getCustomers().isEmpty()).isTrue();
    }

    @Test
    public void deleteCustomerRemovesAllCustomerOrders() {
        customerService.addCustomer("Pszemek", "Pszemkowski", "xyz@ug.pl");
        orderService.addOrder(0);
        orderService.addOrder(0);
        // assertEquals(2, orderService.getOrdersByCustomer(0).size());
        customerService.deleteCustomer(0);

        assertThat(orderService.getOrdersByCustomer(0).isEmpty()).isTrue();
    }

    @Test
    public void addCustomerThatAlreadyExistsOnTheListIsNotAdded() {
        customerService.addCustomer("Pszemek", "Pszemkowski", "cda@ug.pl");
        customerService.addCustomer("Pszemek", "Pszemkowski", "cda@ug.pl");

        assertEquals(1, customerService.getCustomers().size());
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void addOrderThatIsValidIsAddedToTheList() {
        customerService.addCustomer("Pszemek", "Pszemkowski", "xyz@ug.pl");
        orderService.addOrder(0);

        assertThat(orderService.getOrders().get(0).getCustomerId()).isEqualTo(0);
    }

    @Test
    public void getOrdersReturnsAllOrdersFromTheList() {
        customerService.addCustomer("Pszemek", "Pszemkowski", "xyz@ug.pl");
        customerService.addCustomer("Pszemek", "Pszemkowski", "cda@ug.pl");
        customerService.addCustomer("Pszemek", "Pszemkowski", "hhh@ug.pl");
        customerService.addCustomer("Pszemek", "Pszemkowski", "xxx@ug.pl");
        orderService.addOrder(0);
        orderService.addOrder(0);
        orderService.addOrder(1);
        orderService.addOrder(2);

        assertEquals(4, orderService.getOrders().size());
    }

    @Test
    public void addOrderThatCustomerIsInvalidReturnsFalse() {
        assertEquals(false, orderService.addOrder(1));
    }

    @Test
    public void deleteOrderThatExistsOnListPreformRemove() {
        customerService.addCustomer("Pszemek", "Pszemkowski", "xyz@ug.pl");
        orderService.addOrder(0);

        orderService.deleteOrder(0);

        assertThat(orderService.getOrders().isEmpty()).isTrue();
    }

    @Test
    public void deleteOrderRemovesAllOrderItems() {
        customerService.addCustomer("Pszemek", "Pszemkowski", "xyz@ug.pl");
        itemService.addItem("Buty", 100.50);
        itemService.addItem("Spodnie", 60.99);
        orderService.addOrder(0);
        orderItemService.addOrderItem(0, 0);
        orderItemService.addOrderItem(1, 0);
        // assertEquals(2, orderItemService.getItemsByOrder(0).size());
        orderService.deleteOrder(0);

        assertThat(orderItemService.getItemsByOrder(0).isEmpty()).isTrue();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void addItemThatIsValidIsAddedToTheList() {
        itemService.addItem("ABC programowania", 9.99);

        assertThat(itemService.getItems().get(0).getName()).startsWith("ABC");
    }

    @Test
    public void getItemsReturnsAllItemsFromTheList() {
        itemService.addItem("ABC programowania", 9.99);
        itemService.addItem("Stare płyty", 19.99);

        List result = itemService.getItems();
        assertEquals(2, result.size());
    }

    @Test
    public void addItemThatIsInvalidThrowsException() {
        assertThatThrownBy(() -> { itemService.addItem("", 0.0001); })
                                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateItemThatExistsOnListAndDataIsValidPreformUpdate() {
        itemService.addItem("ABC programowania", 9.99);
        itemService.updateItem(0, "ABC gotowania", 10.00);

        assertThat(itemService.getItems().get(0).getName()).contains("gotowania");
    }

    @Test
    public void updateItemThatAlreadyHasTakenNameThrowsException() {
        itemService.addItem("ABC programowania 1", 9.99);
        itemService.addItem("ABC programowania 2", 10.99);
        assertThatThrownBy(() -> { itemService.updateItem(1, "ABC programowania 1", 10.99); })
                                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void deleteItemThatExistsOnListPreformRemove() {
        itemService.addItem("ABC programowania", 9.99);
        itemService.deleteItem(0);

        assertThat(itemService.getItems().isEmpty()).isTrue();
    }

    @Test
    public void addItemThatAlreadyExistsOnTheListIsNotAdded() {
        itemService.addItem("ABC programowania", 9.99);
        itemService.addItem("ABC programowania", 19.99);

        assertEquals(1, itemService.getItems().size());
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void addOrderItemThatIsValidIsAddedToTheList() {
        customerService.addCustomer("Pszemek", "Pszemkowski", "xyz@ug.pl");
        orderService.addOrder(0);
        itemService.addItem("ABC programowania", 9.99);
        itemService.addItem("Stare płyty", 19.99);
        orderItemService.addOrderItem(0, 0);
        orderItemService.addOrderItem(1, 0);

        assertEquals(0, orderItemService.getOrderItems().get(0).getOrderId());
    }

    @Test
    public void getOrderItemsReturnsAllOrderItemsFromTheList() {
        customerService.addCustomer("Pszemek", "Pszemkowski", "xyz@ug.pl");
        orderService.addOrder(0);
        itemService.addItem("ABC programowania", 9.99);
        itemService.addItem("Stare płyty", 19.99);
        orderItemService.addOrderItem(0, 0);
        orderItemService.addOrderItem(1, 0);

        customerService.addCustomer("Alicja", "Pszemkowski", "abc@ug.pl");
        orderService.addOrder(1);
        itemService.addItem("ABC programowania", 9.99);
        itemService.addItem("Stare płyty", 19.99);
        orderItemService.addOrderItem(0, 1);
        orderItemService.addOrderItem(1, 1);

        List result = orderItemService.getOrderItems();
        assertEquals(4, result.size());
    }

    @Test
    public void deleteOrderItemThatDoesNotExists() {
        assertThatThrownBy(() -> { orderItemService.deleteOrderItem(2, 0); })
                                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void deleteOrderItemThatHasWrongData() {
        assertThatThrownBy(() -> { orderItemService.deleteOrderItem(-2, 0); })
                                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void deleteOrderItemThatExistsOnListPreformRemove() {
        customerService.addCustomer("Pszemek", "Pszemkowski", "xyz@ug.pl");
        orderService.addOrder(0);
        itemService.addItem("ABC programowania", 9.99);
        itemService.addItem("Stare płyty", 19.99);
        orderItemService.addOrderItem(0, 0);

        orderItemService.deleteOrderItem(0, 0);

        assertThat(orderItemService.getOrderItems().isEmpty()).isTrue();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @AfterEach
    public void tearDown() {
        orderItemRepository = null;
        customerRepository = null;
        orderRepository = null;
        itemRepository = null;
        orderItemService = null;
        customerService = null;
        orderService = null;
        itemService = null;
    }

}