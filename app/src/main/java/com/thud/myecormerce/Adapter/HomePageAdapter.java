package com.thud.myecormerce.Adapter;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thud.myecormerce.Models.HomePageModel;
import com.thud.myecormerce.Models.ProductHorizonModel;
import com.thud.myecormerce.Models.SliderModel;
import com.thud.myecormerce.Models.WishlistModel;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.ProductDetailActivity;
import com.thud.myecormerce.View.ViewAllActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {

    private List<HomePageModel> homePageModelList;
    private  RecyclerView.RecycledViewPool recycledViewPool;
    private int lastposition = -1;

    //Tao constructor
    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
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
                String resource = homePageModelList.get(i).getResource();
                String color = homePageModelList.get(i).getBackgroundColor();
                ((StripAdsViewHolder) viewHolder).setStripAds(resource,color);
                break;
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String bg_color = homePageModelList.get(i).getBackgroundColor();
                String horizontitle = homePageModelList.get(i).getTitle();
                List<WishlistModel> wishlistViewAllModelList = homePageModelList.get(i).getWishlistViewAllModelList();
                List<ProductHorizonModel> horizontalProductViewList = homePageModelList.get(i).getProductHorizonModelList();
                ((HorizontalProductViewHolder) viewHolder).setHorizontalProductLayout(horizontalProductViewList, horizontitle, bg_color, wishlistViewAllModelList);
            case HomePageModel.GRID_PRODUCT_VIEW:
                String gridtitle = homePageModelList.get(i).getTitle();
                List<ProductHorizonModel> GridProductViewList = homePageModelList.get(i).getProductHorizonModelList();
                String grid_color = homePageModelList.get(i).getBackgroundColor();
                try{

                    ((GridPoductViewHolder) viewHolder).setGridProductLayout(GridProductViewList, gridtitle, grid_color);
                } catch (Exception ex){
                    Log.d("debug", "onBindViewHolder: " + ex);
                }
//                ((GridPoductViewHolder) viewHolder).setGridProductLayout(GridProductViewList, gridtitle);

            default:
                return;
        }
        if(lastposition < i) {
            Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.fade_in);
            viewHolder.itemView.setAnimation(animation);
            lastposition = i;
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
        final private long DELAY_TIME = 2000;
        final private long PERIOD_TIME = 2000;
        private List<SliderModel> arrangeList;

        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerviewpager = itemView.findViewById(R.id.banner_viewpager_main);


        }
        private void setBannerSliderViewPager(final List<SliderModel> sliderModelList){

            currentPage = 2;
            if(timer != null){
                timer.cancel();
            }
            arrangeList = new ArrayList<>();
            for(int x = 0; x < sliderModelList.size(); x++){
                arrangeList.add(x, sliderModelList.get(x));
            }
  //          Toast.makeText(itemView.getContext(), "Length: " + sliderModelList.size(), Toast.LENGTH_SHORT).show();
            arrangeList.add(0, sliderModelList.get(sliderModelList.size() -2 ));
            arrangeList.add(1, sliderModelList.get(sliderModelList.size() -1 ));
            arrangeList.add(sliderModelList.get(0));
            arrangeList.add(sliderModelList.get(1));

            SliderAdapter sliderAdapter = new SliderAdapter(arrangeList);
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
                        LoopPagerBanner(arrangeList);
                    }
                }
            };

            bannerviewpager.addOnPageChangeListener(onPageChangeListener);

            startBannerSlideShow(arrangeList);

            bannerviewpager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    LoopPagerBanner(arrangeList);
                    stopBannerSlideShow();
                    if(event.getAction() == MotionEvent.ACTION_UP){
                        startBannerSlideShow(arrangeList);
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

        private void setStripAds(String resource, String color){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.banner_place)).into(imv_strip_ads);
