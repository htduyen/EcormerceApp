package com.thud.myecormerce.View;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.thud.myecormerce.Adapter.ProductDetailAdapter;
import com.thud.myecormerce.Adapter.ProductImageAdapter;
import com.thud.myecormerce.R;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    private ViewPager productImageViewPager;
    private TabLayout viewPagerIndicator;

    private ViewPager product_descr_viewpager;
    private TabLayout product_content_tab;
    //Rating layout
    private LinearLayout rating;
    //Rating layout

    private Button btn_buy_now;

    private FloatingActionButton add_wishlist;
    private boolean ADDED_WISHLIST = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImageViewPager = findViewById(R.id.product_img_viewpapeger);
        viewPagerIndicator = findViewById(R.id.view_pager_indicator);
        add_wishlist = findViewById(R.id.floating_add_wishlist);
        btn_buy_now = findViewById(R.id.btn_buy_now);
        product_descr_viewpager = findViewById(R.id.content_viewpager_description_layout);
        product_content_tab = findViewById(R.id.tab_description_layoutout);


        List<Integer> productImages = new ArrayList<>();
        productImages.add(R.drawable.phone1);
        productImages.add(R.drawable.phone3);
        productImages.add(R.drawable.phone3);
        productImages.add(R.drawable.phone4);
        productImages.add(R.drawable.phone5);

        ProductImageAdapter productImageAdapter = new ProductImageAdapter(productImages);
        productImageViewPager.setAdapter(productImageAdapter);
        viewPagerIndicator.setupWithViewPager(productImageViewPager, true);

        add_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ADDED_WISHLIST){
                    ADDED_WISHLIST = false;
                    add_wishlist.setImageTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                }
                else {
                    ADDED_WISHLIST = true;
                    add_wishlist.setImageTintList(getResources().getColorStateList(R.color.colorRed));
                }
            }
        });
        //Mô tả - Description
        product_descr_viewpager.setAdapter(new ProductDetailAdapter(getSupportFragmentManager(), product_content_tab.getTabCount()));
        product_descr_viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(product_content_tab));

        product_content_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                product_descr_viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //Rating
        rating = findViewById(R.id.linear_rating_now);
        for (int x=0; x < rating.getChildCount(); x++){
            final  int  startPosition = x;
            rating.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRating(startPosition);
                }
            });

        }
        //Rating
        btn_buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDelivery = new Intent(ProductDetailActivity.this, DeliveryActivity.class );
                startActivity(intentDelivery);
            }
        });
    }

    private void setRating(int startPosition) {
        for(int x = 0; x < rating.getChildCount(); x++){
            ImageView starbtn = (ImageView) rating.getChildAt(x);
            starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if(x  <= startPosition){
                starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_menu, menu);
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
        }else if(id == R.id.icon_shopping_cart){
            Intent intentCart = new Intent(ProductDetailActivity.this, MainActivity.class);
            MainActivity.SHOW_CART = true;
            startActivity(intentCart);
            return true;
        }
        else if(id == android.R.id.home)
        {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
