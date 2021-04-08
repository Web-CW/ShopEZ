package ca.bcit.shopez;

import java.util.ArrayList;

public class ItemList {
    private ArrayList<Item> itemList;

    public ItemList(){
        itemList = new ArrayList<>();
    }

    public int getSize(){
        return this.itemList.size();
    }

    public void sortInAscendingOrder(){
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
