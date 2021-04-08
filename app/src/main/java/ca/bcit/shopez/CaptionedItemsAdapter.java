package ca.bcit.shopez;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CaptionedItemsAdapter extends RecyclerView.Adapter<CaptionedItemsAdapter.ViewHolder>{
    private String[] itemNames;
    private double[] itemPrices;
    private String[] itemImgURL;
    private String[] vendorLogoURL;

    DatabaseReference databaseItems;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public CaptionedItemsAdapter(String[] itemNames, double[] itemPrices, String[] itemImgURL, String[] vendorLogoURL) {
        this.itemNames = itemNames;
        this.itemPrices = itemPrices;
        this.itemImgURL = itemImgURL;
        this.vendorLogoURL = vendorLogoURL;
    }

    @Override
    public int getItemCount() {
        return itemNames.length;
    }

    @Override
    public CaptionedItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_item, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardView;
        cardView.setBackgroundResource(R.drawable.custom_cardview_background);
        ImageView imageView = cardView.findViewById(R.id.item_image);
        Glide.with(cardView).load(itemImgURL[position]).apply(new RequestOptions()
                .override(600, 600)).into(imageView);

        ImageView vendorLogoImageView = cardView.findViewById(R.id.vendor_logo);
        Glide.with(cardView).load(vendorLogoURL[position]).apply(RequestOptions
                .circleCropTransform().override(127, 127)).into(vendorLogoImageView);

        TextView itemNameTextView = cardView.findViewById(R.id.item_name);
        itemNameTextView.setTextSize(17);
        itemNameTextView.setText(itemNames[position]);

        TextView itemPriceTextView = cardView.findViewById(R.id.item_price);
        itemPriceTextView.setTextSize(22);
        itemPriceTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String itemPrice = "$" + formatter.format(itemPrices[position]);
        itemPriceTextView.setText(itemPrice);

        Button addToCartButton = cardView.findViewById(R.id.add_to_cart_button);

        Item item = new Item(itemNames[position], itemPrices[position], itemImgURL[position], vendorLogoURL[position]);

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(item);
            }
        });
    }

    private void addItem(Item item) {
        databaseItems = FirebaseDatabase.getInstance().getReference();

        String itemName = item.getItemName().trim();
        double price = item.getPrice();
        String imgURL = item.getImgURL();
        String itemURL = item.getVendorLogoURL();

        String id = databaseItems.push().getKey();
        Item itemAddedToCart = new Item(itemName, price, imgURL, itemURL);

        Task setValueTask = databaseItems.child("item").child(itemName).setValue(itemAddedToCart);

//        setValueTask.addOnSuccessListener(new OnSuccessListener() {
//            @Override
//            public void onSuccess(Object o) {
//                Toast.makeText(ProductSearchActivity.this,"Item added to cart!",Toast.LENGTH_LONG).show();
//            }
//        });
//
//        setValueTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(ProductSearchActivity.this,
//                        "something went wrong.\n" + e.toString(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
