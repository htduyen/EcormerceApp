package com.thud.myecormerce.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thud.myecormerce.Models.ProductHorizonModel;
import com.thud.myecormerce.R;

import java.util.List;

public class ProductHorizonAdapter extends RecyclerView.Adapter<ProductHorizonAdapter.ViewHolder> {

    private List<ProductHorizonModel> productHorizonModelList;

    //Tạo constructor


    public ProductHorizonAdapter(List<ProductHorizonModel> productHorizonModelList) {
        this.productHorizonModelList = productHorizonModelList;
    }


    @NonNull
    @Override
    public ProductHorizonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_horizon_item_layout, viewGroup, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHorizonAdapter.ViewHolder viewHolder, int i) {
        int resource = productHorizonModelList.get(i).getProductImv();
        String name = productHorizonModelList.get(i).getProductName();
        String descr = productHorizonModelList.get(i).getProductDescription();
        String price = productHorizonModelList.get(i).getProductPrice();

        viewHolder.setImv_product(resource);
        viewHolder.setTxt_descr(descr);
        viewHolder.setTxt_name_pro(name);
        viewHolder.setTxt_price(price);
    }

    @Override
    public int getItemCount() {
        return productHorizonModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imv_product;
        private TextView txt_name_pro;
        private TextView txt_descr;
        private TextView txt_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imv_product = itemView.findViewById(R.id.imv_pro_horizon);
            txt_name_pro = itemView.findViewById(R.id.txt_name_pro_horizon);
            txt_descr = itemView.findViewById(R.id.txt_descr_pro_horizon);
            txt_price = itemView.findViewById(R.id.txt_price_pro_horizon);

        }
        private void setImv_product(int resource){
            imv_product.setImageResource(resource);
        }
        private void setTxt_name_pro(String name){
            txt_name_pro.setText(name);
        }
        private void setTxt_descr(String descr){
            txt_descr.setText(descr);
        }
        private void setTxt_price(String price){
            txt_price.setText(price);
        }
    }
}
