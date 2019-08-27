package com.thud.myecormerce.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thud.myecormerce.Fragments.ProductDescriptionFragment;
import com.thud.myecormerce.Fragments.ProductSpecificationFragment;
import com.thud.myecormerce.Models.ProductSpecificationModel;

import java.util.List;

public class ProductDetailAdapter extends FragmentPagerAdapter {

    private  int total;
    private String productDescription;
    private String productOtherDetail;
    private List<ProductSpecificationModel> productSpecificationModelList;

    public ProductDetailAdapter(FragmentManager fm, int total ,String productDescription, String productOtherDetail, List<ProductSpecificationModel> productSpecificationModelList) {
        super(fm);
        this.productDescription = productDescription;
        this.productOtherDetail = productOtherDetail;
        this.productSpecificationModelList = productSpecificationModelList;
        this.total = total;
    }


    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                ProductDescriptionFragment productDescriptionFragment1 = new ProductDescriptionFragment();
                productDescriptionFragment1.body = productDescription;
                return  productDescriptionFragment1;
            case 1:
                ProductSpecificationFragment specificationFragment = new ProductSpecificationFragment();
                specificationFragment.productSpecificationModelList = productSpecificationModelList;
                return  specificationFragment ;

            case 2:
                ProductDescriptionFragment productDescriptionFragment2 = new ProductDescriptionFragment();
                productDescriptionFragment2.body = productOtherDetail;
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
