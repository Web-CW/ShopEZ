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

    private AutoCompleteTextView autoCompleteTextViewQuestion1;
    private TextInputLayout autoCompleteTextViewMainQuestion1;
    private AutoCompleteTextView autoCompleteTextViewQuestion2;
    private TextInputLayout autoCompleteTextViewMainQuestion2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_q);

        LinearLayout layout = findViewById(R.id.faq_layout_id);
        layout.getBackground().setAlpha(225);

        autoCompleteTextViewMainQuestion1 = findViewById(R.id.question_1);
        autoCompleteTextViewQuestion1 = findViewById(R.id.question_1_ans);
        String[] options = {"Canada Computer Parts", "Memory Express", "Newegg"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.option_item, options) {
            @Override
            public boolean isEnabled(int position) {
                return false;
            }
        };
        autoCompleteTextViewQuestion1.setText(autoCompleteTextViewQuestion1.getText().toString());
        autoCompleteTextViewQuestion1.setAdapter(arrayAdapter);

        autoCompleteTextViewMainQuestion2 = findViewById(R.id.question_2);
        autoCompleteTextViewQuestion2 = findViewById(R.id.question_2_ans);

        String[] answerQuestion2 = {"Some supported websites are not accessible", "outside of Canada."};
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, R.layout.long_answer, answerQuestion2) {
            @Override
            public boolean isEnabled(int position) {
                return false;
            }
        };
        autoCompleteTextViewQuestion2.setText(autoCompleteTextViewQuestion2.getText().toString());
        autoCompleteTextViewQuestion2.setAdapter(arrayAdapter2);

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