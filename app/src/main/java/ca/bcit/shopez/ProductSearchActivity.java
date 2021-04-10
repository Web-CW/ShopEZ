package ca.bcit.shopez;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class ProductSearchActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private EditText searchTextField;
    private String userSearchedText;
    private ItemList itemList;
    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);
        searchTextField = findViewById(R.id.search_item_name_edit_text);
        itemList = new ItemList();

        LinearLayout layout = findViewById(R.id.layout_id);
        layout.getBackground().setAlpha(225);

        autoCompleteTextView.findViewById(R.id.filter_menu);
        String[] option = {};

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void onSearch(View view) {
        userSearchedText = searchTextField.getText().toString();
        if (userSearchedText.length() == 0) {
            Toast.makeText(getApplicationContext(), "Search field cannot be empty",
                    Toast.LENGTH_LONG).show();
        }
        new Content().execute();
    }

    private ArrayList<Item> searchProductsFromMemoryExpress(){
        ArrayList<Item> productsList = new ArrayList<>();
        try {
            String productSearchedName = userSearchedText.replace(" ", "%20");
            String connectionString = String.format("https://www.memoryexpress.com/Search/Products?Search=%s", productSearchedName);
            Document memoryExpress = Jsoup.connect(connectionString).get();
            Elements data = memoryExpress.select("div.c-shca-icon-item");
            String vendorLogoURL = "https://scontent.fsgn2-3.fna.fbcdn.net/v/t1.18169-9/149495_10150770597476780_1164283623_n.jpg?_nc_cat=108&ccb=1-3&_nc_sid=09cbfe&_nc_ohc=lnqxarK5jVEAX8-5vct&_nc_ht=scontent.fsgn2-3.fna&oh=5faa1281c958ecb08479470b82e45f19&oe=60949D8F";
            int productCounter = 0;
            for (Element product: data) {
                String productName = product.select("div.c-shca-icon-item__body-name > a")
                        .text();
                String productPrice = product.select("div.c-shca-icon-item__summary-list > span")
                        .text()
                        .replaceAll("[+$,]","");
                String productImgURL = product.select("div.c-shca-icon-item__body-image > a")
                        .select("img")
                        .attr("src");
                Item item = new Item(productName, Double.parseDouble(productPrice), productImgURL, vendorLogoURL);
                productsList.add(item);
                productCounter++;
                if (productCounter == 3)
                    break;
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return productsList;
    }

    private ArrayList<Item> searchProductsFromNewEgg(){
        ArrayList<Item> productsList = new ArrayList<>();
        try {
            String productSearchedName = userSearchedText.replace(" ", "+");
            String connectionString = String.format("https://www.newegg.ca/p/pl?d=%s", productSearchedName);
            Document newEgg = Jsoup.connect(connectionString).get();
            Elements data = newEgg.select("div.item-cell");
            String vendorLogoURL = "https://c1.neweggimages.com/WebResource/Themes/Nest/logos/logo_424x210.png";
            int productCounter = 0;
            for (Element product: data) {
                String productName = product.select("div.item-info > a")
                        .text();
                String productPrice = product.select("li.price-current > strong")
                        .text()
                        .concat(product.select("li.price-current > sup").text())
                        .replaceAll("[+$,]","");
                String productImgURL = product.select("div.item-container > a")
                        .select("img")
                        .attr("src");
                Item item = new Item(productName, Double.parseDouble(productPrice), productImgURL, vendorLogoURL);
                productsList.add(item);
                productCounter++;
                if (productCounter == 3)
                    break;
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return productsList;
    }

    private ArrayList<Item> searchProductsFromCanadaComputers(){
        ArrayList<Item> productsList = new ArrayList<>();
        try {
            String name1 = userSearchedText.replace(" ", "+");
            String canadaComputersURL = String.format("https://www.canadacomputers.com/search/results_details.php?language=en&keywords=%s", name1);
            Document doc = Jsoup.connect(canadaComputersURL).get();
            Elements data = doc.getElementsByClass("col-xl-3 col-lg-4 col-6 mt-0_5 px-0_5 toggleBox mb-1");
            String vendorLogoURL = "https://imgcdn.flyers-on-line.com/weekly-flyer/canada-computers/logo-387/canada-computers.jpg?v=1570574981";
            int count = 0;
            System.out.println("###############");
            System.out.println(canadaComputersURL);
            for (Element item: data) {
                String productName = item.getElementsByClass("text-dark text-truncate_3").text();
                String productPrice = "";
                if (!item.getElementsByClass("d-block mb-0 pq-hdr-product_price line-height").text().equals("")) {
                    productPrice =  item.getElementsByClass("d-block mb-0 pq-hdr-product_price line-height")
                            .text()
                            .replaceAll("[+$,]","");
                } else {
                    productPrice =  item.getElementsByClass("text-danger d-block mb-0 pq-hdr-product_price line-height")
                            .text()
                            .replaceAll("[+$,]","");
                }
                String productImgURL = item.getElementsByClass("pq-img-manu_logo align-self-center")
                        .attr("src");
                Item itemFound = new Item(productName, Double.parseDouble(productPrice), productImgURL, vendorLogoURL);
                productsList.add(itemFound);
                count++;
                if (count == 3)
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productsList;
    }

    private void mergeProducts(){
        ArrayList<Item> productsFromCanadaComputers = searchProductsFromCanadaComputers();
        ArrayList<Item> productsFromMemoryExpress = searchProductsFromMemoryExpress();
        ArrayList<Item> productsFromNewEgg = searchProductsFromNewEgg();
        productsFromCanadaComputers.addAll(productsFromMemoryExpress);
        productsFromCanadaComputers.addAll(productsFromNewEgg);
        this.itemList.setItemList(productsFromCanadaComputers);
    }

    public class Content extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            RecyclerView itemRecycler = findViewById(R.id.item_recycler);

            String[] itemNames = new String[itemList.getSize()];
            double[] itemPrices = new double[itemList.getSize()];
            String[] itemImgURL = new String[itemList.getSize()];
            String[] itemLinkURL = new String[itemList.getSize()];

            for (int i = 0; i < itemList.getSize(); i++) {
                itemNames[i] = itemList.getItemByIndex(i).getItemName();
                itemPrices[i] = itemList.getItemByIndex(i).getPrice();
                itemImgURL[i] = itemList.getItemByIndex(i).getImgURL();
                itemLinkURL[i] = itemList.getItemByIndex(i).getVendorLogoURL();
            }

            CaptionedItemsAdapter adapter = new CaptionedItemsAdapter(itemNames, itemPrices, itemImgURL, itemLinkURL);
            itemRecycler.setAdapter(adapter);

            GridLayoutManager lm = new GridLayoutManager(ProductSearchActivity.this, 1);
            itemRecycler.setLayoutManager(lm);
        }

        @Override
        public Void doInBackground(Void... voids) {
            mergeProducts();
            itemList.sortInAscendingOrder();
            return null;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;

        switch(id) {
            case R.id.nav_homepage:
                intent = new Intent(this, ProductSearchActivity.class);
                break;
            case R.id.nav_view_cart:
                intent = new Intent(this, MyCartActivity.class);
                break;
            case R.id.nav_sign_in:
                intent = new Intent(this, SignInActivity.class);
                break;
            case R.id.nav_sign_out:
                FirebaseAuth.getInstance().signOut(); // logout
                intent = new Intent(getApplicationContext(), SignInActivity.class);
                break;
            case R.id.nav_about:
                intent = new Intent(this, AboutUsActivity.class);
                break;
        }

        startActivity(intent);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}