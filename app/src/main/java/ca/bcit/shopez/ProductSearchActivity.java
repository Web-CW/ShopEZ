package ca.bcit.shopez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class ProductSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);
    }

    public static void searchAmazon() {
        try {
//            URL url = new URL("https://www.amazon.ca/iphone-x/s?k=iphone+x");
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");

            String url = "https://ca.yahoo.com/";
            Document doc = Jsoup.connect("https://ca.yahoo.com/").userAgent("Chrome").get();;
            Elements data = doc.select("div.mp-welcome");

            int size = data.size();

            for (int i = 0; i < size; i++) {
                System.out.println(data.select("div.mp-welcome"));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void searchAmazonOnClick(View view) {
        searchAmazon();
    }
}