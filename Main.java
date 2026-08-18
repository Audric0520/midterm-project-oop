import java.util.Scanner;
import java.util.List;

class Validators {
    public static final Scanner input = new Scanner(System.in);

    public static int validateIntInput(String prompt) {
        boolean isRunning = true;
        int number = 0;
        String inputNumber = "";
        while (isRunning) {
            System.out.print(prompt);
            inputNumber = input.nextLine().trim();
            if (!inputNumber.matches("-?(0|[1-9]\\d*)")) {
                System.out.println("Invalid Input. Only input positive integers without leading zeros. Try Again.");
                continue;
            }
            try {
                number = Integer.parseInt(inputNumber);
            } catch (NumberFormatException e) {
                System.out.println("Number is too large. Try Again.");
                continue;
            }
            isRunning = false;
        }
        return number;
    }

    public static int validateNumberChoiceInput(String prompt, int min, int max) {
        boolean isRunning = true;
        int number = 0;
        while (isRunning) {
            number = validateIntInput(prompt);
            if (number >= min & number <= max) {
                isRunning = false;
            } else {
                System.out.printf("Invalid Input. Only input %d-%d without leading zeros. Try Again.\n", min, max);
            }
        }
        return number;
    }

    public static String validateStringInput(String prompt) {
        boolean isRunning = true;
        String stringInput = "";
        while (isRunning) {
            System.out.print(prompt);
            stringInput = input.nextLine().trim();
            if (stringInput.isBlank()) {
                System.out.println("Invalid input. Input cannot be blank. Try Again.");
            } else {
                isRunning = false;
            }
        }
        return stringInput;
    }

    public static double validateDoubleInput(String prompt) {
        boolean isRunning = true;
        double number = 0;
        String inputNumber = "";

        while (isRunning) {
            System.out.print(prompt);
            inputNumber = input.nextLine().trim();

            if (!inputNumber.matches("-?(0|[1-9]\\d*)(\\.\\d+)?")) {
                System.out.println("Invalid Input. Only input numbers without leading zeroes. Try Again.");
                continue;
            }
            try {
                number = Double.parseDouble(inputNumber);
            } catch (NumberFormatException e) {
                System.out.println("Number is too large. Try Again.");
                continue;
            }
            if (number <= 0) {
                System.out.println("Input must be greater than 0. Try Again.");
                continue;
            }
            isRunning = false;
        }
        return number;
    }

    public static String validateIDInput(String category) {
        String prefix = getCategoryPrefix(category);
        System.out.printf("ID format: '%s' followed by numbers (e.g. %s01)\n", prefix, prefix);
        boolean isRunning = true;
        String id = "";
        while (isRunning) {
            id = validateStringInput("Input ID: ");
            if (!id.matches("(?i)" + prefix + "\\d+")) {
                System.out.printf(
                        "Invalid Input. ID must be '%s' followed by numbers (e.g. %s001, %s002). Try Again.\n",
                        prefix, prefix, prefix.toLowerCase());
                continue;
            }
            isRunning = false;
        }
        return id.toUpperCase();
    }

    private static String getCategoryPrefix(String category) {
        Item tempItem = createTempItem(category);
        return (tempItem != null) ? tempItem.getCategoryIdPrefix() : "";
    }

    private static Item createTempItem(String category) { // TODO ADD VALIDATORS FOR OTHER CLASSES
        switch (category.toLowerCase()) {
            case "clothing":
                return new Clothing("", "", 0, 0);
            // case "electronics": return new Electronics("", "", 0, 0);
            // case "entertainment": return new Entertainment("", "", 0, 0);
            default:
                return null;
        }
    }

    public static int validateQuantityInput(String prompt, boolean forUpdating) {
        boolean isRunning = true;
        int number = 0;
        while (isRunning) {
            number = validateIntInput(prompt);
            if (forUpdating) {
                if (number < 0) {
                    System.out.println("Invalid Input. Quantity cannot be negative. Try Again.");
                    continue;
                }
            } else {
                if (number <= 0) {
                    System.out.println("Invalid Input. Quantity must be greater than 0. Try Again.");
                    continue;
                }
            }
            isRunning = false;
        }
        return number;
    }

    public static String validateFieldChoice(String prompt) {
        int choice = validateNumberChoiceInput(prompt, 1, 2);
        return (choice == 1) ? "quantity" : "price";
    }

    public static boolean validateSortOrderChoice(String prompt) {
        int choice = validateNumberChoiceInput(prompt, 1, 2);
        return choice == 1;
    }
}

public class Main {
    public static final InventoryManagementSystem ims = new InventoryManagementSystem();
    public static Scanner input = new Scanner(System.in);

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

    public static void displayAllItems() {
        List<Item> allItems = ims.getAllItems();
        if (allItems.isEmpty()) {
            printNoItemMessage("There are no items in the system to display.");
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
        if (allItems.isEmpty()) {
            printNoItemMessage("There are no items in the system to display.");
            return;
        }
        String category = Validators.validateStringInput("Input Category(Clothing/Electronics/Entertainment): ");
        if (!ims.isValidCategory(category)) {
            System.out.printf("Category '%s' does not exist!\n", category);
            return;
        }
        List<Item> categoryItems = ims.getItemsByCategory(category);
        if (categoryItems.isEmpty()) {
            printNoItemMessage(String.format("There are no %s items in the system.", category));
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
        if (lowQuantityItems.isEmpty()) {
            printNoItemMessage("There are no low quantity items in the system to display.");
            return;
        }
        System.out.println("Low Quantity Items");
        printTableHeader(true);
        for (Item lowQuanItem : lowQuantityItems) {
            System.out.println(lowQuanItem.toDisplayFormat(true));
        }
    }

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
        String category = Validators.validateStringInput("Input Category(Clothing/Electronics/Entertainment): ");
        if (!ims.isValidCategory(category)) {
            System.out.printf("Category '%s' does not exist!\n", category);
            return;
        }
        boolean canAddItem = false;
        do {
            ID = Validators.validateIDInput(category);
            if (ims.findDuplicateItem(ID)) {
                System.out.println("Item with that ID already exists. Try Again.");
                continue;
            }
            canAddItem = true;
        } while (!canAddItem); // TODO UPDATE VALIDATOR FOR ID
        name = Validators.validateStringInput("Input Name: ");
        quantity = Validators.validateQuantityInput("Input Quantity: ", false);
        price = Validators.validateDoubleInput("Input Price: ");
        Item item;
        switch (category) {
            case "clothing":
                item = new Clothing(ID, name, quantity, price);
                break;
            default:
                return;
        }
        ims.addItem(item);
    }

    public static void updateItem() {
        List<Item> allItems = ims.getAllItems();
        if (allItems.isEmpty()) {
            printNoItemMessage("There are no items in the system to update.");
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
        if (allItems.isEmpty()) {
            printNoItemMessage("There are no items in the system to remove.");
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
        if (allItems.isEmpty()) {
            printNoItemMessage("There are no items in the system to search.");
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
        if (allItems.isEmpty()) {
            printNoItemMessage("There are no items in the system to sort.");
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
}
