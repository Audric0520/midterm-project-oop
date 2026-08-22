public class Entertainment extends Item {
    public Entertainment(String itemID, String name, int quantity, double price) {
        super(itemID, name, quantity, price);
    }

    @Override
    public String getCategory() {
        return "Entertainment";
    }

    @Override
    public String getCategoryIdPrefix() {
        return "EN";
    }
}
