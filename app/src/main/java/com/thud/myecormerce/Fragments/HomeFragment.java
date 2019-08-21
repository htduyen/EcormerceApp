package com.thud.myecormerce.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.thud.myecormerce.Adapter.CategoryAdapter;
import com.thud.myecormerce.Adapter.GridProductAdapter;
import com.thud.myecormerce.Adapter.HomePageAdapter;
import com.thud.myecormerce.Adapter.ProductHorizonAdapter;
import com.thud.myecormerce.Adapter.SliderAdapter;
import com.thud.myecormerce.Models.CategoryModel;
import com.thud.myecormerce.Models.HomePageModel;
import com.thud.myecormerce.Models.ProductHorizonModel;
import com.thud.myecormerce.Models.SliderModel;
import com.thud.myecormerce.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    //RecyclerView
    private RecyclerView recycler_Category;
    private CategoryAdapter categoryAdapter;
    private RecyclerView recyclerViewHome;
    private HomePageAdapter homePageAdapter;
    //*************Banner Slider***************
    private ViewPager bannerviewpager;
    private List<SliderModel> sliderModelList;
    private int currentPage;
    private Timer timer;
    final private long DELAY_TIME = 3000;
    final private long PERIOD_TIME = 3000;

    private List<CategoryModel> categoryModels;
    private FirebaseFirestore firebaseFirestore;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        recycler_Category = view.findViewById(R.id.recyclerview_category);
        //Định chiều cho recyclerview
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_Category.setLayoutManager(linearLayoutManager);


        categoryModels = new ArrayList<CategoryModel>();

        categoryAdapter = new CategoryAdapter(categoryModels);
        recycler_Category.setAdapter(categoryAdapter);


        firebaseFirestore = FirebaseFirestore.getInstance();
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
                            Toast.makeText(getContext(), "Error: "  + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });




        //Banner Slider]]
//        List<SliderModel> sliderModelList = new ArrayList<SliderModel>();
//
//        sliderModelList.add(new SliderModel(R.drawable.banner1, "BADBF7"));
//        sliderModelList.add(new SliderModel(R.drawable.banner2, "#BADBF7"));
//        sliderModelList.add(new SliderModel(R.drawable.banner3, "#BADBF7"));
//        sliderModelList.add(new SliderModel(R.drawable.banner4, "#BADBF7"));
//        sliderModelList.add(new SliderModel(R.drawable.banner5, "#BADBF7"));
//        sliderModelList.add(new SliderModel(R.drawable.banner6, "#BADBF7"));
//        sliderModelList.add(new SliderModel(R.drawable.banner7, "#BADBF7"));
//        sliderModelList.add(new SliderModel(R.drawable.banner8, "#BADBF7"));



        ////////////////////////End BANNER SLIDER

        //***************** Product Horizon ********************

        List<ProductHorizonModel> horizonModels = new ArrayList<>();
        horizonModels.add(new ProductHorizonModel(R.drawable.phone1, "Phone 1", "Descr 1", "2 000 000"));
        horizonModels.add(new ProductHorizonModel(R.drawable.phone2, "Phone 2", "Descr 1", "2 000 000"));
        horizonModels.add(new ProductHorizonModel(R.drawable.phone3, "Phone 3", "Descr 1", "2 000 000"));
        horizonModels.add(new ProductHorizonModel(R.drawable.phone4, "Phone 4", "Descr 1", "2 000 000"));
        horizonModels.add(new ProductHorizonModel(R.drawable.phone5, "Phone 5", "Descr 1", "2 000 000"));
        horizonModels.add(new ProductHorizonModel(R.drawable.phone6, "Phone 6", "Descr 1", "2 000 000"));
        horizonModels.add(new ProductHorizonModel(R.drawable.phone7, "Phone 7", "Descr 1", "2 000 000"));
        horizonModels.add(new ProductHorizonModel(R.drawable.phone1, "Phone 8", "Descr 1", "2 000 000"));
        //*****************End Product Horizon ********************

        //***************** Product Product ***********************


        //*****************RcuclerView Testing ********************
        recyclerViewHome = view.findViewById(R.id.home_page_recyclerview);
        LinearLayoutManager testingLinearlayout = new LinearLayoutManager(getContext());
        recyclerViewHome.setLayoutManager(testingLinearlayout);

        final List<HomePageModel> homePageModelList = new ArrayList<>();
//        homePageModelList.add(new HomePageModel(1, R.drawable.stript1,"#ffff00"));
//        homePageModelList.add(new HomePageModel(0, sliderModelList));
//        homePageModelList.add(new HomePageModel(2,"Sales Product", horizonModels));
//        homePageModelList.add(new HomePageModel(3,"Phone Product", horizonModels));
//        homePageModelList.add(new HomePageModel(3,"Shoes Product", horizonModels));
//        homePageModelList.add(new HomePageModel(3,"Laptop Product", horizonModels));
        homePageAdapter = new HomePageAdapter(homePageModelList);
        recyclerViewHome.setAdapter(homePageAdapter);

        firebaseFirestore.collection("CATEGORIES")
                .document("HOME")
                .collection("TOP_DEALS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                if((long)documentSnapshot.get("view_type") == 0){
                                    List<SliderModel> sliderModelList= new ArrayList<>();
                                    long num_slider =(long) documentSnapshot.get("no_of_banner");
                                    for(long x = 1; x < num_slider + 1; x++){
                                        sliderModelList.add(new SliderModel(documentSnapshot.get("banner"+ x).toString(),documentSnapshot.get("banner_bg_"+x).toString()));
                                    }
                                    Toast.makeText(getContext(), "Size: " + sliderModelList.size(), Toast.LENGTH_SHORT).show();
                                    homePageModelList.add(new HomePageModel(0, sliderModelList));
                                }
                                else if((long)documentSnapshot.get("view_type") == 1){
                                    homePageModelList.add(new HomePageModel(1, documentSnapshot.get("strip_ads_img").toString(),documentSnapshot.get("background").toString()));
                                }
                                else if((long)documentSnapshot.get("view_type") == 2){

                                }
                                else if((long)documentSnapshot.get("view_type") == 3){

                                }
                            }
                            //Luu vao adapter
                            homePageAdapter.notifyDataSetChanged();
                        }
                        else {
                            String message = task.getException().getMessage();
                            Toast.makeText(getContext(), "Error: "  + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        //*****************RcuclerView Tes]]]]]]g ********************
        return view;
    }
}
