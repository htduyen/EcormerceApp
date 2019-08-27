package com.thud.myecormerce.View;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thud.myecormerce.Adapter.ProductDetailAdapter;
import com.thud.myecormerce.Adapter.ProductImageAdapter;
import com.thud.myecormerce.Adapter.RewardAdapter;
import com.thud.myecormerce.Fragments.ProductDescriptionFragment;
import com.thud.myecormerce.Fragments.ProductSpecificationFragment;
import com.thud.myecormerce.Models.ProductSpecificationModel;
import com.thud.myecormerce.Models.RewardModel;
import com.thud.myecormerce.R;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    private ViewPager productImageViewPager;
    private TabLayout viewPagerIndicator;

    private TextView product_title;
    private TextView product_average_rating_star;
    private TextView product_total_rating;
    private TextView product_price;
    private TextView product_cutted_price;
    private ImageView cod;
    private TextView txt_cod;

    private TextView reward_title;
    private TextView reward_body;


    //Rating layout
    private LinearLayout rating;
    private TextView total_rating;
    private TextView total_rating_sum_linear;
    private TextView average_rating;
    private LinearLayout linear_rating;
    private LinearLayout linear_progressbar_rating;

    //Rating layout

    //Dialog discount layout

    public static TextView discountTitle;
    public static TextView discountTime;
    public static TextView discountContent;
    private static RecyclerView recyclerViewDiscount;
    private static LinearLayout linearLayoutDiscount;

    //Dialog discount layout

    //Description
    private ConstraintLayout productDetailTabs;
    private ConstraintLayout productDetail;
    private ViewPager product_descr_viewpager;
    private TabLayout product_content_tab;
    private String productDescription;
    private String productOrtherDetails;
    private TextView txt_descriptionOnlyDetailBody;
    private static List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();
    //Description

    private Button btn_buy_now;
    private Button btn_discount;

    private FloatingActionButton add_wishlist;
    private boolean ADDED_WISHLIST = false;

    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImageViewPager = findViewById(R.id.product_img_viewpapeger);
        viewPagerIndicator = findViewById(R.id.view_pager_indicator);
        product_title = findViewById(R.id.txt_product_name_detail);
        product_average_rating_star = findViewById(R.id.txt_product_rating_detail);
        product_total_rating = findViewById(R.id.txt_total_rating_detail);
        product_price = findViewById(R.id.txt_product_new_price_detail);
        product_cutted_price = findViewById(R.id.txt_old_price_detail);
        cod = findViewById(R.id.imv_cod_full_detail);
        txt_cod = findViewById(R.id.txt_cod_full_detail);

        reward_title = findViewById(R.id.txt_reward_title_rewardlayout);
        reward_body = findViewById(R.id.txt_reward_content_reward);

        add_wishlist = findViewById(R.id.floating_add_wishlist);
        btn_buy_now = findViewById(R.id.btn_buy_now);

        productDetail = findViewById(R.id.product_detail_constrain);
        productDetailTabs = findViewById(R.id.contrain_product_descriptions_tablayout);
        product_descr_viewpager = findViewById(R.id.content_viewpager_description_layout);
        product_content_tab = findViewById(R.id.tab_description_layoutout);
        txt_descriptionOnlyDetailBody = findViewById(R.id.txt_detail_body_detail_layout);

        total_rating = findViewById(R.id.txt_total_rating_rating);
        linear_rating = findViewById(R.id.num_total_rating_earch_star);
        total_rating_sum_linear = findViewById(R.id.txt_sum_total_number_each_star);
        linear_progressbar_rating = findViewById(R.id.linear_progressbar);
        average_rating = findViewById(R.id.txt_average_raing_rating);

        btn_discount = findViewById(R.id.btn_redemption_detail);

        //Load list images
        firebaseFirestore = FirebaseFirestore.getInstance();
        final List<String> productImages = new ArrayList<>();
        firebaseFirestore.collection("PRODUCTS").document("zE1acvtyOdBt52ZscltX").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();

                            for (long x = 1; x < (long)documentSnapshot.get("no_of_product_images") + 1;x++){
                                productImages.add(documentSnapshot.get("product_image_" + x).toString());
                            }
                            ProductImageAdapter productImageAdapter = new ProductImageAdapter(productImages);
                            productImageViewPager.setAdapter(productImageAdapter);

                            product_title.setText(documentSnapshot.get("product_fullname").toString());
                            product_average_rating_star.setText(documentSnapshot.get("average_rating").toString());
                            product_total_rating.setText("(" + documentSnapshot.get("total_rating").toString()  + ") người bình chọn");
                            product_price.setText(documentSnapshot.get("product_price").toString() + " Đ");
                            product_cutted_price.setText(documentSnapshot.get("product_cutted_price").toString() + " Đ");

                            reward_title.setText(documentSnapshot.get("free_discount_title").toString());
                            reward_body.setText("Giảm " + (long)documentSnapshot.get("free_discount") + "%" + documentSnapshot.get("free_discount_body").toString());

                            if((boolean)documentSnapshot.get("cod")){
                                cod.setVisibility(View.VISIBLE);
                                txt_cod.setVisibility(View.VISIBLE);
                            }
                            else {
                                cod.setVisibility(View.INVISIBLE);
                                txt_cod.setVisibility(View.INVISIBLE);
                            }
                            if((boolean)documentSnapshot.get("use_tab_layout")){
                                productDetail.setVisibility(View.GONE);
                                productDetailTabs.setVisibility(View.VISIBLE);
                                productDescription = documentSnapshot.get("product_description").toString();
                                productOrtherDetails = documentSnapshot.get("product_other_detail").toString();

                                for (long x=1; x < (long)documentSnapshot.get("total_specification_titles") + 1; x++){
                                    productSpecificationModelList.add(new ProductSpecificationModel(0,documentSnapshot.get("spec_title_"+x).toString()));
                                    for (long y = 1; y < (long)documentSnapshot.get("total_fields_spec_title_" + x) +1; y++){
                                        productSpecificationModelList.add(new ProductSpecificationModel(1,documentSnapshot.get("spec_title_"+x+"_field_" + y + "_name").toString(),documentSnapshot.get("spec_title_"+x+"_field_" + y + "_value").toString()));
                                    }
                                }
                            }
                            else {
                                productDetail.setVisibility(View.VISIBLE);
                                productDetailTabs.setVisibility(View.GONE);

                                txt_descriptionOnlyDetailBody.setText(documentSnapshot.get("product_description").toString());
                            }

                            //Rating
                            average_rating.setText(documentSnapshot.get("average_rating").toString());
                            total_rating.setText((long)documentSnapshot.get("total_rating") + "(người bình chọn)");

                            for(int x = 0; x < 5; x++){
                                TextView rating = (TextView) linear_rating.getChildAt(x);
                                rating.setText(String.valueOf((long)documentSnapshot.get((5-x) +"_star")));

                                ProgressBar progressBar= (ProgressBar) linear_progressbar_rating.getChildAt(x);
                                int maxProgress = Integer.parseInt(String.valueOf((long)documentSnapshot.get("total_rating")));
                                progressBar.setMax(maxProgress);
                                progressBar.setProgress(Integer.parseInt(String.valueOf((long)documentSnapshot.get((5-x) +"_star"))));

                            }
                            total_rating_sum_linear.setText(String.valueOf((long)documentSnapshot.get("total_rating")));
                            product_descr_viewpager.setAdapter(new ProductDetailAdapter(getSupportFragmentManager(), product_content_tab.getTabCount(),productDescription, productOrtherDetails, productSpecificationModelList));

                            //Rating
                        }
                        else {
                            String error = task.getException().getMessage();
                            Toast.makeText(ProductDetailActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

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
