package com.thud.myecormerce.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thud.myecormerce.Models.ProductHorizonModel;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.ProductDetailActivity;

import java.util.List;

public class GridProductAdapter extends BaseAdapter {

    List<ProductHorizonModel> productHorizonModelList;

    //Tao constructor


    public GridProductAdapter(List<ProductHorizonModel> productHorizonModelList) {
        this.productHorizonModelList = productHorizonModelList;
    }



    @Override
    public int getCount() {
        return productHorizonModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view;
        if(convertView == null ){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_horizon_item_layout, null);
            view.setElevation(0);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productdetailintent = new Intent(parent.getContext(), ProductDetailActivity.class);
                    productdetailintent.putExtra("PRODUCT_ID", productHorizonModelList.get(position).getProductID());
                    String id = productHorizonModelList.get(position).getProductID();

                    parent.getContext().startActivity(productdetailintent);
                }
            });
            ImageView imv_product = view.findViewById(R.id.imv_pro_horizon);
            TextView txt_name = view.findViewById(R.id.txt_name_pro_horizon);
            TextView txt_descr = view.findViewById(R.id.txt_descr_pro_horizon);
            TextView txt_price = view.findViewById(R.id.txt_price_pro_horizon);

//            imv_product.setImageResource(productHorizonModelList.get(position).getProductImv());
            Glide.with(parent.getContext()).load(productHorizonModelList.get(position).getProductImv()).apply(new RequestOptions().placeholder(R.drawable.image_place)).into(imv_product);
            txt_name.setText(productHorizonModelList.get(position).getProductName());
            txt_descr.setText(productHorizonModelList.get(position).getProductDescription());
            txt_price.setText(productHorizonModelList.get(position).getProductPrice()+" Đ");


        }
        else
        {
            view = convertView;
        }

        return view;
    }
}
