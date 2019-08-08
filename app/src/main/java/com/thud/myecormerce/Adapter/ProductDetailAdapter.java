package com.thud.myecormerce.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thud.myecormerce.Fragments.ProductDescriptionFragment;
import com.thud.myecormerce.Fragments.ProductSpecificationFragment;

public class ProductDetailAdapter extends FragmentPagerAdapter {

    private  int total;

    public ProductDetailAdapter(FragmentManager fm, int total) {
        super(fm);
        this.total = total;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                ProductDescriptionFragment productDescriptionFragment1 = new ProductDescriptionFragment();
                return  productDescriptionFragment1;
            case 1:
                ProductSpecificationFragment specificationFragment = new ProductSpecificationFragment();
                return  specificationFragment ;

            case 2:
                ProductDescriptionFragment productDescriptionFragment2 = new ProductDescriptionFragment();
                return  productDescriptionFragment2;
            default:
                return null;


        }
    }

    @Override
    public int getCount() {
        return total;
    }
}
