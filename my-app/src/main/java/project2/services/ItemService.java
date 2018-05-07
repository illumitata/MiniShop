package project2.services;

import project2.models.Item;
import project2.repositories.Abstract.IItemRepository;
import project2.repositories.FakeItemRepository;
import project2.validators.ItemValidator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ItemService {

    private IItemRepository itemRepository;

    public ItemService(IItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public boolean addItem(String name, double value) {
        if (itemRepository.itemExists(name))
            return false;
        Item i = new Item(name, value);
        if (!itemRepository.validateItem(i))
            throw new IllegalArgumentException("Item has some wrong data!");
        itemRepository.addItem(i);
        return true;
    }

    public boolean deleteItem(int id) {
        if (!itemRepository.itemExists(id))
            throw new IllegalArgumentException("Item with ID: " + id + ", does not exist!");
        itemRepository.deleteItem(id);
        // Deletes also all orders with that item? I think it shouldn't
        return true;
    }

    public boolean updateItem(int id, String name, double value) {
        if (!itemRepository.itemExists(id))
            throw new IllegalArgumentException("Item with ID: " + id + ", does not exist!");
        Item updated = new Item(name, value);
        if (!itemRepository.validateItem(updated))
            throw new IllegalArgumentException("Item has some wrong data!");
        Item iFromId = itemRepository.getItem(id);
        Item iFromName = itemRepository.getItem(name);
        if (iFromName == null || iFromId == iFromName) {
            itemRepository.updateItem(id, updated);
            return true;
        } else
            throw new IllegalArgumentException("Some other item has that name!");
    }

    public List<Item> getItems() {
        return itemRepository.getItems();
    }
}
