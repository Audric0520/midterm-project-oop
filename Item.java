public abstract class Item {
    private String itemID;
    private String name;
    private int quantity;
    private double price;

    public Item(String itemID, String name, int quantity, double price) {
        this.itemID = itemID;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getItemID() {
        return itemID;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0 || quantity > 1000) {
            throw new IllegalArgumentException("Invalid Quantity Input.");
        }
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        if (price < 1.00 || price > 1_000_000.00) {
            throw new IllegalArgumentException("Invalid Price Input.");
        }
        this.price = price;
    }

    public abstract String getCategory();

    public abstract String getCategoryIdPrefix();

    public String toDisplayFormat(boolean includeCategory) {
        String formattedPrice = String.format("P%,.2f", price);
        String formattedName = name.length() > 20 ? name.substring(0, 20) + "..." : name;
        if (includeCategory) {
            return String.format("%-15s %-25s %-,20d %-20s %-20s", itemID, formattedName, quantity, formattedPrice,
                    getCategory());
        }
        return String.format("%-15s %-25s %-20s %-20s", itemID, formattedName, quantity, formattedPrice);
    }
}
