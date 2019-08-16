package com.thud.myecormerce.View;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thud.myecormerce.Adapter.ProductDetailAdapter;
import com.thud.myecormerce.Adapter.ProductImageAdapter;
import com.thud.myecormerce.Adapter.RewardAdapter;
import com.thud.myecormerce.Models.RewardModel;
import com.thud.myecormerce.R;

import org.w3c.dom.Text;

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
    //Dialog discount layout
    public static TextView discountTitle;
    public static TextView discountTime;
    public static TextView discountContent;
    private static RecyclerView recyclerViewDiscount;
    private static LinearLayout linearLayoutDiscount;
    //Dialog discount layout

    private Button btn_buy_now;
    private Button btn_discount;

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
        btn_discount = findViewById(R.id.btn_redemption_detail);

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
        ///Dialog discount
        final Dialog dialogDiscount = new Dialog(ProductDetailActivity.this);
        dialogDiscount.setContentView(R.layout.discount_dialog);
        dialogDiscount.setCancelable(true);
        dialogDiscount.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        ImageView imv_togle_recycler = dialogDiscount.findViewById(R.id.imv_togle_discount_dialog);
        recyclerViewDiscount =  dialogDiscount.findViewById(R.id.recyclerView_discount_dialog);
        linearLayoutDiscount = dialogDiscount.findViewById(R.id.linea_selected_discount_dialog);
        //Luu Y
        discountTitle = dialogDiscount.findViewById(R.id.txt_name_reward_reward);
        discountTime = dialogDiscount.findViewById(R.id.txt_time_reward);
        discountContent = dialogDiscount.findViewById(R.id.txt_content_reward);

        TextView txt_origin_price = dialogDiscount.findViewById(R.id.txt_origin_price_dialog);
        TextView txt_discounted_price = dialogDiscount.findViewById(R.id.txt_discounted_price_dialog);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductDetailActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewDiscount.setLayoutManager(linearLayoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("Lễ 30/4 - 1/5", "Từ 29-2/5/2019","Giảm 20% tất cả các sản phẩm."));
        rewardModelList.add(new RewardModel("Lễ Tình Nhân", "Từ 12-14/2/2019","Giảm 30% tất cả các sản phẩm cho các cặp tình nhân."));
        rewardModelList.add(new RewardModel("Lễ Quốc Tế Thiếu Nhi", "Từ 30-1/6/2019","Giảm 10% tất cả các sản phẩm."));
        rewardModelList.add(new RewardModel("Ngày Thương Binh Liệt Sĩ", "Từ 26-28/7/2019","Giảm 50% tất cả các sản phẩm đối với người có công với đất nước, CBNV nhà nước."));
        rewardModelList.add(new RewardModel("Lễ Quốc Khánh", "Từ 1-3/9/2019","Giảm 30% tất cả các sản phẩm."));
        rewardModelList.add(new RewardModel("Ngày Nhà Giáo Việt Nam", "Từ 19-21/11/2019","Giảm 50% tất cả các sản phẩm cho CBNV giáo viên trên cả nước."));

        RewardAdapter discountAdapter = new RewardAdapter(rewardModelList, true);
        recyclerViewDiscount.setAdapter(discountAdapter);
        discountAdapter.notifyDataSetChanged();

        imv_togle_recycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDisCount();
            }
        });



        btn_discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogDiscount.show();
            }
        });
        ///Dialog discount
    }
    public static void showDialogDisCount(){
        if(recyclerViewDiscount.getVisibility() == View.GONE){
            recyclerViewDiscount.setVisibility(View.VISIBLE);
            linearLayoutDiscount.setVisibility(View.GONE);
        }
        else {
            recyclerViewDiscount.setVisibility(View.GONE);
            linearLayoutDiscount.setVisibility(View.VISIBLE);
        }
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
