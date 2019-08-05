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

    //*************Banner Slider***************
    private ViewPager bannerviewpager;
    private List<SliderModel> sliderModelList;
    private int currentPage;
    private Timer timer;
    final private long DELAY_TIME = 3000;
    final private long PERIOD_TIME = 3000;

    //***********Strip ads *******************
    private ImageView imv_strip_ads;
    private ConstraintLayout strip_constrain;
    //***********Strip ads *******************

    //***************** Product Horizon ********************
    private TextView txt_title_pro_horizon;
    private Button btn_viewall_pro_horizon;
    private RecyclerView recyclerView_pro_horizon;

    //***************** Product Horizon ********************

    //***************** Product Product ***********************
    //***************** Product Product ***********************
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
        bannerviewpager = view.findViewById(R.id.banner_viewpager_main);

        sliderModelList = new ArrayList<SliderModel>();

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

        SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
        bannerviewpager.setAdapter(sliderAdapter);
        bannerviewpager.setClipToPadding(false);
        bannerviewpager.setPageMargin(20);

        bannerviewpager.setCurrentItem(currentPage);
        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPage = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (i == ViewPager.SCROLL_STATE_IDLE){
                    LoopPagerBanner();
                }
            }
        };

        bannerviewpager.addOnPageChangeListener(onPageChangeListener);

        startBannerSlideShow();

        bannerviewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                LoopPagerBanner();
                stopBannerSlideShow();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    startBannerSlideShow();
                }
                return false;
            }
        });
        ////////////////////////End BANNER SLIDER

        //***************** Strip ads ********************
        imv_strip_ads = view.findViewById(R.id.imv_strip_main);
        strip_constrain = view.findViewById(R.id.stript_ads_contrain);

        imv_strip_ads.setImageResource(R.drawable.stript1);
        strip_constrain.setBackgroundColor(Color.parseColor("#000000"));
        //*****************End Strip ads ********************

        //***************** Product Horizon ********************
        txt_title_pro_horizon = view.findViewById(R.id.txt_title_pro_horizon);
        btn_viewall_pro_horizon = view.findViewById(R.id.btn_view_all_pro_horizon);
        recyclerView_pro_horizon = view.findViewById(R.id.recyclerview_pro_horizon);

        List<ProductHorizonModel> horizonModels = new ArrayList<>();
        horizonModels.add(new ProductHorizonModel(R.drawable.phone1, "Phone 1", "Descr 1", "2 000 000"));
        horizonModels.add(new ProductHorizonModel(R.drawable.phone2, "Phone 2", "Descr 1", "2 000 000"));
        horizonModels.add(new ProductHorizonModel(R.drawable.phone3, "Phone 3", "Descr 1", "2 000 000"));
        horizonModels.add(new ProductHorizonModel(R.drawable.phone4, "Phone 4", "Descr 1", "2 000 000"));
        horizonModels.add(new ProductHorizonModel(R.drawable.phone5, "Phone 5", "Descr 1", "2 000 000"));
        horizonModels.add(new ProductHorizonModel(R.drawable.phone6, "Phone 6", "Descr 1", "2 000 000"));
        horizonModels.add(new ProductHorizonModel(R.drawable.phone7, "Phone 7", "Descr 1", "2 000 000"));
        horizonModels.add(new ProductHorizonModel(R.drawable.phone1, "Phone 8", "Descr 1", "2 000 000"));

        ProductHorizonAdapter productHorizonAdapter = new ProductHorizonAdapter(horizonModels);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
        recyclerView_pro_horizon.setLayoutManager(layoutManager);

        recyclerView_pro_horizon.setAdapter(productHorizonAdapter);
        productHorizonAdapter.notifyDataSetChanged();
        //*****************End Product Horizon ********************

        //***************** Product Product ***********************
        TextView txt_title_grid_pro = view.findViewById(R.id.txt_title_grid_product);
        Button btn_viewall = view.findViewById(R.id.btn_viewall_grid_product);
        GridView gdv_grid_pro = view.findViewById(R.id.gdv_grid_product);

        gdv_grid_pro.setAdapter(new GridProductAdapter(horizonModels));
        //*****************End Product Product ********************

        //*****************RcuclerView Testing ********************
        RecyclerView recyclerViewTesting = view.findViewById(R.id.recyclerview_testing);
        LinearLayoutManager testingLinearlayout = new LinearLayoutManager(getContext());
        recyclerViewTesting.setLayoutManager(testingLinearlayout);

        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(1, R.drawable.stript1,"#ffff00"));
        homePageModelList.add(new HomePageModel(0, sliderModelList));
        homePageModelList.add(new HomePageModel(0, sliderModelList));
        homePageModelList.add(new HomePageModel(0, sliderModelList));
        homePageModelList.add(new HomePageModel(1, R.drawable.stript1,"#ffff00"));
        homePageModelList.add(new HomePageModel(0, sliderModelList));
        homePageModelList.add(new HomePageModel(0, sliderModelList));

        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        recyclerViewTesting.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //*****************RcuclerView Testing ********************





        return view;
    }
    private  void LoopPagerBanner(){
        if (currentPage == sliderModelList.size() -2 ){
            currentPage = 2;
            bannerviewpager.setCurrentItem(currentPage, false);
        }
        if (currentPage == 1 ){
            currentPage = sliderModelList.size() - 3;
            bannerviewpager.setCurrentItem(currentPage, false);
        }
    }

    private void startBannerSlideShow(){
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPage > sliderModelList.size()){
                    currentPage = 1;
                }
                bannerviewpager.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },DELAY_TIME,PERIOD_TIME);
    }
    private void stopBannerSlideShow() {
        timer.cancel();
    }

}
