package com.thud.myecormerce.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thud.myecormerce.Models.ProductHorizonModel;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.ProductDetailActivity;

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
        String resource = productHorizonModelList.get(i).getProductImv();
        String name = productHorizonModelList.get(i).getProductName();
        String descr = productHorizonModelList.get(i).getProductDescription();
        String price = productHorizonModelList.get(i).getProductPrice();
        String product_id = productHorizonModelList.get(i).getProductID();

        viewHolder.setData(product_id, resource, name, descr, price);
    }

    @Override
    public int getItemCount() {

        if(productHorizonModelList.size() > 8 ){
            return 8;
        }
        else {
            return productHorizonModelList.size();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imv_product;
        private TextView txt_name_pro;
        private TextView txt_descr;
        private TextView txt_price;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            imv_product = itemView.findViewById(R.id.imv_pro_horizon);
            txt_name_pro = itemView.findViewById(R.id.txt_name_pro_horizon);
            txt_descr = itemView.findViewById(R.id.txt_descr_pro_horizon);
            txt_price = itemView.findViewById(R.id.txt_price_pro_horizon);



        }
        private void setData(final String product_id, String resource, String name, String descr, String price){
//            imv_product.setImageResource(resource);
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.image_place)).into(imv_product);
            txt_name_pro.setText(name);
            txt_descr.setText(descr);
            txt_price.setText(price+ " đ");
            if(!name.equals("")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent_product_detail = new Intent(itemView.getContext(), ProductDetailActivity.class);
                        intent_product_detail.putExtra("PRODUCT_ID", product_id);
                        itemView.getContext().startActivity(intent_product_detail);
                    }
                });
            }
        }
    }
}
