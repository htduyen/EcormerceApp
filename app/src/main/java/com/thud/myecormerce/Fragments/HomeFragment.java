package com.thud.myecormerce.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
    private RecyclerView recyclerViewTesting;
    //*************Banner Slider***************
    private ViewPager bannerviewpager;
    private List<SliderModel> sliderModelList;
    private int currentPage;
    private Timer timer;
    final private long DELAY_TIME = 3000;
    final private long PERIOD_TIME = 3000;


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

        List<CategoryModel> categoryModels = new ArrayList<CategoryModel>();
        categoryModels.add(new CategoryModel("link", "Home"));
        categoryModels.add(new CategoryModel("link", "Fashions"));
        categoryModels.add(new CategoryModel("link", "Toys"));
        categoryModels.add(new CategoryModel("link", "Sports"));
        categoryModels.add(new CategoryModel("link", "Books"));
        categoryModels.add(new CategoryModel("link", "Shoes"));
        categoryModels.add(new CategoryModel("link", "Electronics"));

        categoryAdapter = new CategoryAdapter(categoryModels);
        recycler_Category.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        //Banner Slider
        List<SliderModel> sliderModelList = new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.drawable.banner5, "#BADBF7"));
        sliderModelList.add(new SliderModel(R.drawable.banner6, "#BADBF7"));
        sliderModelList.add(new SliderModel(R.drawable.banner7, "#BADBF7"));
        sliderModelList.add(new SliderModel(R.drawable.banner8, "#BADBF7"));

        sliderModelList.add(new SliderModel(R.drawable.banner1, "#BADBF7"));
        sliderModelList.add(new SliderModel(R.drawable.banner2, "#BADBF7"));
        sliderModelList.add(new SliderModel(R.drawable.banner3, "#BADBF7"));
        sliderModelList.add(new SliderModel(R.drawable.banner4, "#BADBF7"));
        sliderModelList.add(new SliderModel(R.drawable.banner5, "#BADBF7"));
        sliderModelList.add(new SliderModel(R.drawable.banner6, "#BADBF7"));
        sliderModelList.add(new SliderModel(R.drawable.banner7, "#BADBF7"));
        sliderModelList.add(new SliderModel(R.drawable.banner8, "#BADBF7"));


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
        recyclerViewTesting = view.findViewById(R.id.home_page_recyclerview);
        LinearLayoutManager testingLinearlayout = new LinearLayoutManager(getContext());
        recyclerViewTesting.setLayoutManager(testingLinearlayout);

        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(1, R.drawable.stript1,"#ffff00"));
        homePageModelList.add(new HomePageModel(0, sliderModelList));
        homePageModelList.add(new HomePageModel(2,"Sales Product", horizonModels));
        homePageModelList.add(new HomePageModel(3,"Phone Product", horizonModels));
        homePageModelList.add(new HomePageModel(3,"Shoes Product", horizonModels));
        homePageModelList.add(new HomePageModel(3,"Laptop Product", horizonModels));


        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        recyclerViewTesting.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //*****************RcuclerView Testing ********************
        return view;
    }
}
