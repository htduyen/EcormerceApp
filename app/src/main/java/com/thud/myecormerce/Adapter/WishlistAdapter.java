package com.thud.myecormerce.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thud.myecormerce.Models.WishlistModel;
import com.thud.myecormerce.R;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    private List<WishlistModel> wishlistModelList;

    public WishlistAdapter(List<WishlistModel> wishlistModelList) {
        this.wishlistModelList = wishlistModelList;
    }

    @NonNull
    @Override
    public WishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_product_item_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.ViewHolder viewHolder, int i) {
        int resource = wishlistModelList.get(i).getProductImage();
        String name = wishlistModelList.get(i).getProductName();
        String price = wishlistModelList.get(i).getProductPrice();
        String cuttedPrice = wishlistModelList.get(i).getProductCuttedPrice();
        int freeDiscount = wishlistModelList.get(i).getFreeDiscount();
        String payment = wishlistModelList.get(i).getPaymentMethod();
        String rating = wishlistModelList.get(i).getRating();
        int totalRating = wishlistModelList.get(i).getTotalRating();

        viewHolder.setData(resource,name,price,freeDiscount,rating, totalRating,cuttedPrice,payment);
    }

    @Override
    public int getItemCount() {
        return wishlistModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productName;
        private TextView productPrice;
        private TextView productCuttedPrice;
        private View horizon_cutted;
        private TextView productDiscount;
        private ImageView productIconDiscount;
        private TextView productRating;
        private TextView productTotalRating;
        private TextView productPaymentMethod;
        private ImageView productDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imv_product_wishlist);
            productName = itemView.findViewById(R.id.txt_product_name_wishlist);
            productPrice = itemView.findViewById(R.id.txt_product_price_wishlist);
            productCuttedPrice = itemView.findViewById(R.id.txt_cutted_price_wishlist);
            productDiscount = itemView.findViewById(R.id.txt_discount_wishlist);
            productIconDiscount = itemView.findViewById(R.id.imv_discount_wishlist);
            productTotalRating = itemView.findViewById(R.id.txt_num_rating_wishlist);
            productRating = itemView.findViewById(R.id.txt_product_rating_detail_wishlist);
            productDelete = itemView.findViewById(R.id.imv_delete_item_wishlist);
            productPaymentMethod = itemView.findViewById(R.id.txt_payment_method_wishlist);
            horizon_cutted = itemView.findViewById(R.id.horizon_cutted_price_wishlist);

        }
        private  void setData(int resource, String name, String price, int discountNo, String averageRate, int totalRating, String cutted_price, String paymentMethod){

            productImage.setImageResource(resource);
            productName.setText(name);
            productPrice.setText(price);
            productCuttedPrice.setText(cutted_price);
            if(discountNo != 0){
                productIconDiscount.setVisibility(View.VISIBLE);
                productDiscount.setText("Giảm " + discountNo + " %");
            }
            else {
                productIconDiscount.setVisibility(View.GONE);
                productDiscount.setVisibility(View.GONE);
            }
            productRating.setText(averageRate);
            productTotalRating.setText("(" + totalRating + ") người bình chọn" );
            productPaymentMethod.setText(paymentMethod);
            
            productDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Delete", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
