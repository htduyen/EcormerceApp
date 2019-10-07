package com.thud.myecormerce.Presenter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.thud.myecormerce.Adapter.CategoryAdapter;
import com.thud.myecormerce.Adapter.HomePageAdapter;
import com.thud.myecormerce.Adapter.MyOrderAdapter;
import com.thud.myecormerce.Fragments.CartFragment;
import com.thud.myecormerce.Fragments.HomeFragment;
import com.thud.myecormerce.Fragments.MyOrderFragment;
import com.thud.myecormerce.Fragments.MyRewardFragment;
import com.thud.myecormerce.Fragments.MyWishlistFragment;
import com.thud.myecormerce.Models.AddressModel;
import com.thud.myecormerce.Models.CartItemModel;
import com.thud.myecormerce.Models.CategoryModel;
import com.thud.myecormerce.Models.HomePageModel;
import com.thud.myecormerce.Models.MyOrderItemModel;
import com.thud.myecormerce.Models.ProductHorizonModel;
import com.thud.myecormerce.Models.RewardModel;
import com.thud.myecormerce.Models.SliderModel;
import com.thud.myecormerce.Models.WishlistModel;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.AddAddressActivity;
import com.thud.myecormerce.View.DeliveryActivity;
import com.thud.myecormerce.View.ProductDetailActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.thud.myecormerce.View.ProductDetailActivity.cartItem;
import static com.thud.myecormerce.View.ProductDetailActivity.product_id;
import static com.thud.myecormerce.View.ProductDetailActivity.ratting_running;

public class DbQueries {

    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public static String fullname, email, profile;


    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModels = new ArrayList<>();

    public static List<List<HomePageModel>> lists = new ArrayList<>();
    public static List<String> listNameCategories = new ArrayList<>();

    public static List<String> id_wishlist = new ArrayList<>();
    public static List<WishlistModel> wishlistModelList = new ArrayList<>();

    public static List<String> myratting_ids = new ArrayList<>();
    public static List<Long> myRatting = new ArrayList<>();

    public static List<String> cartlist = new ArrayList<>();
    public static List<CartItemModel> cartItemModelList = new ArrayList<>();

    public static int addressselected = -1;
    public static List<AddressModel> addressModelList = new ArrayList<>();

    public static List<RewardModel> rewardModelList = new ArrayList<>();

    public static List<MyOrderItemModel> myOrderItemModelList = new ArrayList<>();

