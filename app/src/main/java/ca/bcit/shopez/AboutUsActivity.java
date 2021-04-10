package ca.bcit.shopez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.getMenu().findItem(R.id.nav_about).setChecked(true);
    }
}