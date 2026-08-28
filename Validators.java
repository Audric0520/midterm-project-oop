import java.util.Scanner;

public class Validators {
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

    public static double validatePriceInput(String prompt) {
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
            if (number < 1) {
                System.out.println("Invalid Input. Price must be greater than or equal to 1.00. Try Again.");
                continue;
            } else if (number > 1000000) {
                System.out.println("Invalid Input. Price cannot be greater than 1,000,000.00. Try Again.");
                continue;
            }
            isRunning = false;
        }
        return number;
    }

    public static String validateCategoryInput(String prompt) {
        String category = "";
        boolean validCategory = false;
        do {
            category = Validators.validateStringInput(prompt);
            if (!(category.equalsIgnoreCase("clothing") || category.equalsIgnoreCase("electronics")
                    || category.equalsIgnoreCase("entertainment"))) {
                System.out.printf("Category '%s' does not exist!\n", category);
                continue;
            }
            validCategory = true;
        } while (!validCategory);
        return category.toLowerCase();
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

    private static Item createTempItem(String category) {
        switch (category.toLowerCase()) {
            case "clothing":
                return new Clothing("", "", 0, 0);
            case "electronics":
                return new Electronics("", "", 0, 0);
            case "entertainment":
                return new Entertainment("", "", 0, 0);
            default:
                return null;
        }
    }

    public static int validateQuantityInput(String prompt, boolean forUpdating) {
        boolean isRunning = true;
        int number = 0;
        while (isRunning) {
            number = validateIntInput(prompt);
            if (forUpdating && number < 0) {
                System.out.println("Invalid Input. Quantity must be between 0 - 1000. Try Again.");
                continue;
            } else if (!forUpdating && number <= 0) {
                System.out.println("Invalid Input. Quantity must be between 1 - 1000. Try Again.");
                continue;
            } else if (number > 1000) {
                System.out.println("Invalid Input. Quantity cannot be higher than 1000. Try Again.");
                continue;
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