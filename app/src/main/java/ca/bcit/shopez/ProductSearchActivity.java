package ca.bcit.shopez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class ProductSearchActivity extends AppCompatActivity {
    TextView tv;
    EditText searchTextField;
    String userSearchedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);

        tv = findViewById(R.id.display_text);
        searchTextField = findViewById(R.id.search_item_name_edit_text);
    }

    public void searchAmazonOnClick(View view) {
        userSearchedText = searchTextField.getText().toString();
        new Content().execute();
    }

        public class Content extends AsyncTask<Void, Void, Void> {
            String text;

            @Override
            public Void doInBackground(Void... voids) {
                try {
                    String name1 = userSearchedText.replace(" ", "-");
                    String name2 = userSearchedText.replace(" ", "+");

                    String url = String.format("https://www.amazon.ca/%s/s?k%s", name1, name2);

                    Document doc = Jsoup.connect("https://www.amazon.ca/iphone-x/s?k=iphone+x").get();

                    text = doc.text();

                    Elements data = doc.getElementsByClass("s-main-slot s-result-list s-search-results sg-row");

//                    Elements data = doc.select("#search");

                    System.out.println("#########################################");
                    System.out.println(data);

                    //text = data.text();

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