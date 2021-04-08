package ca.bcit.shopez;

public class Item {
    private String itemName;
    private String price;
    private String itemURL;
    private String imgURL;

    public Item(String itemName, String price, String imgURL, String itemURL) {
        this.itemName = itemName;
        this.price = price;
        this.itemURL = itemURL;
        this.imgURL = imgURL;
    }

    public Item(String itemName, String price) {
        this.itemName = itemName;
        this.price = price;

        // Set dummy URL pics here later
        this.itemURL = "";
        this.imgURL = "";
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItemURL() {
        return itemURL;
    }

    public void setItemURL(String itemURL) {
        this.itemURL = itemURL;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
