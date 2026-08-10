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

    public abstract String getCategory();

    public String toDisplayFormat() {
        String formattedPrice = String.format("P%.2f", price);
        return String.format("%-20s %-20s %-20s %-20s %-20s", itemID, name, quantity, formattedPrice, getCategory());
    }
}
