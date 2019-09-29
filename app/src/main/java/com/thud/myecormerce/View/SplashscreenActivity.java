package com.thud.myecormerce.View;

import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thud.myecormerce.R;

public class SplashscreenActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        firebaseAuth = FirebaseAuth.getInstance();
        //SystemClock.sleep(500);

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

            //Toast.makeText(this, "You have account", Toast.LENGTH_SHORT).show();
            FirebaseFirestore .getInstance().collection("USERS").document(currentUser.getUid()).update("Last seen", FieldValue.serverTimestamp())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                String error = task.getException().getMessage();
                                Toast.makeText(SplashscreenActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }
}
