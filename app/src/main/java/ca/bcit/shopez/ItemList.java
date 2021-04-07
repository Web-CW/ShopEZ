package ca.bcit.shopez;

import java.util.ArrayList;

public class ItemList {
    ArrayList<Item> itemList = new ArrayList<Item>();

    public ArrayList<Item> getItemsByPriceRange(double lowerBound, double upperBound) {
        ArrayList<Item> foundItems= new ArrayList<Item>();
        for (int i = 0; i < itemList.size(); i++) {
            Item targetItem = itemList.get(i);
            if (targetItem.getPrice() >= lowerBound && targetItem.getPrice() <= upperBound) {
                foundItems.add(targetItem);
            }
        }
        return foundItems;
    }

    public ArrayList<Item> getItemsByName(String name) {
        ArrayList<Item> foundItems= new ArrayList<Item>();
        for (int i = 0; i < itemList.size(); i++) {
            Item targetItem = itemList.get(i);
            if (targetItem.getItemName().toLowerCase().contains(name.toLowerCase().trim())) {
                foundItems.add(targetItem);
            }
        }
        return foundItems;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }
}
