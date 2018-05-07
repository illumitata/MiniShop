package project2;

import extensions.EasyMockExtension;

import project2.models.Customer;
import project2.repositories.Abstract.ICustomerRepository;
import project2.repositories.Abstract.IOrderRepository;
import project2.services.CustomerService;

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
public class CustomerServiceEasyMockTest {

    ICustomerRepository customerRepository;
    IOrderRepository orderRepository;

    CustomerService customerService;

    @BeforeEach
    public void setUp() {
        customerRepository = EasyMock.createNiceMock(ICustomerRepository.class);
        orderRepository = EasyMock.createNiceMock(IOrderRepository.class);
        customerService = new CustomerService(customerRepository, orderRepository);
    }

    @Test
    public void addCustomerValidReturnsTrue() {
        Customer customer = new Customer();
        expect(customerRepository.validateCustomer(anyObject(Customer.class))).andReturn(true);
        expect(customerRepository.customerExists(null)).andReturn(false);
        replay(customerRepository);

        boolean result = customerService.addCustomer(customer.getFirstName(), customer.getLastName(),
                                                     customer.getEmail());
        assertThat(result).isTrue();
    }

    @Test
    public void addCustomerWithInvalidDataThrowsException() {
        Customer customer = new Customer();
        expect(customerRepository.validateCustomer(anyObject(Customer.class))).andReturn(false);
        replay(customerRepository);

        assertThrows(IllegalArgumentException.class, () -> { customerService.addCustomer(customer.getFirstName(),
                                                            customer.getLastName(), customer.getEmail()); });
    }

    @Test
    public void addCustomerThatHasSameEmailAsOtherCustomerReturnsFalse() {
        Customer customer = new Customer();
        expect(customerRepository.customerExists(customer.getEmail())).andReturn(true);
        replay(customerRepository);

        boolean result = customerService.addCustomer(customer.getFirstName(), customer.getLastName(),
                                                     customer.getEmail());
        assertThat(result).isFalse();
    }

    @Test
    public void deleteCustomerThatExistsReturnsTrue() {
        expect(customerRepository.customerExists(1)).andReturn(true);
        replay(customerRepository);

        boolean result = customerService.deleteCustomer(1);
        assertEquals(true, result);
    }

    @Test
    public void deleteCustomerThatDoesNotExistsThrowsException() {
        expect(customerRepository.customerExists(2)).andReturn(false);
        replay(customerRepository);

        assertThrows(IllegalArgumentException.class, ()->{ customerService.deleteCustomer(2); });
    }

    @Test
    public void updateCustomerThatExistsWithValidDataReturnsTrue() {
        Customer customer = new Customer();
        expect(customerRepository.customerExists(0)).andReturn(true);
        expect(customerRepository.validateCustomer(anyObject(Customer.class))).andReturn(true);
        expect(customerRepository.getCustomer(0)).andReturn(customer);
        expect(customerRepository.getCustomer(customer.getEmail())).andReturn(customer);
        replay(customerRepository);

        boolean result = customerService.updateCustomer(0, null, null, null);
        assertEquals(true, result);
    }

    @Test
    public void updateCustomerThatDoesNotExistThrowsException() {
        expect(customerRepository.customerExists(0)).andReturn(false);
        replay(customerRepository);

        assertThrows(IllegalArgumentException.class, () -> { customerService.updateCustomer(0 , null, null, null); });
    }

    @Test
    public void updateCustomerThatExistsWithInvalidDataThrowsException() {
        Customer customer = new Customer();
        expect(customerRepository.customerExists(0)).andReturn(true);
        expect(customerRepository.validateCustomer(anyObject(Customer.class))).andReturn(false);
        replay(customerRepository);

        assertThrows(IllegalArgumentException.class, () -> { customerService.updateCustomer(0, null, null, null); });
    }

    @Test
    public void updateCustomerWithEmailThatAlreadyExistsThrowsException() {
        Customer customerOne = new Customer();
        Customer customerTwo = new Customer();
        expect(customerRepository.customerExists(0)).andReturn(true);
        expect(customerRepository.validateCustomer(anyObject(Customer.class))).andReturn(true);
        expect(customerRepository.getCustomer(0)).andReturn(customerOne);
        expect(customerRepository.getCustomer("abc@xyz.com")).andReturn(customerTwo);
        replay(customerRepository);

        assertThrows(IllegalArgumentException.class, () -> { customerService.updateCustomer(0, "Ala", "Kot", "abc@xyz.com"); });
    }

    @AfterEach
    public void tearDown() {
        customerRepository = null;
        orderRepository = null;
        customerService = null;
    }
}
