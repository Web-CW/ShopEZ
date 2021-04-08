package ca.bcit.shopez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class ProductSearchActivity extends AppCompatActivity {
    private EditText searchTextField;
    private String userSearchedText;
    private ArrayList<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);
        searchTextField = findViewById(R.id.search_item_name_edit_text);
        itemList = new ArrayList<>();
    }

    public void onSearch(View view) {
        userSearchedText = searchTextField.getText().toString();
        new Content().execute();
    }

    private void searchProductsFromMemoryExpress(){
        try {
            String productSearchedName = userSearchedText.replace(" ", "%20");
            String connectionString = String.format("https://www.memoryexpress.com/Search/Products?Search=%s", productSearchedName);
            Document memoryExpress = Jsoup.connect(connectionString).get();
            Elements data = memoryExpress.select("div.c-shca-icon-item");
            int productCounter = 0;
            for (Element product: data) {
                String productName = product.select("div.c-shca-icon-item__body-name > a").text();
                String productPrice = product.select("div.c-shca-icon-item__summary-list > span").text();
                String productImgURL = product.select("div.c-shca-icon-item__body-image > a")
                        .select("img")
                        .attr("src");
                Item item = new Item(productName, productPrice, productImgURL, "");
                itemList.add(item);
                productCounter++;
                if (productCounter == 10)
                    break;
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void searchProductsFromNewEgg(){
        try {
            String productSearchedName = userSearchedText.replace(" ", "+");
            String connectionString = String.format("https://www.newegg.ca/p/pl?d=%s", productSearchedName);
            Document newegg = Jsoup.connect(connectionString).get();
            Elements data = newegg.select("div.item-cell");
            int productCounter = 0;
            for (Element product: data) {
                String productName = product.select("div.item-info > a").text();
                String productPrice = "$" + product.select("li.price-current > strong").text();
                String productImgURL = product.select("div.item-container > a")
                        .select("img")
                        .attr("src");
                Item item = new Item(productName, productPrice, productImgURL, "");
                itemList.add(item);
                productCounter++;
                if (productCounter == 10)
                    break;
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void searchProductsFromCanadaComputers(){
        try {
            String name1 = userSearchedText.replace(" ", "+");
            String canadaComputersURL = String.format("https://www.canadacomputers.com/search/results_details.php?language=en&keywords=%s", name1);
            System.out.println(canadaComputersURL);
            Document doc = Jsoup.connect(canadaComputersURL).get();
            Elements data = doc.getElementsByClass("col-xl-3 col-lg-4 col-6 mt-0_5 px-0_5 toggleBox mb-1");

            System.out.println("#########################################");
            System.out.println(data);

            int count = 0;
            for (Element item: data) {
                String productName = item.getElementsByClass("text-dark text-truncate_3").text();
                String productPrice =  item.getElementsByClass("d-block mb-0 pq-hdr-product_price line-height").text();
                String productImgURL = item.getElementsByClass("pq-img-manu_logo align-self-center").attr("src");
                System.out.println(productImgURL);
                Item itemFound = new Item(productName, productPrice, productImgURL, "");
                itemList.add(itemFound);
                count++;
                if (count == 10)
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class Content extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            RecyclerView itemRecycler = findViewById(R.id.item_recycler);

            String[] itemNames = new String[itemList.size()];
            String[] itemPrices = new String[itemList.size()];
            String[] itemImgURL = new String[itemList.size()];
            String[] itemLinkURL = new String[itemList.size()];

            for (int i = 0; i < itemList.size(); i++) {
                itemNames[i] = itemList.get(i).getItemName();
                itemPrices[i] = itemList.get(i).getPrice();
                itemImgURL[i] = itemList.get(i).getImgURL();
                itemLinkURL[i] = itemList.get(i).getItemURL();
            }

            CaptionedItemsAdapter adapter = new CaptionedItemsAdapter(itemNames, itemPrices, itemImgURL, itemLinkURL);
            itemRecycler.setAdapter(adapter);

            GridLayoutManager lm = new GridLayoutManager(ProductSearchActivity.this, 1);
            itemRecycler.setLayoutManager(lm);

        }

        @Override
        public Void doInBackground(Void... voids) {
            itemList.clear();
            searchProductsFromCanadaComputers();
            searchProductsFromMemoryExpress();
            searchProductsFromNewEgg();
            return null;
        }
    }

}