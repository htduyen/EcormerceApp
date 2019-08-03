package com.thud.myecormerce.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.thud.myecormerce.Fragments.SignInFragment;
import com.thud.myecormerce.R;

public class RegisterActivity extends AppCompatActivity {

    private FrameLayout register_frame_layout;
    public static boolean onResetPass = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_frame_layout = findViewById(R.id.register_frame_layout);
        //Set fragment cho tabview
        setDefaultFragment(new SignInFragment());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(onResetPass == true){

                setFragment(new SignInFragment());
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setDefaultFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_right, R.anim.slide_out_left);
        fragmentTransaction.replace(register_frame_layout.getId(), fragment);
        fragmentTransaction.commit();
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_left, R.anim.slide_out_right);
        fragmentTransaction.replace(register_frame_layout.getId(), fragment);
        fragmentTransaction.commit();
    }
}
