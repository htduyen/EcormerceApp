package com.thud.myecormerce.View;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.thud.myecormerce.Fragments.CartFragment;
import com.thud.myecormerce.Fragments.HomeFragment;
import com.thud.myecormerce.Fragments.MyAccountFragment;
import com.thud.myecormerce.Fragments.MyOrderFragment;
import com.thud.myecormerce.Fragments.MyRewardFragment;
import com.thud.myecormerce.Fragments.MyWishlistFragment;
import com.thud.myecormerce.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT = 1;
    private static final int MYORDER_FRAGMENT = 2;
    private static final int MYWISHLIST_FRAGMENT = 3;
    private static final int MYREWARD_FRAGMENT = 4;
    private static final int MYACCOUNT_FRAGMENT = 5;
    public static Boolean SHOW_CART = false;

    private int currentFragment = -1;
    private NavigationView navigationView;
    private ImageView actionBar_logo;
    private FrameLayout frameLayout;
    private Window window;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        actionBar_logo = findViewById(R.id.actionbar_logo);
        setSupportActionBar(toolbar);

        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        frameLayout = findViewById(R.id.main_framelayout);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        if(SHOW_CART){
            drawer.setDrawerLockMode(1);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toFragment("Giỏ Hàng", new CartFragment(), -2);
        }
        else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setFragment(new HomeFragment(),HOME_FRAGMENT);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(currentFragment == HOME_FRAGMENT){
                currentFragment = -1;
                super.onBackPressed();
            }
            else {
                if(SHOW_CART){
                    SHOW_CART = false;
                    finish();
                }
                else {
                    actionBar_logo.setVisibility(View.VISIBLE);
                    invalidateOptionsMenu();
                    setFragment(new HomeFragment(), HOME_FRAGMENT);
                    navigationView.getMenu().getItem(0).setChecked(true);

                }
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(currentFragment == HOME_FRAGMENT){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.icon_search) {

            return true;
        }
        else if (id == R.id.icon_notification) {

            return true;
        }
        else if (id == R.id.icon_shopping_cart) {
            toFragment("Giỏ hàng",new CartFragment(),CART_FRAGMENT);
            return true;
        }else if(id == android.R.id.home){
            SHOW_CART = false;
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void toFragment(String title, Fragment fragment, int numFragment) {
        actionBar_logo.setVisibility(View.GONE);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);

        invalidateOptionsMenu();
        setFragment(fragment, numFragment);
        if(numFragment == CART_FRAGMENT){
            navigationView.getMenu().getItem(3).setChecked(true);

        }
        if(numFragment == MYORDER_FRAGMENT) {
            navigationView.getMenu().getItem(1).setChecked(true);
        }
        else
         {

         }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            actionBar_logo.setVisibility(View.VISIBLE);
            invalidateOptionsMenu();
            setFragment(new HomeFragment(), HOME_FRAGMENT);
        } else if (id == R.id.nav_my_order) {
            toFragment("Đơn mua",new MyOrderFragment(),MYORDER_FRAGMENT);
        } else if (id == R.id.nav_my_reward) {
            toFragment("Danh sách yêu thích", new MyRewardFragment(),MYREWARD_FRAGMENT);
        } else if (id == R.id.nav_my_cart) {
            toFragment("Giỏ hàng",new CartFragment(),CART_FRAGMENT);
        } else if (id == R.id.nav_my_account) {
            toFragment("Hồ Sơ",new MyAccountFragment(),MYACCOUNT_FRAGMENT);
        } else if (id == R.id.nav_my_wishlist) {
            toFragment("Danh sách mong muốn",new MyWishlistFragment(),MYWISHLIST_FRAGMENT);
        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment(Fragment fragment, int NumFragment){
        if(NumFragment != currentFragment){
            if(NumFragment == MYREWARD_FRAGMENT){
                window.setStatusBarColor(Color.parseColor("#ca098a"));
                toolbar.setBackgroundColor(Color.parseColor("#ca098a"));
            }
            else {
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }

            currentFragment = NumFragment;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();
        }
        else {

        }
    }
}
