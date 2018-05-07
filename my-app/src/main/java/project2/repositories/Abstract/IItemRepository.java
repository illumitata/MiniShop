package project2.repositories.Abstract;

import project2.models.Item;
import java.util.List;

public interface IItemRepository {
    List getItems();
    Item getItem(int id);
    Item getItem(String name);
    // check id, name and value in diff funcs, I thinks it's good idea
    boolean itemExists(int id);
    boolean itemExists(String name);
    boolean itemExists(double value);
    void addItem(Item item);
    void deleteItem(int id);
    void updateItem(int id, Item item);
    boolean validateItem(Item item);
}