import java.util.ArrayList;
import java.util.List;

public class InventoryManagementSystem {
    private final List<Item> items = new ArrayList<Item>();

    public boolean findDuplicateItem(String itemID) {
        for (Item item : items) {
            if (itemID.equalsIgnoreCase(item.getItemID())) {
                return true;
            }
        }
        return false;
    }

    public void addItem(Item item) {
        items.add(item);
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

    public List<Item> getSortedItems(String sortBy, boolean ascending) {
        List<Item> sorted = new ArrayList<>(items);
        sorted.sort((a, b) -> {
            int comparison;
            if (sortBy.equalsIgnoreCase("quantity")) {
                comparison = Integer.compare(a.getQuantity(), b.getQuantity());
            } else {
                comparison = Double.compare(a.getPrice(), b.getPrice());
            }
            return ascending ? comparison : -comparison;
        });
        return sorted;
    }

    public boolean isEmpty() {
        return items.isEmpty();
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
