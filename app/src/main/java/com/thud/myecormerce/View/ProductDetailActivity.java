package com.thud.myecormerce.View;

import android.app.Activity;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thud.myecormerce.Adapter.ProductDetailAdapter;
import com.thud.myecormerce.Adapter.ProductImageAdapter;
import com.thud.myecormerce.Adapter.RewardAdapter;
import com.thud.myecormerce.Fragments.CartFragment;
import com.thud.myecormerce.Fragments.ProductDescriptionFragment;
import com.thud.myecormerce.Fragments.ProductSpecificationFragment;
import com.thud.myecormerce.Fragments.SignInFragment;
import com.thud.myecormerce.Fragments.SignUpFragment;
import com.thud.myecormerce.Models.CartItemModel;
import com.thud.myecormerce.Models.ProductSpecificationModel;
import com.thud.myecormerce.Models.RewardModel;
import com.thud.myecormerce.Models.WishlistModel;
import com.thud.myecormerce.Presenter.DbQueries;
import com.thud.myecormerce.R;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity {

    public static boolean wishlist_running = false;
    public static boolean ratting_running = false;
    public static boolean cart_running = false;
    private TextView  badget_count;
    public static Activity productDetailActivity;

    public static MenuItem cartItem;

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
    public static  int initialRatting;
    public static LinearLayout rating;
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
    private LinearLayout btn_add_to_cart;

    public static FloatingActionButton add_wishlist;
    public static boolean ADDED_WISHLIST = false;
    public static boolean ADDED_TO_CART = false;
    private Dialog loadingDialog;
    private DocumentSnapshot documentSnapshot;

    private FirebaseFirestore firebaseFirestore;
    private Dialog signin_dialog;
    public static String product_id;

    private LinearLayout linearLayout_check_discount;
    private Button btn_check_discount;

    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

        linearLayout_check_discount = findViewById(R.id.linear_check_discount);
        btn_check_discount = findViewById(R.id.btn_redemption_detail);

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
        btn_add_to_cart = findViewById(R.id.btn_add_to_cart);


        initialRatting = -1;
        //Load list images

        firebaseFirestore = FirebaseFirestore.getInstance();
        loadingDialog = new Dialog(ProductDetailActivity.this);
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_main));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        final List<String> productImages = new ArrayList<>();
        product_id = getIntent().getStringExtra("PRODUCT_ID");

        firebaseFirestore.collection("PRODUCTS").document(product_id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){

                            documentSnapshot = task.getResult();

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
                            total_rating.setText((long)documentSnapshot.get("total_rating") + " (người bình chọn)");

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

                            if(currentUser != null) {

                                if(DbQueries.myRatting.size() == 0){
                                    DbQueries.loadRatting(ProductDetailActivity.this);
                                }

                                if (DbQueries.cartlist.size() == 0) {
                                    DbQueries.loadCartList(ProductDetailActivity.this, loadingDialog, false, badget_count, new TextView(ProductDetailActivity.this));
                                }

                                if (DbQueries.id_wishlist.size() == 0) {
                                    DbQueries.loadWishlist(ProductDetailActivity.this, loadingDialog, false);
                                }
                                else {
                                    loadingDialog.dismiss();
                                }

                            }
                            else {
                                loadingDialog.dismiss();
                            }
                            if(DbQueries.myratting_ids.contains(product_id)){
                                int index = DbQueries.myratting_ids.indexOf(product_id);
                                initialRatting = Integer.parseInt(String.valueOf(DbQueries.myRatting.get(index))) -1;
                                setRating(initialRatting);
                            }
                            if(DbQueries.cartlist.contains(product_id)){
                                ADDED_TO_CART = true;
                            }
                            else {
                                ADDED_TO_CART = false;
                            }

                            if(DbQueries.id_wishlist.contains(product_id)){
                                ADDED_WISHLIST = true;
                                add_wishlist.setImageTintList(getResources().getColorStateList(R.color.colorRed));
                            }
                            else {
                                add_wishlist.setImageTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                                ADDED_WISHLIST = false;
                            }
                            if((boolean) documentSnapshot.get("in_stock")){
                                btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(currentUser == null){
                                            signin_dialog.show();
                                        }
                                        else {
                                            /////////
                                            if(!cart_running) {
                                                cart_running= true;
                                                if (ADDED_TO_CART) {
                                                    cart_running= false;
                                                    Toast.makeText(ProductDetailActivity.this, "Sản phẩm đã có trong giỏ hàng!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Map<String, Object> addProduct = new HashMap<>();
                                                    addProduct.put("product_id_" + String.valueOf(DbQueries.cartlist.size()), product_id);
                                                    addProduct.put("list_size", (long) (DbQueries.cartlist.size() + 1));

                                                    firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_CART")
                                                            .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                if (DbQueries.cartItemModelList.size() != 0) {
                                                                    DbQueries.cartItemModelList.add(0,new CartItemModel(
                                                                            CartItemModel.CART_ITEM,
                                                                            product_id,
                                                                            documentSnapshot.get("product_image_1").toString(),
                                                                            documentSnapshot.get("product_fullname").toString(),
                                                                            documentSnapshot.get("product_price").toString(),
                                                                            documentSnapshot.get("product_cutted_price").toString(),
                                                                            (long) documentSnapshot.get("free_discount"),
                                                                            (long) 1,
                                                                            (long)0,
                                                                            (long) 0,
                                                                            (boolean)documentSnapshot.get("in_stock"),
                                                                            (long) documentSnapshot.get("max_quantity"))
                                                                            );
                                                                }

                                                                ADDED_TO_CART = true;
                                                                DbQueries.cartlist.add(product_id);
                                                                Toast.makeText(ProductDetailActivity.this, "Sản phẩm đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                                                                invalidateOptionsMenu();
                                                                cart_running =false;
                                                            } else {
                                                                cart_running  = false;
                                                                String error = task.getException().getMessage();
                                                                Toast.makeText(ProductDetailActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        }

                                    }
                                });
                            }
                            else {
                                btn_buy_now.setVisibility(View.GONE);
                                TextView out_of_stock = (TextView) btn_add_to_cart.getChildAt(0);
                                out_of_stock.setText("Hết hàng");
                                out_of_stock.setTextColor(getResources().getColor(R.color.colorwhitewhite));
                                out_of_stock.setCompoundDrawables(null,null, null,null);

                            }
                            //Rating
                        }
                        else {
                            loadingDialog.dismiss();
                            String error = task.getException().getMessage();
                            Toast.makeText(ProductDetailActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        viewPagerIndicator.setupWithViewPager(productImageViewPager, true);

        add_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser == null){
                    signin_dialog.show();
                }
                else
                {
                    //add_wishlist.setEnabled(false);
                    if(!wishlist_running) {
                        wishlist_running= true;
                        if (ADDED_WISHLIST) {
                            int index = DbQueries.id_wishlist.indexOf(product_id);
                            DbQueries.removeFromWishList(index, ProductDetailActivity.this);
                            //ADDED_WISHLIST = false;
                            add_wishlist.setImageTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                        } else {
                            add_wishlist.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ff142b")));
                            Map<String, Object> idData = new HashMap<>();
                            idData.put("product_id_" + String.valueOf(DbQueries.id_wishlist.size()), product_id);
                            idData.put("list_size", (long) (DbQueries.id_wishlist.size() + 1));

                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                    .set(idData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        if (DbQueries.wishlistModelList.size() != 0) {
                                            DbQueries.wishlistModelList.add(new WishlistModel(
                                            product_id,
                                            documentSnapshot.get("product_image_1").toString(),
                                            documentSnapshot.get("product_fullname").toString(),
                                            documentSnapshot.get("product_price").toString(),
                                            documentSnapshot.get("product_cutted_price").toString(),
                                            (long) documentSnapshot.get("free_discount"),
                                            (boolean) documentSnapshot.get("cod"),
                                            documentSnapshot.get("average_rating").toString(),
                                            (long) documentSnapshot.get("total_rating"),
                                            (boolean) documentSnapshot.get("cod")));
                                                    }

                                                    ADDED_WISHLIST = true;
                                                    add_wishlist.setImageTintList(getResources().getColorStateList(R.color.colorRed));
                                                    DbQueries.id_wishlist.add(product_id);
                                                    Toast.makeText(ProductDetailActivity.this, "Sản phẩm đã thêm vào danh sách mong muốn", Toast.LENGTH_SHORT).show();

//

                                        } else {
                                        add_wishlist.setImageTintList(getResources().getColorStateList(R.color.colorBlack));
                                        wishlist_running  = false;
                                        String error = task.getException().getMessage();
                                        Toast.makeText(ProductDetailActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                                    }
                                    wishlist_running  = false;
                                }
                            });
                        }
                    }
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
        // Rating Layout
        rating = findViewById(R.id.linear_rating_now);
        for (int x=0; x < rating.getChildCount(); x++){
            final  int  startPosition = x;
            rating.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentUser == null){
                        signin_dialog.show();
                    }
                    else {
                        if(startPosition != initialRatting) {
                            if (!ratting_running) {
                                ratting_running = true;
                                setRating(startPosition);
                                Map<String, Object> updateRatting = new HashMap<>();

                                if (DbQueries.myratting_ids.contains(product_id)) {
                                    //Đã ratting
                                    TextView oldrating = (TextView) linear_rating.getChildAt(5 - initialRatting - 1);
                                    TextView finalrating = (TextView) linear_rating.getChildAt(5 - startPosition - 1);

                                    updateRatting.put(initialRatting + 1 + "_star", Long.parseLong(oldrating.getText().toString()) - 1);
                                    updateRatting.put(startPosition + 1 + "_star", Long.parseLong(finalrating.getText().toString()) + 1);
                                    updateRatting.put("average_rating", calculateAverageRating(startPosition - initialRatting, true));
                                    updateRatting.put("total_rating", (long) documentSnapshot.get("total_rating") + 1);

                                } else {
                                    //Chưa ratting

                                    updateRatting.put(startPosition + 1 + "_star", (long) documentSnapshot.get(startPosition + 1 + "_star") + 1);
                                    updateRatting.put("average_rating", calculateAverageRating(startPosition + 1, false));
                                    updateRatting.put("total_rating", (long) documentSnapshot.get("total_rating") + 1);
                                }
                                firebaseFirestore.collection("PRODUCTS").document(product_id)
                                        .update(updateRatting).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            Map<String, Object> myratting = new HashMap<>();
                                            if (DbQueries.myratting_ids.contains(product_id)) {
                                                myratting.put("ratting_" + DbQueries.myratting_ids.indexOf(product_id), (long) startPosition + 1);
                                            } else {
                                                myratting.put("list_size", (long) DbQueries.myratting_ids.size() + 1);
                                                myratting.put("product_id_" + DbQueries.myratting_ids.size(), product_id);
                                                myratting.put("ratting_" + DbQueries.myratting_ids.size(), (long) startPosition + 1);
                                            }

                                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_RATTING")
                                                    .update(myratting).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {

                                                        if (DbQueries.myratting_ids.contains(product_id)) {

                                                            DbQueries.myRatting.set(DbQueries.myratting_ids.indexOf(product_id), (long) startPosition + 1);

                                                            TextView oldrating = (TextView) linear_rating.getChildAt(5 - initialRatting - 1);
                                                            TextView finalrating = (TextView) linear_rating.getChildAt(5 - startPosition - 1);
                                                            oldrating.setText(String.valueOf(Integer.parseInt(oldrating.getText().toString()) - 1));
                                                            finalrating.setText(String.valueOf(Integer.parseInt(finalrating.getText().toString()) + 1));

                                                        } else {
                                                            DbQueries.myratting_ids.add(product_id);
                                                            DbQueries.myRatting.add((long) startPosition + 1);

                                                            product_total_rating.setText((long) documentSnapshot.get("total_rating") + 1 + " (người bình chọn)");
                                                            total_rating.setText((long) documentSnapshot.get("total_rating") + 1 + " (người bình chọn)");
                                                            total_rating_sum_linear.setText(String.valueOf((long) documentSnapshot.get("total_rating") + 1));

                                                            TextView rating = (TextView) linear_rating.getChildAt(5 - startPosition - 1);
                                                            rating.setText(String.valueOf(Integer.parseInt(rating.getText().toString()) + 1));

                                                            Toast.makeText(ProductDetailActivity.this, "Cảm ơn bạn đã đánh giá!", Toast.LENGTH_SHORT).show();
                                                        }

                                                        for (int x = 0; x < 5; x++) {
                                                            TextView rating_each = (TextView) linear_rating.getChildAt(x);

                                                            ProgressBar progressBar = (ProgressBar) linear_progressbar_rating.getChildAt(x);
                                                            DbQueries.myratting_ids.contains(product_id);
                                                            int maxProgress = Integer.parseInt(total_rating_sum_linear.getText().toString());
                                                            progressBar.setMax(maxProgress);
                                                            progressBar.setProgress(Integer.parseInt(rating_each.getText().toString()));

                                                        }
                                                        initialRatting = startPosition;
                                                        product_average_rating_star.setText(calculateAverageRating(0, true));
                                                        average_rating.setText(calculateAverageRating(0, true));

                                                        if (DbQueries.myratting_ids.contains(product_id) && DbQueries.wishlistModelList.size() != 0) {
                                                            int index = DbQueries.id_wishlist.indexOf(product_id);
                                                            DbQueries.wishlistModelList.get(index).setRating(average_rating.getText().toString());
                                                            DbQueries.wishlistModelList.get(index).setTotalRating(Long.parseLong(product_total_rating.getText().toString()));
                                                        }
                                                    } else {
                                                        setRating(initialRatting);
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(ProductDetailActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                                                    }
                                                    ratting_running = false;
                                                }
                                            });

                                        } else {
                                            ratting_running = false;
                                            setRating(initialRatting);
                                            String error = task.getException().getMessage();
                                            Toast.makeText(ProductDetailActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            });

        }
        //Rating
        btn_buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentUser == null){
                    signin_dialog.show();
                }
                else {
                    DeliveryActivity.fromCart = false;
                    loadingDialog.show();
                    productDetailActivity =ProductDetailActivity.this;
                    DeliveryActivity.cartItemModelList = new ArrayList<>();
                    DeliveryActivity.cartItemModelList.add(new CartItemModel(
                            CartItemModel.CART_ITEM,
                            product_id,
                            documentSnapshot.get("product_image_1").toString(),
                            documentSnapshot.get("product_fullname").toString(),
                            documentSnapshot.get("product_price").toString(),
                            documentSnapshot.get("product_cutted_price").toString(),
                            (long) documentSnapshot.get("free_discount"),
                            (long) 1,
                            (long)0,
                            (long) 0,
                            (boolean)documentSnapshot.get("in_stock"),
                            (long) documentSnapshot.get("max_quantity"))
                    );
                    DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));

                    if(DbQueries.addressModelList.size() == 0) {
                        DbQueries.loadAddress(ProductDetailActivity.this, loadingDialog);
                    }else {
                        loadingDialog.dismiss();
                        Intent intentDelivery = new Intent(ProductDetailActivity.this, DeliveryActivity.class );
                        startActivity(intentDelivery);
                    }
                }
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
        /// End Dialog discount

        /// Dialog sign

        signin_dialog = new Dialog(ProductDetailActivity.this);
        signin_dialog.setContentView(R.layout.signin_dialog);
        signin_dialog.setCancelable(true);
        signin_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final Intent intentSignInUp = new Intent(ProductDetailActivity.this, RegisterActivity.class);

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
        /// Dialog sign
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Hien thi layout
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            linearLayout_check_discount.setVisibility(View.GONE);
        }
        else {
            linearLayout_check_discount.setVisibility(View.VISIBLE);
        }

        if(currentUser != null) {

            if(DbQueries.myRatting.size() == 0){
                DbQueries.loadRatting(ProductDetailActivity.this);
            }
            if (DbQueries.id_wishlist.size() == 0) {
                DbQueries.loadWishlist(ProductDetailActivity.this, loadingDialog, false);
            } else {
                loadingDialog.dismiss();
            }

        }
        else {
            loadingDialog.dismiss();
        }
        if(DbQueries.myratting_ids.contains(product_id)){
            int index = DbQueries.myratting_ids.indexOf(product_id);
            initialRatting = Integer.parseInt(String.valueOf(DbQueries.myRatting.get(index))) -1;
            setRating(initialRatting);
        }

        if(DbQueries.cartlist.contains(product_id)){
            ADDED_TO_CART = true;
        }else {
            ADDED_TO_CART = false;
        }

        if(DbQueries.id_wishlist.contains(product_id)){
            ADDED_WISHLIST = true;
            add_wishlist.setImageTintList(getResources().getColorStateList(R.color.colorRed));
        }else {
            add_wishlist.setImageTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
            ADDED_WISHLIST = false;
        }
        invalidateOptionsMenu();
    }

    //Tinh so sao - Average star
    private String calculateAverageRating(long currentUserRatting, boolean update){
            Double totalStar = Double.valueOf(0);
            for (int x = 1; x < 6; x++){
                TextView ratingNo= (TextView) linear_rating.getChildAt(5 - x);
                totalStar = totalStar +  (Long.parseLong(ratingNo.getText().toString())*x);
            }
            totalStar = totalStar + currentUserRatting;
            if(update){
                return String.valueOf(totalStar / Long.parseLong(total_rating_sum_linear.getText().toString())).substring(0,3);
            }
            else {
                return String.valueOf(totalStar / (Long.parseLong(total_rating_sum_linear.getText().toString()) + 1)).substring(0,3);
            }
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

    public static void setRating(int startPosition) {
            for (int x = 0; x < rating.getChildCount(); x++) {
                ImageView starbtn = (ImageView) rating.getChildAt(x);
                starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
                if (x <= startPosition) {
                    starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
                }
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_menu, menu);

        cartItem = menu.findItem(R.id.icon_shopping_cart);
        cartItem.setActionView(R.layout.badge_layout);
        ImageView cart_icon = cartItem.getActionView().findViewById(R.id.imv_badge_icon_cart);
        cart_icon.setImageResource(R.drawable.shopping_cart);
        badget_count = cartItem.getActionView().findViewById(R.id.txt_badge_count);

        if(currentUser != null) {
            if (DbQueries.cartlist.size() == 0) {
                DbQueries.loadCartList(ProductDetailActivity.this, loadingDialog, false, badget_count, new TextView(ProductDetailActivity.this));
            }else {
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
                    Intent intentCart = new Intent(ProductDetailActivity.this, MainActivity.class);
                    MainActivity.SHOW_CART = true;
                    startActivity(intentCart);
                }
            }
        });
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

            return true;
        }
        else if(id == android.R.id.home)
        {
            productDetailActivity =null;
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
       productDetailActivity = null;
        super.onBackPressed();

    }
}
