package ca.bcit.shopez;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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
    private String[] itemLinkURL;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public CaptionedItemsAdapter(String[] itemNames, double[] itemPrices, String[] itemImgURL, String[] itemLinkURL) {
        this.itemNames = itemNames;
        this.itemPrices = itemPrices;
        this.itemImgURL = itemImgURL;
        this.itemLinkURL = itemLinkURL;
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
        Glide.with(cardView).load(itemImgURL[position]).apply(new RequestOptions().override(750, 750)).into(imageView);

        TextView itemNameTextView = cardView.findViewById(R.id.item_name);
        itemNameTextView.setTextSize(17);
        itemNameTextView.setText(itemNames[position]);

        TextView itemPriceTextView = cardView.findViewById(R.id.item_price);
        itemPriceTextView.setTextSize(22);
        itemPriceTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String itemPrice = "$" + formatter.format(itemPrices[position]);
        itemPriceTextView.setText(itemPrice);
    }
}
