public class Electronics extends Item {
    public Electronics(String itemID, String name, int quantity, double price) {
        super(itemID, name, quantity, price);
    }

    @Override
    public String getCategory() {
        return "Electronics";
    }

    @Override
    public String getCategoryIdPrefix() {
        return "EL";
    }
}