    //Categories
    public static void loadCategories(final RecyclerView recyclerView_Cate, final Context context){
        categoryModels.clear();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                categoryModels.add(new CategoryModel(documentSnapshot.get("CateIcon").toString(),documentSnapshot.get("CateName").toString()));
                            }
                            //Luu vao adapter
                            CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModels);
                            recyclerView_Cate.setAdapter(categoryAdapter);
                            categoryAdapter.notifyDataSetChanged();
                        }
                        else {
                            String message = task.getException().getMessage();
                            Toast.makeText(context, "Error: "  + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void setLayout(final RecyclerView recyclerViewHomePage, final Context context, final int index, String cateName){

        firebaseFirestore.collection("CATEGORIES")
                .document(cateName.toUpperCase())
                .collection("TOP_DEALS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
 //                               Toast.makeText(context, "Type: " + documentSnapshot.get("view_type"), Toast.LENGTH_SHORT).show();
                                if((long)documentSnapshot.get("view_type") == 0){
                                    List<SliderModel> sliderModelList= new ArrayList<>();
                                    long num_slider =(long) documentSnapshot.get("no_of_banner");
                                    for(long x = 1; x < num_slider + 1; x++){
                                        sliderModelList.add(new SliderModel(documentSnapshot.get("banner"+ x).toString(),documentSnapshot.get("banner_bg_"+x).toString()));
                                    }
                                    lists.get(index).add(new HomePageModel(0, sliderModelList));
                                }
                                else if((long)documentSnapshot.get("view_type") == 1){
                                    lists.get(index).add(new HomePageModel(1, documentSnapshot.get("strip_ads_img").toString(),documentSnapshot.get("background").toString()));
                                }
                                else if((long)documentSnapshot.get("view_type") == 2){

                                    List<WishlistModel> wishlistViewAllModelList  = new ArrayList<>();


                                    List<ProductHorizonModel> productHorizonModelList = new ArrayList<>();
                                    long num_products =(long) documentSnapshot.get("num_products");
                                    for(long x = 1; x < num_products + 1; x++){
                                        productHorizonModelList.add(new ProductHorizonModel(
                                                documentSnapshot.get("product_id_"+x).toString(),
                                                documentSnapshot.get("product_image_"+x).toString(),
                                                documentSnapshot.get("product_name_"+x).toString(),
                                                documentSnapshot.get("product_descr_"+x).toString(),
                                                documentSnapshot.get("product_price_"+x).toString()
                                        ));
                                        wishlistViewAllModelList.add(new WishlistModel(
                                                documentSnapshot.get("product_id_"+x).toString(),
                                                documentSnapshot.get("product_image_"+x).toString(),
                                                documentSnapshot.get("product_fullname_"+x).toString(),
                                                documentSnapshot.get("product_price_"+x).toString(),
                                                documentSnapshot.get("cutted_price_"+x).toString(),
                                                (long)documentSnapshot.get("free_discount_" + x),
                                                (boolean)documentSnapshot.get("cod_" + x),
                                                documentSnapshot.get("average_rating_" + x).toString(),
                                                (long)documentSnapshot.get("total_rating_" + x),
                                                (boolean)documentSnapshot.get("in_stock_" + x)));
                                    }
                                    //Toast.makeText(getContext(), "Size: " + sliderModelList.size(), Toast.LENGTH_SHORT).show();
                                    lists.get(index).add(new HomePageModel(2, documentSnapshot.get("group_product_title").toString(),documentSnapshot.get("background_group_product").toString(), productHorizonModelList,wishlistViewAllModelList));

                                }
                                else if((long)documentSnapshot.get("view_type") == 3){
                                    List<ProductHorizonModel> gridProductList = new ArrayList<>();
                                    long num_products =(long) documentSnapshot.get("num_products");
                                    for(long x = 1; x < num_products + 1; x++){
                                        gridProductList.add(new ProductHorizonModel(
                                                documentSnapshot.get("product_id_"+x).toString(),
                                                documentSnapshot.get("product_image_"+x).toString(),
                                                documentSnapshot.get("product_name_"+x).toString(),
                                                documentSnapshot.get("product_descr_"+x).toString(),
                                                documentSnapshot.get("product_price_"+x).toString()
                                        ));
                                    }
                                    //Toast.makeText(getContext(), "Size: " + sliderModelList.size(), Toast.LENGTH_SHORT).show();
                                    lists.get(index).add(new HomePageModel(3, documentSnapshot.get("group_product_title").toString(),documentSnapshot.get("background_group_product").toString(), gridProductList));


                                }
                            }
                            //Luu vao adapter
                            HomePageAdapter homePageAdapter = new HomePageAdapter(lists.get(index));
                            recyclerViewHomePage.setAdapter(homePageAdapter);
                            homePageAdapter.notifyDataSetChanged();
                            HomeFragment.swipeRefreshLayout.setRefreshing(false);
                        }
                        else {
                            String message = task.getException().getMessage();
                            Toast.makeText(context, "Error: "  + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    //Categories

    ///Wishlist
    public static void loadWishlist(final Context context, final Dialog dialog, final boolean loadProDuctData){
        id_wishlist.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    for (long x = 0; x < (long)task.getResult().get("list_size"); x++){

                        id_wishlist.add(task.getResult().get("product_id_"+ x).toString());

                        if(DbQueries.id_wishlist.contains(ProductDetailActivity.product_id)){
                            ProductDetailActivity.ADDED_WISHLIST = true;
                            if(ProductDetailActivity.add_wishlist != null) {
                                ProductDetailActivity.add_wishlist.setImageTintList(context.getResources().getColorStateList(R.color.colorRed));
                            }
                        }
                        else {
                            if (ProductDetailActivity.add_wishlist != null) {
                                ProductDetailActivity.add_wishlist.setImageTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                            }
                            ProductDetailActivity.ADDED_WISHLIST = false;
                        }
                        if(loadProDuctData) {
                            wishlistModelList.clear();
                            final String product_id = task.getResult().get("product_id_" + x).toString();
                            firebaseFirestore.collection("PRODUCTS").document(product_id)
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        final DocumentSnapshot documentSnapshot = task.getResult();
                                        FirebaseFirestore.getInstance().collection("PRODUCTS").document(product_id).collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING).get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if(task.isSuccessful()){

                                                            if(task.getResult().getDocuments().size() < (long)documentSnapshot.get("stock_quantity")){
                                                                wishlistModelList.add(new WishlistModel(
                                                                        product_id,
                                                                        documentSnapshot.get("product_image_1").toString(),
                                                                        documentSnapshot.get("product_fullname").toString(),
                                                                        documentSnapshot.get("product_price").toString(),
                                                                        documentSnapshot.get("product_cutted_price").toString(),
                                                                        (long) documentSnapshot.get("free_discount"),
                                                                        (boolean) documentSnapshot.get("cod"),
                                                                        documentSnapshot.get("average_rating").toString(),
                                                                        (long) documentSnapshot.get("total_rating"),
                                                                         true));
                                                            }else {
                                                                wishlistModelList.add(new WishlistModel(
                                                                        product_id,
                                                                        documentSnapshot.get("product_image_1").toString(),
                                                                        documentSnapshot.get("product_fullname").toString(),
                                                                        documentSnapshot.get("product_price").toString(),
                                                                        documentSnapshot.get("product_cutted_price").toString(),
                                                                        (long) documentSnapshot.get("free_discount"),
                                                                        (boolean) documentSnapshot.get("cod"),
                                                                        documentSnapshot.get("average_rating").toString(),
                                                                        (long) documentSnapshot.get("total_rating"),
                                                                         false));
                                                            }
                                                            // xem  try/catch phia duoi
                                                            MyWishlistFragment.wishlistAdapter.notifyDataSetChanged();
                                                        }
                                                        else {
                                                            String error = task.getException().getMessage();
                                                            Toast.makeText(context, "Error: " +error, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

//                                        try{
//                                            MyWishlistFragment.wishlistAdapter.notifyDataSetChanged();
//                                        }
//                                        catch (Exception e){
//                                            Toast.makeText(context, "Loi: " + e, Toast.LENGTH_SHORT).show();
//                                            Log.d("AAAAAAA", e.toString());
//                                        }

                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }
                else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }
    public static void removeFromWishList(final int index, final Context context){
        final String productRemoveID = id_wishlist.get(index);
        id_wishlist.remove(index);
        Map<String,Object> updateWishlist = new HashMap<>();
        for(int x = 0; x < id_wishlist.size(); x++){
            updateWishlist.put("product_id_" + x, id_wishlist.get(x));
        }
        updateWishlist.put("list_size", (long)id_wishlist.size());

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
                .set(updateWishlist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    if(wishlistModelList.size() != 0){
                        wishlistModelList.remove(index);
                        MyWishlistFragment.wishlistAdapter.notifyDataSetChanged();
                    }
                    ProductDetailActivity.ADDED_WISHLIST = false;
                    Toast.makeText(context, "Sản phẩm đã được xóa khỏi danh sách", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (ProductDetailActivity.add_wishlist != null) {
                        ProductDetailActivity.add_wishlist.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ff142b")));
                    }
                    id_wishlist.add(index, productRemoveID);
                    String error = task.getException().getMessage();
                    Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
//                if(ProductDetailActivity.add_wishlist !=null) {
//                    ProductDetailActivity.add_wishlist.setEnabled(true);
//                }
                ProductDetailActivity.wishlist_running =   false;

            }
        });
    }
    ///Wishlist

    ///Ratting
    public static void loadRatting(final Context context){
        if (!ProductDetailActivity.ratting_running) {
            ProductDetailActivity.ratting_running = true;
            myratting_ids.clear();
            myRatting.clear();
            firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).collection("USER_DATA").document("MY_RATTING")
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        List<String> orderProductIds = new ArrayList<>();
                        for(int x =0; x < myOrderItemModelList.size(); x++){
                            orderProductIds.add(myOrderItemModelList.get(x).getProduct_id());
                        }

                        for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                            myratting_ids.add(task.getResult().get("product_id_" + x).toString());
                            myRatting.add((long) task.getResult().get("ratting_" + x));

                            if (task.getResult().get("product_id_" + x).toString().equals(ProductDetailActivity.product_id)){
                                ProductDetailActivity.initialRatting = Integer.parseInt(String.valueOf((long) task.getResult().get("ratting_" + x))) - 1;
                                /////////////////////////////////////////////////////////////////////////////////////////
                                if(ProductDetailActivity.rating != null) {
                                    ProductDetailActivity.setRating(ProductDetailActivity.initialRatting);
                                }
                            }
                            if(orderProductIds.contains(task.getResult().get("product_id_" + x).toString())){
                                myOrderItemModelList.get(orderProductIds.indexOf(task.getResult().get("product_id_" + x).toString())).setNumRating(Integer.parseInt(String.valueOf((long) task.getResult().get("ratting_" + x))) - 1);
                            }
                        }
                        if(MyOrderFragment.myOrderAdapter != null){
                            MyOrderFragment.myOrderAdapter.notifyDataSetChanged();
                        }
                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                    ProductDetailActivity.ratting_running = false;
                }
            });
        }
    }
    ///Ratting

    public static void  loadCartList(final Context context, final Dialog dialog, final boolean loadProDuctData, final TextView badget_count, final TextView txt_total_amount){
        cartlist.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_CART")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    for (long x = 0; x < (long)task.getResult().get("list_size"); x++){

                        cartlist.add(task.getResult().get("product_id_"+ x).toString());

                        if(DbQueries.cartlist.contains(ProductDetailActivity.product_id)){
                            ProductDetailActivity.ADDED_TO_CART = true;

                        }
                        else {
                            ProductDetailActivity.ADDED_TO_CART = false;

                        }
                        if(loadProDuctData) {
                            cartItemModelList.clear();
                            final String product_id = task.getResult().get("product_id_" + x).toString();
                                    firebaseFirestore.collection("PRODUCTS").document(product_id)
                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                final DocumentSnapshot documentSnapshot = task.getResult();
                                                FirebaseFirestore.getInstance().collection("PRODUCTS").document(product_id).collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING).get()
                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                if(task.isSuccessful()){
                                                                    int index  = 0;
                                                                    if(cartlist.size() >= 2){
                                                                        index = cartlist.size() - 2;
                                                                    }
                                                                    if(task.getResult().getDocuments().size() < (long)documentSnapshot.get("stock_quantity")){
                                                                        cartItemModelList.add(index,new CartItemModel(
                                                                                documentSnapshot.getBoolean("cod"),
                                                                                CartItemModel.CART_ITEM,
                                                                                product_id,
                                                                                documentSnapshot.get("product_image_1").toString(),
                                                                                documentSnapshot.get("product_fullname").toString(),
                                                                                documentSnapshot.get("product_price").toString(),
                                                                                documentSnapshot.get("product_cutted_price").toString(),
                                                                                (long) documentSnapshot.get("free_discount"),
                                                                                (long) 1,
                                                                                (long) documentSnapshot.get("offers_applied"),
                                                                                (long) 0,
                                                                                true,
                                                                                (long) documentSnapshot.get("max_quantity"),
                                                                                (long) documentSnapshot.get("stock_quantity")));
                                                                    }else {
                                                                        cartItemModelList.add(index,new CartItemModel(
                                                                                documentSnapshot.getBoolean("cod"),
                                                                                CartItemModel.CART_ITEM,
                                                                                product_id,
                                                                                documentSnapshot.get("product_image_1").toString(),
                                                                                documentSnapshot.get("product_fullname").toString(),
                                                                                documentSnapshot.get("product_price").toString(),
                                                                                documentSnapshot.get("product_cutted_price").toString(),
                                                                                (long) documentSnapshot.get("free_discount"),
                                                                                (long) 1,
                                                                                (long)documentSnapshot.get("offers_applied"),
                                                                                (long) 0,
                                                                                false,
                                                                                (long) documentSnapshot.get("max_quantity"),
                                                                                (long) documentSnapshot.get("stock_quantity")));
                                                                    }
                                                                    if(cartlist.size() == 1){
                                                                        cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));
                                                                        Log.d("TOTALAMOUND", String.valueOf(CartItemModel.TOTAL_AMOUNT));
                                                                        Log.d("TOTALAMOUND_num", txt_total_amount.getText().toString());
                                                                        LinearLayout parent = (LinearLayout) txt_total_amount.getParent().getParent();
                                                                        parent.setVisibility(View.VISIBLE);
                                                                    }
                                                                    if(cartlist.size() == 0){
                                                                        cartItemModelList.clear();
                                                                    }
                                                                    CartFragment.cartlistAdapter.notifyDataSetChanged();
                                                                    // xem  try/catch phia duoi
                                                                    //MyWishlistFragment.wishlistAdapter.notifyDataSetChanged();
                                                                }
                                                                else {
                                                                    String error = task.getException().getMessage();
                                                                    Toast.makeText(context, "Error: " +error, Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });

                                } else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    }
                    if(cartlist.size() > 0 ){
                        badget_count.setVisibility(View.VISIBLE);
                    }
                    else {
                        badget_count.setVisibility(View.INVISIBLE);
                    }
                    if(DbQueries.cartlist.size() < 99){
                        badget_count.setText(String.valueOf(DbQueries.cartlist.size()));
                    }
                    else {
                        badget_count.setText("99+");
                    }
                }
                else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }
    public static void removeFromCart(final int index, final Context context, final TextView totalAmount){

        final String productRemoveID = cartlist.get(index);
        cartlist.remove(index);
        Map<String,Object> updateCartlist = new HashMap<>();
        for(int x = 0; x < cartlist.size(); x++){
            updateCartlist.put("product_id_" + x, cartlist.get(x));
        }
        updateCartlist.put("list_size", (long)cartlist.size());

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_CART")
                .set(updateCartlist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    if(cartItemModelList.size() != 0){
                        cartItemModelList.remove(index);
                        CartFragment.cartlistAdapter.notifyDataSetChanged();
                    }
                    if(cartlist.size() == 0){
                        LinearLayout parent = (LinearLayout) totalAmount.getParent().getParent();
                        parent.setVisibility(View.GONE);
                        Toast.makeText(context, "Giỏ hàng rỗng!", Toast.LENGTH_SHORT).show();
                        cartItemModelList.clear();

                    }
                    Toast.makeText(context, "Sản phẩm đã được xóa khỏi giỏ hàng", Toast.LENGTH_SHORT).show();
                }
                else {
                    cartlist.add(index, productRemoveID);
                    String error = task.getException().getMessage();
                    Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
                ProductDetailActivity.cart_running =   false;

            }
        });
    }

    public static void loadAddress(final Context context, final Dialog dialog, final boolean gotoDeliveryActivity){
        addressModelList.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_ADDRESS")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    Intent deliveryIntent = null;
                    if((long)task.getResult().get("list_size") == 0){
                        deliveryIntent = new Intent(context, AddAddressActivity.class);
                        deliveryIntent.putExtra("INTENT", "deliveryIntent");
                    }
                    else {
                        for(long x =1;x < (long)task.getResult().get("list_size") +1; x++){
                            addressModelList.add(new AddressModel(
                                    task.getResult().getString("province_"+ x),
                                    task.getResult().getString("country_"+ x),
                                    task.getResult().getString("locationDetail_"+ x),
                                    task.getResult().getString("full_name_"+ x),
                                    task.getResult().getString("gender_"+ x),
                                    task.getResult().getString("email_"+ x),
                                    task.getResult().getString("phone_number_"+ x),
                                    task.getResult().getBoolean("selected_"+ x)));

                            if((boolean)task.getResult().get("selected_"+ x)){
                                addressselected = Integer.parseInt(String.valueOf(x -1));
                            }
                        }
                        if(gotoDeliveryActivity) {
                            deliveryIntent = new Intent(context, DeliveryActivity.class);
                        }

                    }
                    if(gotoDeliveryActivity) {
                        context.startActivity(deliveryIntent);
                    }
                }
                else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

    }

    public static void loadReward(final Context context, final Dialog loadingDialog, final Boolean onRewardFragment){

        rewardModelList.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            final Date lastSeenDate = task.getResult().getDate("Last seen");

                            firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_REWARDS").get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful()){

                                                for(DocumentSnapshot documentSnapshot: task.getResult()){
                                                    if(documentSnapshot.get("type").toString().equals("Discount") && lastSeenDate.before(documentSnapshot.getDate("validity"))){
                                                        rewardModelList.add(new RewardModel(
                                                                documentSnapshot.getId(),
                                                                documentSnapshot.get("type").toString(),
                                                                documentSnapshot.get("lower_limit").toString(),
                                                                documentSnapshot.get("upper_limit").toString(),
                                                                documentSnapshot.get("percent").toString(),
                                                                documentSnapshot.get("body").toString(),
                                                                (Date)documentSnapshot.get("validity"),
                                                                (boolean)documentSnapshot.get("alreadlyUse")
                                                        ));
                                                    }else if(documentSnapshot.get("type").toString().equals("Flat") && lastSeenDate.before(documentSnapshot.getDate("validity"))) {
                                                        rewardModelList.add(new RewardModel(
                                                                documentSnapshot.getId(),
                                                                documentSnapshot.get("type").toString(),
                                                                documentSnapshot.get("lower_limit").toString(),
                                                                documentSnapshot.get("upper_limit").toString(),
                                                                documentSnapshot.get("amount").toString(),
                                                                documentSnapshot.get("body").toString(),
                                                                (Date)documentSnapshot.get("validity"),
                                                                (boolean)documentSnapshot.get("alreadlyUse")
                                                        ));
                                                    }
                                                }
                                                if(onRewardFragment) {
                                                    MyRewardFragment.rewardAdapter.notifyDataSetChanged();
                                                }
                                            }else {
                                                String error = task.getException().getMessage();
                                                Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show();

                                            }
                                            loadingDialog.dismiss();
                                        }
                                    });
                        }else {
                            loadingDialog.dismiss();
                            String error = task.getException().getMessage();
                            Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loadOrders(final Context context,@NonNull final MyOrderAdapter myOrderAdapter, final Dialog loadingDialog){
        myOrderItemModelList.clear();
        firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).collection("USER_ORDERS").orderBy("time order", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){

                                firebaseFirestore.collection("ORDERS").document(documentSnapshot.getString("order_id")).collection("OrderItems").get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (DocumentSnapshot orderItems : task.getResult().getDocuments()) {
                                                    final MyOrderItemModel myOrderItemModel = new MyOrderItemModel(
                                                            orderItems.getString("Product ID"),
                                                            orderItems.getString("Order Status"),
                                                            orderItems.getString("Address"),
                                                            orderItems.getString("Discounted Price"),
                                                            orderItems.getString("Product Cutted Price"),
                                                            orderItems.getDate("Ordered date"),
                                                            orderItems.getDate("Packed date"),
                                                            orderItems.getDate("Shipped date"),
                                                            orderItems.getDate("Delivered date"),
                                                            orderItems.getDate("Cancelled date"),
                                                            orderItems.getString("Discounted Price"),
                                                            orderItems.getLong("Free Discount"),
                                                            orderItems.getString("FullName"),
                                                            orderItems.getString("Order ID"),
                                                            orderItems.getString("Payment Method"),
                                                            orderItems.getString("PhoneNumber"),
                                                            orderItems.getString("Product Price"),
                                                            orderItems.getLong("Product Quantity"),
                                                            orderItems.getString("User ID"),
                                                            orderItems.getString("Product Image"),
                                                            orderItems.getString("Product Name"),
                                                            orderItems.getString("Delivery Price"),
                                                            orderItems.getBoolean("Cancellation Requested")

                                                    );
                                                    myOrderItemModelList.add(myOrderItemModel);
                                                }
                                                DbQueries.loadRatting(context);
                                                if(myOrderAdapter != null) {
                                                    myOrderAdapter.notifyDataSetChanged();
                                                }
                                            } else {
                                                String ex = task.getException().getMessage();
                                                Toast.makeText(context, "Error: " + ex, Toast.LENGTH_SHORT).show();
                                            }
                                            loadingDialog.dismiss();
                                        }
                                    });
                            }
                        }else {
                            loadingDialog.dismiss();
                            String ex = task.getException().getMessage();
                            Toast.makeText(context, "Error: " + ex, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void clearData(){
        categoryModels.clear();
        lists.clear();
        id_wishlist.clear();
        listNameCategories.clear();
        wishlistModelList.clear();
        cartlist.clear();
        cartItemModelList.clear();
        myratting_ids.clear();
        myRatting.clear();
        addressModelList.clear();
        rewardModelList.clear();
        myOrderItemModelList.clear();
    }


}
