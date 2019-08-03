package com.thud.myecormerce.View;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.thud.myecormerce.R;

public class SplashscreenActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        firebaseAuth = FirebaseAuth.getInstance();
        SystemClock.sleep(500);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser == null) {
            Intent intentRegis = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intentRegis);
            finish();
        }
        else {
            Toast.makeText(this, "You have account", Toast.LENGTH_SHORT).show();
            Intent intentRegis = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intentRegis);
            finish();
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//            finish();
        }
    }
}
