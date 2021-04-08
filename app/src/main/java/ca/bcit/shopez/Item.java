package ca.bcit.shopez;

public class Item {
    private String itemName;
    private double price;
    private String vendorLogoURL;
    private String imgURL;

    public Item(String itemName, double price, String imgURL, String vendorLogoURL) {
        this.itemName = itemName;
        this.price = price;
        this.vendorLogoURL = vendorLogoURL;
        this.imgURL = imgURL;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getVendorLogoURL() {
        return this.vendorLogoURL;
    }

    public void setVendorLogoURL(String vendorLogoURL) {
        this.vendorLogoURL = vendorLogoURL;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
