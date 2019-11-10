package com.thud.myecormerce.Fragments;


import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.bumptech.glide.Glide;
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
import com.thud.myecormerce.Models.WishlistModel;
import com.thud.myecormerce.Presenter.DbQueries;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.thud.myecormerce.Presenter.DbQueries.categoryModels;
import static com.thud.myecormerce.Presenter.DbQueries.firebaseFirestore;
import static com.thud.myecormerce.Presenter.DbQueries.listNameCategories;
import static com.thud.myecormerce.Presenter.DbQueries.lists;
import static com.thud.myecormerce.Presenter.DbQueries.loadCategories;
import static com.thud.myecormerce.Presenter.DbQueries.setLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    //RecyclerView
    private RecyclerView recycler_Category;
    private CategoryAdapter categoryAdapter;

    private RecyclerView recyclerViewHome;
    private List<HomePageModel> homePageModePlacelList = new ArrayList<>();
    private HomePageAdapter homePageAdapter;

    private Button btn_retry;

    public static SwipeRefreshLayout swipeRefreshLayout;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    private ImageView imv_noInternet;
    //*************Banner Slider***************
    private ViewPager bannerviewpager;
    private List<SliderModel> sliderModelList;
    private List<CategoryModel> categoryModePlacelList = new ArrayList<>();
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
        swipeRefreshLayout = view.findViewById(R.id.refreshLayout_home);
        swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorPrimary),getContext().getResources().getColor(R.color.colorPrimary),getContext().getResources().getColor(R.color.colorPrimary));
        imv_noInternet = view.findViewById(R.id.no_internet_main);
        recyclerViewHome = view.findViewById(R.id.home_page_recyclerview);
        recycler_Category = view.findViewById(R.id.recyclerview_category);
        btn_retry = view.findViewById(R.id.btn_retry_home);


        connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_Category.setLayoutManager(linearLayoutManager);

        LinearLayoutManager testingLinearlayout = new LinearLayoutManager(getContext());
        testingLinearlayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewHome.setLayoutManager(testingLinearlayout);


        // Category frefresh
        categoryModePlacelList.add(new CategoryModel("null", ""));
        categoryModePlacelList.add(new CategoryModel("", ""));
        categoryModePlacelList.add(new CategoryModel("", ""));
        categoryModePlacelList.add(new CategoryModel("", ""));
        categoryModePlacelList.add(new CategoryModel("", ""));
        categoryModePlacelList.add(new CategoryModel("", ""));
        categoryModePlacelList.add(new CategoryModel("", ""));
        // HomePage frefresh
        List<SliderModel> sliderModelListPlace = new ArrayList<>();
        sliderModelListPlace.add(new SliderModel("", "#dfdfdf"));
        sliderModelListPlace.add(new SliderModel("", "#dfdfdf"));
        sliderModelListPlace.add(new SliderModel("", "#dfdfdf"));
        sliderModelListPlace.add(new SliderModel("", "#dfdfdf"));
        sliderModelListPlace.add(new SliderModel("", "#dfdfdf"));
        sliderModelListPlace.add(new SliderModel("", "#dfdfdf"));
        sliderModelListPlace.add(new SliderModel("", "#dfdfdf"));
        sliderModelListPlace.add(new SliderModel("", "#dfdfdf"));

        List<ProductHorizonModel> productHorizonModelListPlace = new ArrayList<>();
        productHorizonModelListPlace.add(new ProductHorizonModel("","","","",""));
        productHorizonModelListPlace.add(new ProductHorizonModel("","","","",""));
        productHorizonModelListPlace.add(new ProductHorizonModel("","","","",""));
        productHorizonModelListPlace.add(new ProductHorizonModel("","","","",""));
        productHorizonModelListPlace.add(new ProductHorizonModel("","","","",""));
        productHorizonModelListPlace.add(new ProductHorizonModel("","","","",""));
        productHorizonModelListPlace.add(new ProductHorizonModel("","","","",""));

        homePageModePlacelList.add(new HomePageModel(0, sliderModelListPlace));
        homePageModePlacelList.add(new HomePageModel(1,"" ,"#ffffff"));
        homePageModePlacelList.add(new HomePageModel(2,"", "#ffffff",productHorizonModelListPlace, new ArrayList<WishlistModel>()));
        homePageModePlacelList.add(new HomePageModel(3, "", "#ffffff",productHorizonModelListPlace));
        // HomePage frefresh

        categoryAdapter = new CategoryAdapter(categoryModePlacelList);
        homePageAdapter = new HomePageAdapter(homePageModePlacelList);

        //homePageAdapter = new HomePageAdapter(lists.get(0));


        if(networkInfo != null && networkInfo.isConnected() == true){
            MainActivity.drawer.setDrawerLockMode(0);
            btn_retry.setVisibility(View.GONE);
            imv_noInternet.setVisibility(View.GONE);
            recycler_Category.setVisibility(View.VISIBLE);
            recyclerViewHome.setVisibility(View.VISIBLE);

            if(categoryModels.size() == 0){
                loadCategories(recycler_Category,getContext());
            }
            else {
                categoryAdapter = new CategoryAdapter(categoryModels);
                categoryAdapter.notifyDataSetChanged();
            }
            recycler_Category.setAdapter(categoryAdapter);

            if(lists.size() == 0){
                listNameCategories.add("HOME");
                lists.add(new ArrayList<HomePageModel>());
                //homePageAdapter = new HomePageAdapter(lists.get(0)); // nho xoa
                setLayout(recyclerViewHome,getContext(), 0,"Home");
            }
            else {
                //chu y
                homePageAdapter = new HomePageAdapter(lists.get(0));
                homePageAdapter.notifyDataSetChanged();
            }
            recyclerViewHome.setAdapter(homePageAdapter);
        }
        else {
            MainActivity.drawer.setDrawerLockMode(1);
            recycler_Category.setVisibility(View.GONE);
            recyclerViewHome.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.no_wifi64).into(imv_noInternet);
            imv_noInternet.setVisibility(View.VISIBLE);
            btn_retry.setVisibility(View.VISIBLE);
        }
        //*****************RcuclerView Tes]]]]]]g ********************
        //Swipe RefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                //swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorPrimary), getContext().getResources().getColor(R.color.colorPrimary));
                reloadPage();
            }
        });
        //Swipe RefreshLayout

        btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadPage();
            }
        });
        return view;
    }

    private void reloadPage(){
        networkInfo = connectivityManager.getActiveNetworkInfo();
//        categoryModels.clear();
//        lists.clear();
//        listNameCategories.clear();
        DbQueries.clearData();

        if(networkInfo != null && networkInfo.isConnected() == true) {
            MainActivity.drawer.setDrawerLockMode(0);
            imv_noInternet.setVisibility(View.GONE);
            btn_retry.setVisibility(View.GONE);
            recycler_Category.setVisibility(View.VISIBLE);
            recyclerViewHome.setVisibility(View.VISIBLE);

            categoryAdapter = new CategoryAdapter(categoryModePlacelList);
            homePageAdapter= new HomePageAdapter(homePageModePlacelList);
            recycler_Category.setAdapter(categoryAdapter);
            recyclerViewHome.setAdapter(homePageAdapter);

            loadCategories(recycler_Category,getContext());

            listNameCategories.add("HOME");
            lists.add(new ArrayList<HomePageModel>());
            setLayout(recyclerViewHome,getContext(), 0,"Home");

        }
        else {
            MainActivity.drawer.setDrawerLockMode(1);
            Toast.makeText(getContext(), "Không có kết nối Internet!", Toast.LENGTH_SHORT).show();
            recycler_Category.setVisibility(View.GONE);
            recyclerViewHome.setVisibility(View.GONE);
            Glide.with(getContext()).load(R.drawable.no_wifi64).into(imv_noInternet);
            imv_noInternet.setVisibility(View.VISIBLE);
            btn_retry.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
