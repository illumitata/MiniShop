package project2.repositories;

import project2.models.Item;
import project2.models.OrderItem;
import project2.repositories.Abstract.IItemRepository;
import project2.validators.ItemValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FakeItemRepository implements IItemRepository {

    private int idCounter = 0;
    private List<Item> items = new ArrayList<Item>();

    public List getItems() {
        return items;
    }

    public Item getItem(int id) {
        return items.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    public Item getItem(String name) {
        return items.stream().filter(x -> x.getName() == name).findFirst().orElse(null);
    }

    public boolean itemExists(int id) {
        return items.stream().anyMatch(x -> x.getId() == id);
    }

    public boolean itemExists(String name) {
        return items.stream().anyMatch(x -> x.getName().equals(name));
    }

    public boolean itemExists(double value) {
        return items.stream().anyMatch(x -> x.getValue() == value);
    }

    public void addItem(Item item) {
        items.add(new Item(idCounter, item));
        idCounter++;
    }

    public void deleteItem(int id) {
        items.removeIf(x -> x.getId() == id);
        // to add: delete also all OrderItems contains id!
    }

    public void updateItem(int id, Item item) {
        Item i = items.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
        if(i != null) {
            items.set(items.indexOf(i), new Item(id, item));
        }
    }

    public boolean validateItem(Item item) {
        return ItemValidator.validate(item);
    }
}
