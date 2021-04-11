package ca.bcit.shopez;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class FAQActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AutoCompleteTextView autoCompleteTextView;
    private TextInputLayout autoCompleteTextViewMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_q);

        LinearLayout layout = findViewById(R.id.faq_layout_id);
        layout.getBackground().setAlpha(225);

        autoCompleteTextViewMain = findViewById(R.id.question_1);
        autoCompleteTextView = findViewById(R.id.question_1_ans);
        String[] options = {"Canada Computer Parts", "Memory Express", "Newegg"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.option_item, options) {
            @Override
            public boolean isEnabled(int position) {
                return false;
            }
        };
        autoCompleteTextView.setText(autoCompleteTextView.getText().toString());
        autoCompleteTextView.setAdapter(arrayAdapter);

        Toolbar toolbar = findViewById(R.id.faq_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.faq_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.faq_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_faq).setChecked(true);
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
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(getApplicationContext(), SignInActivity.class);
                break;
            case R.id.nav_about:
                intent = new Intent(this, AboutUsActivity.class);
                break;
            case R.id.nav_faq:
                intent = new Intent(this, FAQActivity.class);
                break;
        }
        startActivity(intent);
        item.setChecked(true);
        DrawerLayout drawer = findViewById(R.id.faq_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.faq_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}