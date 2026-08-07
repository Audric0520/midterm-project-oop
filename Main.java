import java.util.Scanner;

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
                System.out.println("Invalid Input. Only input integers without leading zeros. Try Again.");
                continue;
            }
            try {
                number = Integer.parseInt(inputNumber);
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
}

public class Main {
    public static final InventoryManagementSystem ims = new InventoryManagementSystem();
    public static Scanner input = new Scanner(System.in);

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
            int choice = Validators.validateNumberChoiceInput("Choice: ", 1, 9);

            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 5:
                    ims.displayAllItems();
                    break;
                case 9:
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
        String choice = Validators.validateStringInput("Categories: Clothing,Electronics,Entertainment\nChoice: ");
        boolean canAddItem = false;
        do {
            ID = Validators.validateStringInput("Input ID: ");
            if (ims.findDuplicateItem(ID)) {
                System.out.println("Item with that ID already exists. Try Again.");
                continue;
            }
            canAddItem = true;
        } while (!canAddItem); // TODO UPDATE VALIDATOR FOR ID
        name = Validators.validateStringInput("Input Name: ");
        quantity = Validators.validateIntInput("Input Quantity: ");
        price = Validators.validateDoubleInput("Input Price: ");
        Item item;
        switch (choice) {
            case "clothing":
                item = new Clothing(ID, name, quantity, price);
                break;
            default:
                return;
        }
        ims.addItem(item);
    }
}
