import java.util.List;

public class Main {
    public static final InventoryManagementSystem ims = new InventoryManagementSystem();

    public static void main(String[] args) {
        boolean programRunning = true;
        while (programRunning) {
            System.out.println("========== INVENTORY MANAGEMENT SYSTEM ==========");
            System.out.println("1. Add Item");
            System.out.println("2. Update Item");
            System.out.println("3. Remove Item");
            System.out.println("4. Display Items by Category");
            System.out.println("5. Display all Items");
            System.out.println("6. Search Item");
            System.out.println("7. Sort Items");
            System.out.println("8. Display Low Stock Items");
            System.out.println("9. Exit");
            int choice = Validators.validateNumberChoiceInput("Choice(1-9): ", 1, 9);
            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    updateItem();
                    break;
                case 3:
                    removeItem();
                    break;
                case 4:
                    displayItemsByCategory();
                    break;
                case 5:
                    displayAllItems();
                    break;
                case 6:
                    searchItem();
                    break;
                case 7:
                    sortItems();
                    break;
                case 8:
                    displayLowQuantityItems();
                    break;
                case 9:
                    System.out.println("Thank you for using the program.\nExiting...");
                    programRunning = false;
                    break;
                default:
                    break;
            }
        }
    }

    public static void addItem() {
        String ID, name;
        int quantity;
        double price;
        String category = "";
        boolean canAddItem = false;

        category = Validators.validateCategoryInput("Input Category(Clothing/Electronics/Entertainment): ");
        do {
            ID = Validators.validateIDInput(category);
            if (ims.findDuplicateItem(ID)) {
                System.out.println("Item with that ID already exists. Try Again.");
                continue;
            }
            canAddItem = true;
        } while (!canAddItem);
        name = Validators.validateStringInput("Input Name: ");
        quantity = Validators.validateQuantityInput("Input Quantity: ", false);
        price = Validators.validateDoubleInput("Input Price: ");
        Item item;
        switch (category) {
            case "clothing":
                item = new Clothing(ID, name, quantity, price);
                break;
            case "electronics":
                item = new Electronics(ID, name, quantity, price);
                break;
            case "entertainment":
                item = new Entertainment(ID, name, quantity, price);
                break;
            default:
                return;
        }
        ims.addItem(item);
    }

    public static void updateItem() {
        List<Item> allItems = ims.getAllItems();
        if (isListEmpty(allItems, "There are no items in the system to update.")) {
            return;
        }
        String ID;
        ID = Validators.validateStringInput("Input ID of item to Update: ");
        Item item = ims.findSpecificItem(ID);
        if (item == null) {
            System.out.println("Item not Found!");
            return;
        }
        String field = Validators
                .validateFieldChoice("Update Quantity or Price?\n1. Quantity\n2. Price\nChoice(1 or 2): ");
        if (field.equalsIgnoreCase("quantity")) {
            int oldQuantity = 0, newQuantity = 0;
            do {
                oldQuantity = item.getQuantity();
                newQuantity = Validators.validateQuantityInput("Input new Quantity: ", true);
                if (oldQuantity == newQuantity) {
                    System.out.printf("Quantity of Item '%s' is already %d. Try Again\n", item.getName(), oldQuantity);
                }
            } while (oldQuantity == newQuantity);

            ims.updateQuantityItem(ID, newQuantity);
            System.out.printf("%s\nQuantity of Item '%s' is updated from %d to %d\n%s\n", ".".repeat(30),
                    item.getName(), oldQuantity,
                    newQuantity, ".".repeat(30));
        } else {
            double oldPrice = 0, newPrice = 0;
            do {
                oldPrice = item.getPrice();
                newPrice = Validators.validateDoubleInput("Input new Price: ");
                if (oldPrice == newPrice) {
                    System.out.printf("Price of Item '%s' is already %.2f. Try Again\n", item.getName(), oldPrice);
                }
            } while (oldPrice == newPrice);

            ims.updatePriceItem(ID, newPrice);
            System.out.printf("%s\nPrice of Item '%s' is updated from P%.2f to P%.2f\n%s\n", ".".repeat(30),
                    item.getName(), oldPrice,
                    newPrice, ".".repeat(30));
        }
    }

    public static void removeItem() {
        List<Item> allItems = ims.getAllItems();
        if (isListEmpty(allItems, "There are no items in the system to remove.")) {
            return;
        }
        String ID;
        ID = Validators.validateStringInput("Input ID of Item to Remove: ");
        Item item = ims.findSpecificItem(ID);
        if (item == null) {
            System.out.println("Item not Found!");
            return;
        }
        String name = item.getName();
        ims.removeItem(ID);
        System.out.printf("Item '%s' has been removed from the inventory.\n", name);
    }

    public static void searchItem() {
        List<Item> allItems = ims.getAllItems();
        if (isListEmpty(allItems, "There are no items in the system to search.")) {
            return;
        }
        String ID = Validators.validateStringInput("Enter ID of Item to Search: ");
        Item item = ims.findSpecificItem(ID);
        if (item == null) {
            System.out.println("Item not Found!");
            return;
        }
        System.out.printf("%s\nItem ID '%s' Details\n%s\n", ".".repeat(30), item.getItemID(), ".".repeat(30));
        printTableHeader(true);
        System.out.println(item.toDisplayFormat(true));
    }

    public static void sortItems() {
        List<Item> allItems = ims.getAllItems();
        if (isListEmpty(allItems, "There are no items in the system to sort.")) {
            return;
        }
        String sortField = Validators
                .validateFieldChoice("Sort by Quantity or Price?\n1. Quantity\n2. Price\nChoice(1 or 2): ");
        boolean isAscending = Validators
                .validateSortOrderChoice(
                        "Sort by Ascending or Descending\n1. Ascending\n2. Descending\nChoice(1 or 2): ");

        List<Item> sorted = ims.getSortedItems(sortField, isAscending);
        printTableHeader(true);
        for (Item item : sorted) {
            System.out.println(item.toDisplayFormat(true));
        }
    }

    public static void printNoItemMessage(String message) {
        System.out.println(".".repeat(30));
        System.out.println(message);
        System.out.println(".".repeat(30));
    }

    public static void printTableHeader(boolean includeCategory) {
        if (includeCategory) {
            System.out.printf("%-20s %-20s %-20s %-20s %-20s\n", "ITEM ID", "NAME", "QUANTITY", "PRICE", "CATEGORY");
            System.out.println("-".repeat(100));
        } else {
            System.out.printf("%-20s %-20s %-20s %-20s\n", "ITEM ID", "NAME", "QUANTITY", "PRICE");
            System.out.println("-".repeat(80));
        }
    }

    public static boolean isListEmpty(List<Item> items, String message) {
        if (items.isEmpty()) {
            printNoItemMessage(message);
            return true;
        }
        return false;
    }

    public static void displayAllItems() {
        List<Item> allItems = ims.getAllItems();
        if (isListEmpty(allItems, "There are no items in the system to display.")) {
            return;
        }
        System.out.println("All Items");
        printTableHeader(true);
        for (Item item : allItems) {
            System.out.println(item.toDisplayFormat(true));
        }
    }

    public static void displayItemsByCategory() {
        List<Item> allItems = ims.getAllItems();
        String category = "";
        if (isListEmpty(allItems, "There are no items in the system to display by category.")) {
            return;
        }
        category = Validators.validateCategoryInput("Input Category(Clothing/Electronics/Entertainment): ");

        List<Item> categoryItems = ims.getItemsByCategory(category);
        if (isListEmpty(categoryItems, String.format("There are no %s items in the system.", category))) {
            return;
        }
        System.out.println("Items by category");// TODO ADJUST MESSAGE TO DISPLAY WHAT CATEGORY IT IS
        printTableHeader(false);
        for (Item categoryItem : categoryItems) {
            System.out.println(categoryItem.toDisplayFormat(false));
        }

    }

    public static void displayLowQuantityItems() {
        List<Item> lowQuantityItems = ims.getLowQuantityItems();
        if (isListEmpty(lowQuantityItems, "There are no low quantity items in the system to display.")) {
            return;
        }
        System.out.println("Low Quantity Items");
        printTableHeader(true);
        for (Item lowQuanItem : lowQuantityItems) {
            System.out.println(lowQuanItem.toDisplayFormat(true));
        }
    }

}
