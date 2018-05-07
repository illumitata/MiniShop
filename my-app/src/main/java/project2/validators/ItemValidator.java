package project2.validators;

import project2.models.Item;

public class ItemValidator {
    public static boolean validate(Item i) {
        if(i.getName() == null || i.getName().isEmpty() || i.getValue() < 0.01)
            return false;
        return true;
    }
}
