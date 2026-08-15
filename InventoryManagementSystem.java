import java.util.ArrayList;
import java.util.List;

public class InventoryManagementSystem {
    private final List<Item> items = new ArrayList<Item>();

    public boolean isValidCategory(String category) {
        if (category.equalsIgnoreCase("Clothing") || category.equalsIgnoreCase("Electronics")
                || category.equalsIgnoreCase("Entertainment")) {
            return true;
        }
        return false;
    }

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

    public boolean updateQuantityItem(String ID, int quantity) {
        Item item = findSpecificItem(ID);
        if (item == null) {
            return false;
        }
        item.setQuantity(quantity);
        return true;
    }

    public boolean updatePriceItem(String ID, double price) {
        Item item = findSpecificItem(ID);
        if (item == null) {
            return false;
        }
        item.setPrice(price);
        return true;
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

    public List<Item> getItemsByCategory(String category) {
        List<Item> categoryItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                categoryItems.add(item);
            }
        }
        return categoryItems;
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
