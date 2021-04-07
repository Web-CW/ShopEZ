package ca.bcit.shopez;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.concurrent.Executor;


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
        String text = "";

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tv.setText(text);
        }

        @Override
            public Void doInBackground(Void... voids) {
                try {
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
                    System.out.println(data);

//                    text = data.text();

                    int size = data.size();
//                    System.out.println(size);

                    int count = 0;
                    for (Element item: data) {
                        String productName = item.getElementsByClass("text-dark text-truncate_3").text();
                        String productURL =  item.getElementsByClass("d-block mb-0 pq-hdr-product_price line-height").text();
//                        System.out.println(productPrice);

                        text += productName + " " + productURL + "\n\n";
//                        text += productPrice + "\n\n";
                        count++;
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