//            imv_strip_ads.setImageResource(resource);
              strip_constrain.setBackgroundColor(Color.parseColor(color));
        }
    }

    public class HorizontalProductViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout constraintLayout_horizon;
        private TextView txt_title_pro_horizon;
        private Button btn_viewall_pro_horizon;
        private RecyclerView recyclerView_pro_horizon;

        public HorizontalProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title_pro_horizon = itemView.findViewById(R.id.txt_title_pro_horizon);
            btn_viewall_pro_horizon = itemView.findViewById(R.id.btn_view_all_pro_horizon);
            recyclerView_pro_horizon = itemView.findViewById(R.id.recyclerview_pro_horizon);
            constraintLayout_horizon = itemView.findViewById(R.id.contrain_product_horizon);
            recyclerView_pro_horizon.setRecycledViewPool(recycledViewPool);
        }
        private void setHorizontalProductLayout(List<ProductHorizonModel>  horizonModels, final String title, String color, final List<WishlistModel> wishlistModelList){

            txt_title_pro_horizon.setText(title);
            constraintLayout_horizon.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            if(horizonModels.size() >= 8){
                btn_viewall_pro_horizon.setVisibility(View.VISIBLE);
                btn_viewall_pro_horizon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllActivity.wishlistModelList = wishlistModelList;
                        Intent intentViewAll = new Intent(itemView.getContext(), ViewAllActivity.class);
                        intentViewAll.putExtra("view_all_code", 0);
                        intentViewAll.putExtra("title_group",title);
                        itemView.getContext().startActivity(intentViewAll);
                    }
                });
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
        private GridLayout gridLayout;
        private ConstraintLayout constraintLayout_gridproduct;

        public GridPoductViewHolder(@NonNull View itemView) {
             super(itemView);

             txt_title_grid_pro = itemView.findViewById(R.id.txt_title_grid_product);
             btn_viewall = itemView.findViewById(R.id.btn_viewall_grid_product);
             gridLayout = itemView.findViewById(R.id.grid_layout);
             constraintLayout_gridproduct = itemView.findViewById(R.id.contrain_grid_products);
        }

        private void setGridProductLayout(final List<ProductHorizonModel> horizonModelList, final String title, String color){

            txt_title_grid_pro.setText(title);
            constraintLayout_gridproduct.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));

            for (int x = 0; x < 4; x++){
                ImageView productImage = gridLayout.getChildAt(x).findViewById(R.id.imv_pro_horizon);
                TextView productName = gridLayout.getChildAt(x).findViewById(R.id.txt_name_pro_horizon);
                TextView productDescription = gridLayout.getChildAt(x).findViewById(R.id.txt_descr_pro_horizon);
                TextView productPrice = gridLayout.getChildAt(x).findViewById(R.id.txt_price_pro_horizon);

                //productImage.setImageResource(horizonModelList.get(x).getProductImv());
                Glide.with(itemView.getContext()).load(horizonModelList.get(x).getProductImv()).apply(new RequestOptions().placeholder(R.drawable.image_place)).into(productImage);
                productName.setText(horizonModelList.get(x).getProductName());
                productDescription.setText(horizonModelList.get(x).getProductDescription());
                productPrice.setText(horizonModelList.get(x).getProductPrice() + " Đ");

                gridLayout.getChildAt(x).setBackgroundColor(Color.parseColor("#ffffff"));
                if(!title.equals("")) {
                    final int finalx = x;
                    gridLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intentProductDetail = new Intent(itemView.getContext(), ProductDetailActivity.class);
                            intentProductDetail.putExtra("PRODUCT_ID", horizonModelList.get(finalx).getProductID());
                            itemView.getContext().startActivity(intentProductDetail);
                        }
                    });
                }
            }
            //GridProductAdapter gridProductAdapter = new GridProductAdapter(horizonModelList);

            if(!title.equals("")) {
                btn_viewall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllActivity.productHorizonModelList = horizonModelList;
                        Intent intentViewAll = new Intent(itemView.getContext(), ViewAllActivity.class);
                        intentViewAll.putExtra("view_all_code", 1);
                        intentViewAll.putExtra("group_product_title", title);
                        itemView.getContext().startActivity(intentViewAll);

                    }
                });
            }
        }
    }
}
