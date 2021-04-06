package ca.bcit.shopez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void products_activity_on_click(View view) {
        Intent intent = new Intent(this, ProductSearchActivity.class);
        startActivity(intent);
    }
}