package com.thud.myecormerce.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thud.myecormerce.R;

import java.util.List;

public class ProductImageAdapter extends PagerAdapter {

    List<String> productImages;

    public ProductImageAdapter(List<String> productImages) {
        this.productImages = productImages;
    }



    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView product_imv = new ImageView(container.getContext());
//        product_imv.setImageResource(productImages.get(position));
        Glide.with(container.getContext()).load(productImages.get(position)).apply(new RequestOptions().placeholder(R.drawable.image_place)).into(product_imv);
        container.addView(product_imv, 0);
        return product_imv;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);

    }

    @Override
    public int getCount() {
        return productImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
