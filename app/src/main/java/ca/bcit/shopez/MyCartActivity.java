package ca.bcit.shopez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyCartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tv;
    private Button signInButton;
    private RecyclerView itemRecycler;

    private ItemList itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        NavigationView navigationView = findViewById(R.id.nav_cart_view);

        tv = findViewById(R.id.cart_activity_title_text_view);
        tv.setText("Please sign into your account to view your cart!");
        signInButton = findViewById(R.id.cart_activity_login_button);
        itemRecycler = findViewById(R.id.cart_item_recycler);
        signInButton.setVisibility(View.VISIBLE);

        itemList = new ItemList();

        ArrayList<Item> productsList = new ArrayList<>();
        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            signInButton.setVisibility(View.GONE);
            tv.setText("My Cart");

            DatabaseReference databaseItems = FirebaseDatabase.getInstance().getReference().child("users").child(fAuth.getUid()).child("item");

            databaseItems.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    productsList.clear();
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        String productName = snapshot.child("itemName").getValue().toString();
                        String productPrice = snapshot.child("price").getValue().toString();
                        String productImgURL = snapshot.child("imgURL").getValue().toString();
                        String vendorLogoURL = snapshot.child("vendorLogoURL").getValue().toString();

                        Item item = new Item(productName, Double.parseDouble(productPrice), productImgURL, vendorLogoURL);
                        productsList.add(item);
                    }
                    itemList.setItemList(productsList);
                    bindDataToAdapter();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
//            Task setValueTask = databaseItems.child("users").child(fAuth.getUid()).child("item").child(itemName).setValue(itemAddedToCart);
        }

        Toolbar toolbar = findViewById(R.id.cart_toolbar);
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
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_view_cart).setChecked(true);
    }

    public void goToSignInOnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(intent);
    }

    public void bindDataToAdapter() {
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

        CaptionedCartItemsAdapter adapter = new CaptionedCartItemsAdapter(itemNames, itemPrices, itemImgURL, itemLinkURL);
        itemRecycler.setAdapter(adapter);

        GridLayoutManager lm = new GridLayoutManager(MyCartActivity.this, 1);
        itemRecycler.setLayoutManager(lm);
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
                intent = new Intent(getApplicationContext(), MyCartActivity.class);
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