package ca.bcit.shopez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class ProductSearchActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);

        tv = findViewById(R.id.display_text);
    }

    public void searchAmazonOnClick(View view) {
        new Content().execute();

//        try {
//
//            String url = "https://en.wikipedia.org/wiki/Main_Page";
//            Document doc = Jsoup.connect(url).
//                    timeout(6000).get();
//            Elements navigationMenu = doc.select("#mw-navigation");
//            System.out.println(navigationMenu);
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
    }

        public class Content extends AsyncTask<Void, Void, Void> {
            String text;

            @Override
            public Void doInBackground(Void... voids) {
                try {
                    String url = "https://en.m.wikipedia.org/wiki/Main_Page";

                    Document doc = Jsoup.connect(url).get();

                    //text = doc.text();

                    Elements data = doc.select("#mp-welcome");

                    System.out.println("#########################################");
                    System.out.println(data);

                    text = data.text();

//                    int size = data.size();


//                    for (int i = 0; i < size; i++) {
//                        text = data.select("div.mp-welcome").text();
//                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            tv.setText(text);
        }
    }

}