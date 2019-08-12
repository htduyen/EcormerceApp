package com.thud.myecormerce.Adapter;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thud.myecormerce.Models.MyOrderItemModel;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.OrderDateilsActivity;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    private  List<MyOrderItemModel> myOrderItemModelList;

    public MyOrderAdapter(List<MyOrderItemModel> myOrderItemModelList) {
        this.myOrderItemModelList = myOrderItemModelList;
    }


    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_order_item_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder viewHolder, int i) {
        int resource = myOrderItemModelList.get(i).getProductImage();
        int rating = myOrderItemModelList.get(i).getNumRating();
        String name = myOrderItemModelList.get(i).getProductName();
        String status = myOrderItemModelList.get(i).getDiliveryStatus();

        viewHolder.setData(resource, name,status,rating);
    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private ImageView dilivertyIndicator;
        private TextView productName;
        private  TextView diliveryStatus;
        private LinearLayout rating;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imv_product_myorder);
            productName = itemView.findViewById(R.id.txt_product_name_myorder);
            dilivertyIndicator = itemView.findViewById(R.id.imv_order_status);
            diliveryStatus= itemView.findViewById(R.id.txt_time_dilivered);
            rating = itemView.findViewById(R.id.linear_rating_now);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentMyOrderDetail = new Intent(itemView.getContext(), OrderDateilsActivity.class);
                    itemView.getContext().startActivity(intentMyOrderDetail);
                }
            });
        }

        private void setData(int resource, String name, String status_date, int numRating)
        {
            productImage.setImageResource(resource);
            productName.setText(name);
            if(status_date.equals("Cancelled")){
                //Lưu ý getColor
                ColorStateList xam = AppCompatResources.getColorStateList(itemView.getContext(), R.color.colorRed);
                ImageViewCompat.setImageTintList(dilivertyIndicator, xam);
                //dilivertyIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getColor(R.color.colorGray)));
            }
            else {
                //dilivertyIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getColor(R.color.colorGreenDark)));
                ColorStateList xanh = AppCompatResources.getColorStateList(itemView.getContext(), R.color.colorGreenDark);
                ImageViewCompat.setImageTintList(dilivertyIndicator, xanh);

            }
            diliveryStatus.setText(status_date);

            //Rating
            setRating(numRating);
            for (int x=0; x < rating.getChildCount(); x++){
                final  int  startPosition = x;
                rating.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRating(startPosition);
                    }
                });

            }
            //Rating
        }
        private void setRating(int startPosition) {
            for(int x = 0; x < rating.getChildCount(); x++){
                ImageView starbtn = (ImageView) rating.getChildAt(x);
                starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
                if(x  <= startPosition){
                    starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
                }
            }
        }
    }


}
