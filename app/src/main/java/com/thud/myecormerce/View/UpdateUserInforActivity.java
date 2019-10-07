package com.thud.myecormerce.View;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.thud.myecormerce.Fragments.ChangePasswordFragment;
import com.thud.myecormerce.Fragments.UserInforFragment;
import com.thud.myecormerce.R;

public class UpdateUserInforActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    private UserInforFragment userInforFragment;
    private ChangePasswordFragment changePasswordFragment;
    private String name, email, profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_infor);

        tabLayout = findViewById(R.id.tab_layout_updateuserinfor);
        frameLayout = findViewById(R.id.framelayout_infor);
        name = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
        profile = getIntent().getStringExtra("profile");

        userInforFragment = new UserInforFragment();
        changePasswordFragment =new ChangePasswordFragment();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    setFragment(userInforFragment, true);
                }
                if(tab.getPosition() == 1){
                    setFragment(changePasswordFragment, false);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.getTabAt(0).select();
        setFragment(userInforFragment, true);
    }

    private void setFragment(Fragment fragment, boolean setBundle) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if(setBundle) {
            Bundle bundle = new Bundle();
            bundle.putString("username", name);
            bundle.putString("email", email);
            bundle.putString("profile", profile);
            fragment.setArguments(bundle);
        }else {
            Bundle bundle = new Bundle();
            bundle.putString("email", email);
            fragment.setArguments(bundle);
        }
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();

    }
}
