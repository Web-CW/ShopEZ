package ca.bcit.shopez;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ItemList {
    private ArrayList<Item> itemList;

    public ItemList(){
        itemList = new ArrayList<>();
    }

    public int getSize(){
        return this.itemList.size();
    }

    private Double[] sortProductsPriceInAscendingOrder(){
        Double[] productsPrice = new Double[this.itemList.size()];
        for (int index = 0; index < productsPrice.length; index++){
            productsPrice[index] = this.itemList.get(index).getPrice();
        }
        Arrays.sort(productsPrice);
        return productsPrice;
    }

    private String[] sortProductsFromAToZ(){
        String[] productsName = new String[this.itemList.size()];
        for (int index = 0; index < productsName.length; index++){
            productsName[index] = this.itemList.get(index).getItemName();
        }
        Arrays.sort(productsName);
        return productsName;
    }

    private boolean isAddedAlready(Item item, ArrayList<Item> addedItemsList){
        for (Item product : addedItemsList){
            if (product.equals(item)){
                return true;
            }
        }
        return false;
    }

    private Item retrieveItemByPrice(double price, ArrayList<Item> addedItemsList){
        Item item = null;
        for (Item product : this.itemList){
            if (Double.compare(product.getPrice(), price) == 0 &&
                    !isAddedAlready(product, addedItemsList)){
                item = product;
                break;
            }
        }
        return item;
    }

    private Item retrieveItemByName(String name){
        Item item = null;
        for (Item product : this.itemList){
            if (product.getItemName().equals(name)){
                item = product;
                break;
            }
        }
        return item;
    }

    public void sortInAscendingOrder(){
        Double[] productsPrice = this.sortProductsPriceInAscendingOrder();
        ArrayList<Item> sortedItemsList = new ArrayList<>();
        for (Double price : productsPrice) {
            sortedItemsList.add(this.retrieveItemByPrice(price, sortedItemsList));
        }
        this.itemList = sortedItemsList;
    }

    public void sortInDescendingOrder(){
        Double[] productsPrice = this.sortProductsPriceInAscendingOrder();
        Arrays.sort(productsPrice, Collections.reverseOrder());
        ArrayList<Item> sortedItemsList = new ArrayList<>();
        for (Double price : productsPrice) {
            sortedItemsList.add(this.retrieveItemByPrice(price, sortedItemsList));
        }
        this.itemList = sortedItemsList;

    }

    public void sortInAlphabeticalOrder(){
        String[] productsName = this.sortProductsFromAToZ();
        ArrayList<Item> sortedItemsList = new ArrayList<>();
        for (String name : productsName) {
            sortedItemsList.add(this.retrieveItemByName(name));
        }
        this.itemList = sortedItemsList;
    }

    public void sortInReverseAlphabeticalOrder(){
        String[] productsName = this.sortProductsFromAToZ();
        Arrays.sort(productsName, Collections.reverseOrder());
        ArrayList<Item> sortedItemsList = new ArrayList<>();
        for (String name : productsName) {
            sortedItemsList.add(this.retrieveItemByName(name));
        }
        this.itemList = sortedItemsList;
    }

    public Item getItemByIndex(int index){
        return this.itemList.get(index);
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }
}
