package ca.bcit.shopez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executor;


public class ProductSearchActivity extends AppCompatActivity {
    private TextView tv;
    private EditText searchTextField;
    private String userSearchedText;
    private ArrayList<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);

//        tv = findViewById(R.id.display_text);
        searchTextField = findViewById(R.id.search_item_name_edit_text);

        itemList = new ArrayList<Item>();
    }

    public void searchAmazonOnClick(View view) {
        userSearchedText = searchTextField.getText().toString();
        new Content().execute();
    }

    public class Content extends AsyncTask<Void, Void, Void> {
        String text = "";

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


//            tv.setText(text);
        }

        @Override
            public Void doInBackground(Void... voids) {
                try {
                    itemList.clear();

                    String name1 = userSearchedText.replace(" ", "+");
                    System.out.println(name1);
//                    String name2 = userSearchedText.replace(" ", "+");

                    String canadaComputersURL = String.format("https://www.canadacomputers.com/search/results_details.php?language=en&keywords=%s", name1);
                    System.out.println(canadaComputersURL);

                    Document doc = Jsoup.connect(canadaComputersURL).get();

//                    text = doc.text();

//                    System.out.println(doc.text());

//                    Elements data = doc.getElementsByClass("css-x7wixz epettpn0");
                    Elements data = doc.getElementsByClass("col-xl-3 col-lg-4 col-6 mt-0_5 px-0_5 toggleBox mb-1");

//                    Elements data = doc.select("a[href]");

                    System.out.println("#########################################");
//                    System.out.println(data);

//                    text = data.text();

                    int size = data.size();
//                    System.out.println(size);

                    int count = 0;
                    for (Element item: data) {
                        String productName = item.getElementsByClass("text-dark text-truncate_3").text();
                        String productPrice =  item.getElementsByClass("d-block mb-0 pq-hdr-product_price line-height").text();

                        String productImgURL = item.getElementsByClass("pq-img-manu_logo align-self-center").attr("src");
                        System.out.println(productImgURL);

                        Item itemFound = new Item(productName, productPrice, productImgURL, "");
                        itemList.add(itemFound);
//                        text += productName + " " + productURL + "\n\n";
//                        text += productPrice + "\n\n";

                        count++;
//                        break;
                        if (count == 10)
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
    }

}