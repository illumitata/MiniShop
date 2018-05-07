package project2.models;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Item {
    private int id;
    private String name;
    // double is a better fit for me
    private double value;

    public Item() {

    }

    public Item(String name, double value) {
        this.name = name;
        this.value = value;
    }

    // Database model
    public Item(int id, Item item) {
        this.id = id;
        this.name = item.getName();
        this.value = item.getValue();
    }
}
