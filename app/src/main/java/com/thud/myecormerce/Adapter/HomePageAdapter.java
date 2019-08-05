package com.thud.myecormerce.Adapter;

import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.thud.myecormerce.Models.HomePageModel;
import com.thud.myecormerce.Models.SliderModel;
import com.thud.myecormerce.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {

    List<HomePageModel> homePageModelList = new ArrayList<>();

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
}
