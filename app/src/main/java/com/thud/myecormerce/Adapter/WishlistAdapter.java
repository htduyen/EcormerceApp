package com.thud.myecormerce.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thud.myecormerce.Models.WishlistModel;
import com.thud.myecormerce.Presenter.DbQueries;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.ProductDetailActivity;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    private List<WishlistModel> wishlistModelList;
    private Boolean wishlist;
    private int lastposition = -1;

    public WishlistAdapter(List<WishlistModel> wishlistModelList, Boolean wishlist) {
        this.wishlistModelList = wishlistModelList;
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public WishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_product_item_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.ViewHolder viewHolder, int i) {
        String productID = wishlistModelList.get(i).getProduct_id();
        String resource = wishlistModelList.get(i).getProductImage();
        String name = wishlistModelList.get(i).getProductName();
        String price = wishlistModelList.get(i).getProductPrice();
        String cuttedPrice = wishlistModelList.get(i).getProductCuttedPrice();
        long freeDiscount = wishlistModelList.get(i).getFreeDiscount();
        boolean payment = wishlistModelList.get(i).isPaymentMethod();
        String rating = wishlistModelList.get(i).getRating();
        long totalRating = wishlistModelList.get(i).getTotalRating();
        boolean inStock = wishlistModelList.get(i).isInStock();
        viewHolder.setData(productID,resource,name,price,freeDiscount,rating, totalRating,cuttedPrice,payment,i, inStock);

        if(lastposition < i) {
            Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.fade_in);
            viewHolder.itemView.setAnimation(animation);
            lastposition = i;
        }
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
        private  void setData(final String productID, String resource, String name, String price, long discountNo, String averageRate, long totalRating, String cutted_price, boolean paymentMethod, final int index, boolean inStock){

//            productImage.setImageResource(resource);
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.image_place)).into(productImage);
            //productCuttedPrice.setText(cutted_price + " Đ");
            productName.setText(name);
            if(discountNo != 0 && inStock){
                productIconDiscount.setVisibility(View.VISIBLE);
                productDiscount.setText("Giảm " + discountNo + " %");
            }
            else {
                productIconDiscount.setVisibility(View.VISIBLE);
                productDiscount.setVisibility(View.VISIBLE);
            }
            LinearLayout linearLayoutRating = (LinearLayout) productRating.getParent();
            if(inStock){
                linearLayoutRating.setVisibility(View.VISIBLE);
                productTotalRating.setVisibility(View.VISIBLE);
                productPaymentMethod.setVisibility(View.VISIBLE);
                productCuttedPrice.setVisibility(View.VISIBLE);
                productRating.setText(averageRate);
                productTotalRating.setText("(" + totalRating + ") người bình chọn" );
                productName.setText(name);
                productPrice.setText(price + " Đ");
                if(paymentMethod){
                    productPaymentMethod.setVisibility(View.VISIBLE);
                }
                else {
                    productPaymentMethod.setVisibility(View.INVISIBLE);
                }
            }
            else {
                linearLayoutRating.setVisibility(View.INVISIBLE);
                productPaymentMethod.setVisibility(View.INVISIBLE);
                productTotalRating.setVisibility(View.INVISIBLE);
                productName.setText(name);
                productPrice.setText("Hết hàng");
                productPrice.setTextColor(Color.parseColor("#ff142b"));
                productCuttedPrice.setVisibility(View.INVISIBLE);
                //xxxxx.setTextColor(Color.parseColor("#ff142b"));
                productDiscount.setVisibility(View.INVISIBLE);
                productIconDiscount.setVisibility(View.INVISIBLE);


            }
            if (wishlist){
                productDelete.setVisibility(View.VISIBLE);
                productDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!ProductDetailActivity.wishlist_running) {
                            ProductDetailActivity.wishlist_running = true;
                            DbQueries.removeFromWishList(index, itemView.getContext());
                        }
                    }
                });
            }
            else {
                productDelete.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentProduct = new Intent(itemView.getContext(), ProductDetailActivity.class);
                    //ma cua product
                    intentProduct.putExtra("PRODUCT_ID", productID);
                    itemView.getContext().startActivity(intentProduct);
                }
            });

        }
    }
}
