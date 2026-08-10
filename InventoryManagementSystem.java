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
        System.out.println("Success Add!");
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

    public void displayAllItems() {
        if (items.isEmpty()) {
            System.out.println(".".repeat(30));
            System.out.println("There are no items in the system.");
            System.out.println(".".repeat(30));
            return;
        }
        System.out.println("All Items");
        System.out.printf("%-20s %-20s %-20s %-20s %-20s\n", "ITEM ID", "NAME", "QUANTITY", "PRICE", "CATEGORY");
        System.out.println("-".repeat(100));
        for (Item i : items) {
            System.out.println(i.toDisplayFormat());
        }
    }

    public void displayLowQuantityItems() {
        if (items.isEmpty()) {
            System.out.println(".".repeat(30));
            System.out.println("There are no items in the system.");
            System.out.println(".".repeat(30));
            return;
        }
        System.out.println("Low Quantity Items");
        System.out.printf("%-20s %-20s %-20s %-20s %-20s\n", "ITEM ID", "NAME", "QUANTITY", "PRICE", "CATEGORY");
        System.out.println("-".repeat(100));
        for (Item i : items) {
            if (i.getQuantity() <= 5) {
                System.out.println(i.toDisplayFormat());
            }
        }
    }
}
