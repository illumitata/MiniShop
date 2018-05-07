package project2;

import extensions.MockitoExtension;

import project2.models.Item;
import project2.repositories.Abstract.IItemRepository;
import project2.services.ItemService;

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
public class ItemServiceMockitoTest {

    IItemRepository itemRepository;
    ItemService itemService;

    @BeforeEach
    public void setUp() {
        itemRepository = Mockito.mock(IItemRepository.class);
        itemService = new ItemService(itemRepository);
    }

    @Test
    public void addItemValidReturnsTrue() {
        Item item = new Item();
        doReturn(false).when(itemRepository).itemExists(item.getName());
        doReturn(true).when(itemRepository).validateItem(any(Item.class));

        boolean result = itemService.addItem(item.getName(), item.getValue());
        assertThat(result).isTrue();
    }

    @Test
    public void addItemWithInvalidDataThrowsException() {
        Item item = new Item();
        doReturn(false).when(itemRepository).itemExists(item.getName());
        doReturn(false).when(itemRepository).validateItem(any(Item.class));

        assertThrows(IllegalArgumentException.class, () -> { itemService.addItem(item.getName(), item.getValue()); });
    }

    @Test
    public void addItemThatHaveNameAlreadyExistsReturnsFalse() {
        Item item = new Item();
        doReturn(true).when(itemRepository).itemExists(item.getName());

        boolean result = itemService.addItem(item.getName(), item.getValue());
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void deleteItemThatExistsReturnsTrue() {
        doReturn(true).when(itemRepository).itemExists(1);

        boolean result = itemService.deleteItem(1);
        assertEquals(true, result);
    }

    @Test
    public void deleteItemThatDoesNotExistThrowsException() {
        doReturn(false).when(itemRepository).itemExists(2);

        assertThatThrownBy(() -> { itemService.deleteItem(2); }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateItemThatExistsAndDataIsValidReturnsTrue() {
        Item item = new Item();
        doReturn(true).when(itemRepository).itemExists(0);
        doReturn(true).when(itemRepository).validateItem(any(Item.class));
        doReturn(item).when(itemRepository).getItem(0);
        doReturn(item).when(itemRepository).getItem(item.getName());

        boolean result = itemService.updateItem(0, null, 0.00);
        assertEquals(true, result);
    }

    @Test
    public void updateItemThatDoesNotExistsThrowsException() {
        doReturn(false).when(itemRepository).itemExists(1);

        assertThatThrownBy(() -> { itemService.updateItem(1, null, 0.00); })
                                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateItemThatExistsWithWrongDataThrowsException() {
        Item item = new Item();
        doReturn(true).when(itemRepository).itemExists(0);
        doReturn(false).when(itemRepository).validateItem(any(Item.class));

        assertThatThrownBy(() -> { itemService.updateItem(1, null, 0.00); })
                                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateItemWithNameThatAlreadyExistsThrowsException() {
        Item itemOne = new Item();
        Item itemTwo = new Item();
        doReturn(true).when(itemRepository).itemExists(0);
        doReturn(itemOne).when(itemRepository).getItem(0);
        doReturn(itemTwo).when(itemRepository).getItem("Buciorki");

        assertThatThrownBy(() -> { itemService.updateItem(0, null, 0.00); })
                                .isInstanceOf(IllegalArgumentException.class);
    }

    @AfterEach
    public void tearDown() {
        itemRepository = null;
        itemService = null;
    }
}
