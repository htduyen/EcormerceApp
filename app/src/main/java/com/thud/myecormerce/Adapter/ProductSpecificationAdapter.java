package com.thud.myecormerce.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thud.myecormerce.Models.ProductSpecificationModel;
import com.thud.myecormerce.R;

import java.lang.reflect.Type;
import java.util.List;

public class ProductSpecificationAdapter extends RecyclerView.Adapter<ProductSpecificationAdapter.ViewHolder> {

    private List<ProductSpecificationModel> productSpecificationModelList;

    public ProductSpecificationAdapter(List<ProductSpecificationModel> productSpecificationModelList) {
        this.productSpecificationModelList = productSpecificationModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (productSpecificationModelList.get(position).getType()){
            case 0:
                return ProductSpecificationModel.spec_title;
            case 1:
                return ProductSpecificationModel.spec_body;
            default:
                return -1;
        }
    }

    //Xac định Layout ánh xạ
    @NonNull
    @Override
    public ProductSpecificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType){
            case ProductSpecificationModel.spec_title:
                TextView txt_generate = new TextView(viewGroup.getContext());
                txt_generate.setTypeface(null, Typeface.BOLD);
                txt_generate.setTextColor(Color.parseColor("#000000"));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(getDp(16, viewGroup.getContext()),
                        getDp(16, viewGroup.getContext()),
                        getDp(16, viewGroup.getContext()),
                        getDp(8, viewGroup.getContext()));
                txt_generate.setLayoutParams(layoutParams);
                return new ViewHolder(txt_generate);

            case ProductSpecificationModel.spec_body:
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_specification_item, viewGroup, false);
                return new ViewHolder(view);
            default: return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSpecificationAdapter.ViewHolder viewHolder, int i) {

        switch (productSpecificationModelList.get(i).getType()){
            case ProductSpecificationModel.spec_title:
                viewHolder.setTitle(productSpecificationModelList.get(i).getTitle());
                break;
            case ProductSpecificationModel.spec_body:
                String featureTitle = productSpecificationModelList.get(i).getFeatureName();
                String featureDetail = productSpecificationModelList.get(i).getFeatureValue();
                viewHolder.setFeature(featureTitle, featureDetail);
                break;
            default:
                return;
        }


    }

    @Override
    public int getItemCount() {
        return productSpecificationModelList.size();
    }

    //Xac định, khai báo thành phần trong layout
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView featureName;
        private TextView featureValue;
        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            featureName = itemView.findViewById(R.id.txt_feature_name);
//            featureValue = itemView.findViewById(R.id.txt_feature_value);
        }
        private void setTitle(String txttitle){
            title = (TextView) itemView;
            title.setText(txttitle);
        }
        //Gán dữ liệu
        private void setFeature(String name, String detail){
            featureName = itemView.findViewById(R.id.txt_feature_name);
            featureValue = itemView.findViewById(R.id.txt_feature_value);
            featureName.setText(name);
            featureValue.setText(detail);
        }
    }

    private int getDp(int dp, Context context){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp, context.getResources().getDisplayMetrics());
    }

}
