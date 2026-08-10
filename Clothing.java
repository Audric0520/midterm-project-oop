public class Clothing extends Item {
    public Clothing(String itemID, String name, int quantity, double price) {
        super(itemID, name, quantity, price);
    }

    @Override
    public String getCategory() {
        return "Clothing";
    }
}
