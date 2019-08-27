package com.thud.myecormerce.Presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.thud.myecormerce.Adapter.CategoryAdapter;
import com.thud.myecormerce.Adapter.HomePageAdapter;
import com.thud.myecormerce.Models.CategoryModel;
import com.thud.myecormerce.Models.HomePageModel;
import com.thud.myecormerce.Models.ProductHorizonModel;
import com.thud.myecormerce.Models.SliderModel;
import com.thud.myecormerce.Models.WishlistModel;

import java.util.ArrayList;
import java.util.List;

public class DbQueries {

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModels = new ArrayList<>();

    public static List<List<HomePageModel>> lists = new ArrayList<>();
    public static List<String> listNameCategories = new ArrayList<>();

    public static void loadCategories(final CategoryAdapter categoryAdapter, final Context context){

        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                categoryModels.add(new CategoryModel(documentSnapshot.get("CateIcon").toString(),documentSnapshot.get("CateName").toString()));
                            }
                            //Luu vao adapter
                            categoryAdapter.notifyDataSetChanged();
                        }
                        else {
                            String message = task.getException().getMessage();
                            Toast.makeText(context, "Error: "  + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void setLayout(final HomePageAdapter homePageAdapter, final Context context, final int index, String cateName){

        firebaseFirestore.collection("CATEGORIES")
                .document(cateName.toUpperCase())
                .collection("TOP_DEALS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                Toast.makeText(context, "Type: " + documentSnapshot.get("view_type"), Toast.LENGTH_SHORT).show();
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
                                                documentSnapshot.get("product_image_"+x).toString(),
                                                documentSnapshot.get("product_fullname_"+x).toString(),
                                                documentSnapshot.get("product_price_"+x).toString(),
                                                documentSnapshot.get("cutted_price_"+x).toString(),
                                                (long)documentSnapshot.get("free_discount_" + x),
                                                (boolean)documentSnapshot.get("cod_" + x),
                                                documentSnapshot.get("average_rating_" + x).toString(),
                                                (long)documentSnapshot.get("total_rating_" + x)));
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
                            homePageAdapter.notifyDataSetChanged();
                        }
                        else {
                            String message = task.getException().getMessage();
                            Toast.makeText(context, "Error: "  + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
