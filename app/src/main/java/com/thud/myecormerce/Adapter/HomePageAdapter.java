package com.thud.myecormerce.Adapter;

import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.thud.myecormerce.Models.HomePageModel;
import com.thud.myecormerce.Models.ProductHorizonModel;
import com.thud.myecormerce.Models.SliderModel;
import com.thud.myecormerce.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {

    private List<HomePageModel> homePageModelList;
    //<HomePageModel> homePageModelList = new ArrayList<>();

    //Tao constructor
    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
    }
    //Ham co san
    @Override
    public int getItemViewType(int position) {

        switch (homePageModelList.get(position).getType()){
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.STRIP_ADS;
            case 2:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;
            case 3:
                return HomePageModel.GRID_PRODUCT_VIEW;
            default:
                    return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType){
            case HomePageModel.BANNER_SLIDER:
                View sliderview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sliding_ads_layout, viewGroup,false);
                return new BannerSliderViewHolder(sliderview);
            case HomePageModel.STRIP_ADS:
                View stripview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stript_ads_layout, viewGroup,false);
                return new StripAdsViewHolder(stripview);
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                View horizontalproductview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_horizon_layout, viewGroup,false);
                return new HorizontalProductViewHolder(horizontalproductview);
            case HomePageModel.GRID_PRODUCT_VIEW:
                View gridproductview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_product_layout, viewGroup,false);
                return new GridPoductViewHolder(gridproductview);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (homePageModelList.get(i).getType()){
            case HomePageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList = homePageModelList.get(i).getSliderModelList();
                ((BannerSliderViewHolder) viewHolder).setBannerSliderViewPager(sliderModelList);
                break;
            case HomePageModel.STRIP_ADS:
                int resource = homePageModelList.get(i).getResource();
                String color = homePageModelList.get(i).getBackgroundColor();
                ((StripAdsViewHolder) viewHolder).setStripAds(resource,color);
                break;
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String horizontitle = homePageModelList.get(i).getTitle();
                List<ProductHorizonModel> horizontalProductViewList = homePageModelList.get(i).getProductHorizonModelList();
                ((HorizontalProductViewHolder) viewHolder).setHorizontalProductLayout(horizontalProductViewList, horizontitle);
            case HomePageModel.GRID_PRODUCT_VIEW:
                String gridtitle = homePageModelList.get(i).getTitle();
                List<ProductHorizonModel> GridProductViewList = homePageModelList.get(i).getProductHorizonModelList();

                try{
                    ((GridPoductViewHolder) viewHolder).setGridProductLayout(GridProductViewList, gridtitle);
                } catch (Exception ex){
                    Log.d("debug", "onBindViewHolder: " + ex);
                }
//                ((GridPoductViewHolder) viewHolder).setGridProductLayout(GridProductViewList, gridtitle);

            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    //Tao
    public class BannerSliderViewHolder extends RecyclerView.ViewHolder {

        private ViewPager bannerviewpager;
        private List<SliderModel> sliderModelList;
        private int currentPage;
        private Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;

        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerviewpager = itemView.findViewById(R.id.banner_viewpager_main);


        }
        private void setBannerSliderViewPager(final List<SliderModel> sliderModelList){
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
                        LoopPagerBanner(sliderModelList);
                    }
                }
            };

            bannerviewpager.addOnPageChangeListener(onPageChangeListener);

            startBannerSlideShow(sliderModelList);

            bannerviewpager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    LoopPagerBanner(sliderModelList);
                    stopBannerSlideShow();
                    if(event.getAction() == MotionEvent.ACTION_UP){
                        startBannerSlideShow(sliderModelList);
                    }
                    return false;
                }
            });
        }
        private  void LoopPagerBanner(List<SliderModel> sliderModelList){
            if (currentPage == sliderModelList.size() -2 ){
                currentPage = 2;
                bannerviewpager.setCurrentItem(currentPage, false);
            }
            if (currentPage == 1 ){
                currentPage = sliderModelList.size() - 3;
                bannerviewpager.setCurrentItem(currentPage, false);
            }
        }

        private void startBannerSlideShow(final List<SliderModel> sliderModelList){
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

    public class StripAdsViewHolder extends RecyclerView.ViewHolder {

        private ImageView imv_strip_ads;
        private ConstraintLayout strip_constrain;

        public StripAdsViewHolder(@NonNull View itemView) {
            super(itemView);
            imv_strip_ads = itemView.findViewById(R.id.imv_strip_main);
            strip_constrain = itemView.findViewById(R.id.stript_ads_contrain);
        }

        private void setStripAds(int resource, String color){
            imv_strip_ads.setImageResource(resource);
            strip_constrain.setBackgroundColor(Color.parseColor(color));
        }
    }

    public class HorizontalProductViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_title_pro_horizon;
        private Button btn_viewall_pro_horizon;
        private RecyclerView recyclerView_pro_horizon;

        public HorizontalProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title_pro_horizon = itemView.findViewById(R.id.txt_title_pro_horizon);
            btn_viewall_pro_horizon = itemView.findViewById(R.id.btn_view_all_pro_horizon);
            recyclerView_pro_horizon = itemView.findViewById(R.id.recyclerview_pro_horizon);
        }
        private void setHorizontalProductLayout(List<ProductHorizonModel>  horizonModels, String title){

            txt_title_pro_horizon.setText(title);

            if(horizonModels.size() > 8){
                btn_viewall_pro_horizon.setVisibility(View.VISIBLE);
            }
            else {
                btn_viewall_pro_horizon.setVisibility(View.INVISIBLE);
            }

            ProductHorizonAdapter productHorizonAdapter = new ProductHorizonAdapter(horizonModels);
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

            recyclerView_pro_horizon.setLayoutManager(layoutManager);
            recyclerView_pro_horizon.setAdapter(productHorizonAdapter);
            productHorizonAdapter.notifyDataSetChanged();
        }
    }

    public class GridPoductViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_title_grid_pro;
        private Button btn_viewall;
        private GridView gdv_grid_pro;

        public GridPoductViewHolder(@NonNull View itemView) {
             super(itemView);
             txt_title_grid_pro = itemView.findViewById(R.id.txt_title_grid_product);
             btn_viewall = itemView.findViewById(R.id.btn_viewall_grid_product);
             gdv_grid_pro = itemView.findViewById(R.id.gdv_grid_product);
        }

        private void setGridProductLayout(List<ProductHorizonModel> horizonModelList, String title){

            txt_title_grid_pro.setText(title);
            GridProductAdapter gridProductAdapter = new GridProductAdapter(horizonModelList);
            gdv_grid_pro.setAdapter(gridProductAdapter);
        }
    }
}
