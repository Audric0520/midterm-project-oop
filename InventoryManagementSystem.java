import java.util.ArrayList;
import java.util.List;

public class InventoryManagementSystem {
    private final List<Item> items = new ArrayList<Item>();

    public boolean findDuplicateItem(String itemID) {
        for (Item item : items) {
            if (itemID.equals(item.getItemID())) {
                return true;
            }
        }
        return false;
    }

    public void addItem(Item item) {
        items.add(item);
        System.out.println("Success Add!");// TODO MAKE MESSAGE BETTER
    }

    public Item findSpecificItem(String ID) {
        for (Item item : items) {
            if (item.getItemID().equalsIgnoreCase(ID)) {
                return item;
            }
        }
        return null;
    }

    public boolean removeItem(String ID) {
        Item itemToRemove = findSpecificItem(ID);
        if (itemToRemove == null) {
            return false;
        }
        items.remove(itemToRemove);
        return true;
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(items);
    }

    public List<Item> getLowQuantityItems() {
        List<Item> lowQuantityItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getQuantity() <= 5) {
                lowQuantityItems.add(item);
            }
        }
        return lowQuantityItems;
    }

}
