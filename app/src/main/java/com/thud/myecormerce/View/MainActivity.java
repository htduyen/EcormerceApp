package com.thud.myecormerce.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thud.myecormerce.Fragments.CartFragment;
import com.thud.myecormerce.Fragments.HomeFragment;
import com.thud.myecormerce.Fragments.MyAccountFragment;
import com.thud.myecormerce.Fragments.MyOrderFragment;
import com.thud.myecormerce.Fragments.MyRewardFragment;
import com.thud.myecormerce.Fragments.MyWishlistFragment;
import com.thud.myecormerce.Fragments.SignInFragment;
import com.thud.myecormerce.Fragments.SignUpFragment;
import com.thud.myecormerce.Presenter.DbQueries;
import com.thud.myecormerce.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT = 1;
    private static final int MYORDER_FRAGMENT = 2;
    private static final int MYWISHLIST_FRAGMENT = 3;
    private static final int MYREWARD_FRAGMENT = 4;
    private static final int MYACCOUNT_FRAGMENT = 5;
    public static Boolean SHOW_CART = false;
    public static Activity mainActivity;
    public static  boolean resetMainActivity = false;

    private TextView badget_count;
    private TextView notify_count;
    private int currentFragment = -1;
    private NavigationView navigationView;
    private ImageView actionBar_logo;
    private FrameLayout frameLayout;
    private Window window;
    private Toolbar toolbar;
    private Dialog signin_dialog;
    private int scrollFlat;
    private AppBarLayout.LayoutParams params;
    public static DrawerLayout drawer;

    private FirebaseUser currentUser;
    private CircleImageView imv_profile_nav;
    private TextView fullname_nav, email_nav;
    private ImageView icon_add_profile_nav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        actionBar_logo = findViewById(R.id.actionbar_logo);
        setSupportActionBar(toolbar);

        params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        scrollFlat = params.getScrollFlags();

        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        frameLayout = findViewById(R.id.main_framelayout);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        imv_profile_nav = navigationView.getHeaderView(0).findViewById(R.id.imv_circle_user_profile_navdrawer);
        icon_add_profile_nav = navigationView.getHeaderView(0).findViewById(R.id.imv_add_profile_icon_navdrawer);
        fullname_nav = navigationView.getHeaderView(0).findViewById(R.id.txt_username_navdrawer);
        email_nav = navigationView.getHeaderView(0).findViewById(R.id.txt_email_navdrawer);


        if(SHOW_CART){
            mainActivity = this;
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
        signin_dialog = new Dialog(MainActivity.this);
        signin_dialog.setContentView(R.layout.signin_dialog);
        signin_dialog.setCancelable(true);
        signin_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final Intent intentSignInUp = new Intent(MainActivity.this, RegisterActivity.class);

        Button btn_signin = signin_dialog.findViewById(R.id.btn_signin_dialog);
        Button btn_signup =  signin_dialog.findViewById(R.id.btn_signup_dialog);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInFragment.disibleCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                signin_dialog.dismiss();
                RegisterActivity.signupfragment = false;
                startActivity(intentSignInUp);
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInFragment.disibleCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                signin_dialog.dismiss();
                RegisterActivity.signupfragment = true;
                startActivity(intentSignInUp);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(false);
        }
        else {
            DbQueries.checkNotification(false, notify_count);
            if(DbQueries.email == null) {
                FirebaseFirestore.getInstance().collection("USERS").document(currentUser.getUid())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DbQueries.fullname = task.getResult().getString("username");
                            DbQueries.email = task.getResult().getString("email");
                            DbQueries.profile = task.getResult().getString("profile");

                            // Log.d("fullname:", DbQueries.fullname + " - " + DbQueries.email+ " - " + DbQueries.profile);

                            fullname_nav.setText(DbQueries.fullname);
                            email_nav.setText(DbQueries.email);
                            if (DbQueries.profile.equals("")) {
                                icon_add_profile_nav.setVisibility(View.VISIBLE);
                            } else {
                                icon_add_profile_nav.setVisibility(View.INVISIBLE);
                                Glide.with(MainActivity.this).load(DbQueries.profile).apply(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground)).into(imv_profile_nav);
                            }

                        } else {
                            Toast.makeText(MainActivity.this, "Loi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else {
                fullname_nav.setText(DbQueries.fullname);
                email_nav.setText(DbQueries.email);
                if (DbQueries.profile.equals("")) {
                    icon_add_profile_nav.setVisibility(View.VISIBLE);
                    imv_profile_nav.setImageResource(R.drawable.ic_launcher_foreground);
                } else {
                    icon_add_profile_nav.setVisibility(View.INVISIBLE);
                    Glide.with(MainActivity.this).load(DbQueries.profile).apply(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground)).into(imv_profile_nav);
                }
            }
            navigationView.getMenu().getItem(navigationView.getMenu().size() -1).setEnabled(true);
        }
        if(resetMainActivity){
            actionBar_logo.setVisibility(View.VISIBLE);
            resetMainActivity = false;
            setFragment(new HomeFragment(), HOME_FRAGMENT);
            navigationView.getMenu().getItem(0).setChecked(true);
        }
        invalidateOptionsMenu();
    }

    @Override
    protected void onPause() {
        super.onPause();
        DbQueries.checkNotification(true, null);
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
                        mainActivity = null;
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

            MenuItem cartItem = menu.findItem(R.id.icon_shopping_cart);

            cartItem.setActionView(R.layout.badge_layout);
            ImageView cart_icon = cartItem.getActionView().findViewById(R.id.imv_badge_icon_cart);
            cart_icon.setImageResource(R.drawable.shopping_cart);
            badget_count = cartItem.getActionView().findViewById(R.id.txt_badge_count);
                if(currentUser != null) {

                    if (DbQueries.cartlist.size() == 0) {
                        DbQueries.loadCartList(MainActivity.this, new Dialog(MainActivity.this), false, badget_count, new TextView(MainActivity.this));
                    }
                    else {
                        badget_count.setVisibility(View.VISIBLE);
                        if(DbQueries.cartlist.size() < 99){
                            badget_count.setText(String.valueOf(DbQueries.cartlist.size()));
                        }
                        else {
                            badget_count.setText("99+");
                        }
                    }
                }
                cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(currentUser == null) {
                            signin_dialog.show();
                        }
                        else {
                            toFragment("Giỏ hàng", new CartFragment(), CART_FRAGMENT);
                        }
                    }
                });
            MenuItem notificationItem = menu.findItem(R.id.icon_notification);
            notificationItem.setActionView(R.layout.badge_layout);
            ImageView notification_icon = notificationItem.getActionView().findViewById(R.id.imv_badge_icon_cart);
            notification_icon.setImageResource(R.drawable.notification_bell_main);
            notify_count = notificationItem.getActionView().findViewById(R.id.txt_badge_count);
            if(currentUser != null){
                DbQueries.checkNotification(false,notify_count);
            }
            notificationItem.getActionView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentNotification = new Intent(MainActivity.this, NotificationActivity.class);
                    startActivity(intentNotification);
                }
            });
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
            Intent intentSearch =new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intentSearch);
            return true;
        }
        else if (id == R.id.icon_notification) {
            Intent intentNotification = new Intent(MainActivity.this, NotificationActivity.class);
            startActivity(intentNotification);
            return true;
        }
        else if (id == R.id.icon_shopping_cart) {
            if(currentUser == null) {
                signin_dialog.show();
            }
            else {
                toFragment("Giỏ hàng", new CartFragment(), CART_FRAGMENT);
            }
            return true;
        }else if(id == android.R.id.home){
            mainActivity = null;
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
        if(numFragment == CART_FRAGMENT || SHOW_CART){
            navigationView.getMenu().getItem(3).setChecked(true);
            params.setScrollFlags(0);
        }
        else {
            params.setScrollFlags(scrollFlat);
        }
        if(numFragment == MYORDER_FRAGMENT) {
            navigationView.getMenu().getItem(1).setChecked(true);
        }
    }

    MenuItem menuItem;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        // Handle navigation view item clicks here.
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        menuItem = item;
        if(currentUser != null) {
            drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    int id = menuItem.getItemId();
                    if (id == R.id.nav_home) {
                        actionBar_logo.setVisibility(View.VISIBLE);
                        invalidateOptionsMenu();
                        setFragment(new HomeFragment(), HOME_FRAGMENT);
                    } else if (id == R.id.nav_my_order) {
                        toFragment("Đơn mua", new MyOrderFragment(), MYORDER_FRAGMENT);
                    } else if (id == R.id.nav_my_reward) {
                        toFragment("Danh sách yêu thích", new MyRewardFragment(), MYREWARD_FRAGMENT);
                    } else if (id == R.id.nav_my_cart) {
                        toFragment("Giỏ hàng", new CartFragment(), CART_FRAGMENT);
                    } else if (id == R.id.nav_my_account) {
                        toFragment("Hồ Sơ", new MyAccountFragment(), MYACCOUNT_FRAGMENT);
                    } else if (id == R.id.nav_my_wishlist) {
                        toFragment("Danh sách mong muốn", new MyWishlistFragment(), MYWISHLIST_FRAGMENT);
                    } else if (id == R.id.nav_logout) {
                        FirebaseAuth.getInstance().signOut();
                        DbQueries.clearData();
                        Intent intentRegis = new Intent(MainActivity.this, RegisterActivity.class);
                        startActivity(intentRegis);
                        finish();
                    }
                    drawer.removeDrawerListener(this);
                }
            });
            return true;
        }else {
            signin_dialog.show();
            return false;
        }
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
