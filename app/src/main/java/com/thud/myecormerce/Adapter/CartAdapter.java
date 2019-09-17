package com.thud.myecormerce.Adapter;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thud.myecormerce.Fragments.CartFragment;
import com.thud.myecormerce.Models.CartItemModel;
import com.thud.myecormerce.Presenter.DbQueries;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.ProductDetailActivity;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModel> cartItemModelList;
    private int lastposition = -1;
    private TextView totalCartAmount;
    private boolean showBtnDelete;


    public CartAdapter(List<CartItemModel> cartItemModelList, TextView totalCartAmount, boolean showBtnDelete) {
        this.cartItemModelList = cartItemModelList;
        this.totalCartAmount = totalCartAmount;
        this.showBtnDelete = showBtnDelete;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get(position).getType()){
            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType){
            case CartItemModel.CART_ITEM:

                View Cartview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_layout, viewGroup, false);
                return new CartItemViewHolder(Cartview); //Trả về

            case CartItemModel.TOTAL_AMOUNT:
                View Totalview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_total_buy_layout, viewGroup, false);
                return new CartTotalAmountViewHolder(Totalview); //Trả về

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (cartItemModelList.get(position).getType()){
            case CartItemModel.CART_ITEM:
                String product_id = cartItemModelList.get(position).getProduct_id();
                String resource = cartItemModelList.get(position).getProductImage();
                String name = cartItemModelList.get(position).getProductName();
                String price = cartItemModelList.get(position).getProductPrice();
                String cuttedprice = cartItemModelList.get(position).getCuttedProductPrice();
                Long freeDiscount = cartItemModelList.get(position).getFreeDiscount();
                Long offerAplied = cartItemModelList.get(position).getOfferAplied();
                boolean instock = cartItemModelList.get(position).isInstock();

                ((CartItemViewHolder) viewHolder).setCartDetail(product_id,resource,name,freeDiscount,price,cuttedprice,offerAplied, position, instock);
                break;
            case CartItemModel.TOTAL_AMOUNT:

                int totalItems = 0;
                int totalItemsPrice = 0;
                String dilivery;
                int totalAmount;
                int  saveAmount = 0;

                for (int x = 0; x< cartItemModelList.size(); x++){
                    if(cartItemModelList.get(x).getType() == CartItemModel.CART_ITEM && cartItemModelList.get(x).isInstock()){
                        totalItems++;
                        totalItemsPrice = totalItemsPrice + Integer.parseInt(cartItemModelList.get(x).getProductPrice());

                    }
                }
                if(totalItemsPrice < 1000000){
                    dilivery = "Free ship";
                    totalAmount = totalItemsPrice;
                }
                else {
                    dilivery = "40000";
                    totalAmount = totalItemsPrice + 40000;
                }

                ((CartTotalAmountViewHolder) viewHolder).setTotalAmount(totalItems,totalItemsPrice,dilivery, totalAmount, saveAmount);

                break;
            default:
                return;
        }
        if(lastposition < position) {
            Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.fade_in);
            viewHolder.itemView.setAnimation(animation);
            lastposition = position;
        }
    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }
    //Tự tạo class cho CartItem
    public class CartItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView productImage;
        private ImageView productIconDiscount;
        private TextView productName;
        private TextView productfreeDiscount;
        private TextView productPrice;
        private TextView productCuttedPrice;
        private TextView productOfferAplied;
        private TextView productDiscountAplied;
        private TextView productQuantity;
        private LinearLayout remove_item_cart_btn;
        private LinearLayout linear_discount_cart_item;


        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imv_product_cart_item);
            productIconDiscount = itemView.findViewById(R.id.imv_discount_wishlist);
            productName = itemView.findViewById(R.id.txt_product_name_cart_item);
            productfreeDiscount = itemView.findViewById(R.id.txt_discount_cart_item);
            productPrice = itemView.findViewById(R.id.txt_product_price_cart_item);
            productCuttedPrice = itemView.findViewById(R.id.txt_old_price_cart_item);
            productDiscountAplied = itemView.findViewById(R.id.txt_discounted_cart_item);
            productOfferAplied = itemView.findViewById(R.id.txt_offer_applied_cart_item);
            productQuantity = itemView.findViewById(R.id.txt_quantity_cart_item);
            remove_item_cart_btn = itemView.findViewById(R.id.btn_remove_item_cart_item);
            linear_discount_cart_item = itemView.findViewById(R.id.linear_discount_cart_item);

        }
        private void setCartDetail(String product_id, String resource, String name, Long freeDiscount, String price, String cuttedprice, Long offerAplied, final int position, boolean instock){
            //productImage.setImageResource(resource);
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.image_place)).into(productImage);
            productName.setText(name);

            if(instock){
                if(freeDiscount > 0){
                    productIconDiscount.setVisibility(View.VISIBLE);
                    productfreeDiscount.setVisibility(View.VISIBLE);
                    if(freeDiscount == 1){
                        productfreeDiscount.setText(" giảm " + freeDiscount + " %");
                    }
                    else
                    {
                        productfreeDiscount.setText(" giảm " + freeDiscount + " %");
                    }
                }
                else {
                    productIconDiscount.setVisibility(View.INVISIBLE);
                    productfreeDiscount.setVisibility(View.INVISIBLE);
                }
                productPrice.setText(price + " Đ");
                productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.colorBlack));
                productCuttedPrice.setText(cuttedprice + " Đ");

                linear_discount_cart_item.setVisibility(View.VISIBLE);
                productQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialogQuantity = new Dialog(itemView.getContext());
                        dialogQuantity.setContentView(R.layout.quantity_dialog);
                        dialogQuantity.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialogQuantity.setCancelable(false);

                        final EditText edt_quantity = dialogQuantity.findViewById(R.id.edt_quantity_dialog);
                        Button btn_cancel = dialogQuantity.findViewById(R.id.btn_cancel_dialog);
                        Button btn_ok = dialogQuantity.findViewById(R.id.btn_okay_dialog);

                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogQuantity.dismiss();
                            }
                        });
                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                productQuantity.setText("Số lượng: " + edt_quantity.getText());
                                dialogQuantity.dismiss();
                            }
                        });
                        dialogQuantity.show();
                    }
                });
                if(offerAplied > 0 ){
                    productOfferAplied.setVisibility(View.VISIBLE);
                    productOfferAplied.setText(offerAplied + " offers aplied");
                }
                else {
                    productOfferAplied.setVisibility(View.INVISIBLE);
                }
            }
            else {
                productPrice.setText(price + " Đ");
                productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.colorRed));
                productCuttedPrice.setText("Hết hàng");
                productCuttedPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.colorRed));

                linear_discount_cart_item.setVisibility(View.GONE);
                productQuantity.setVisibility(View.GONE);
                productIconDiscount.setVisibility(View.INVISIBLE);
                productfreeDiscount.setVisibility(View.INVISIBLE);
                productOfferAplied.setText("Sản phẩm sẽ sớm được nhập về");
                productDiscountAplied.setVisibility(View.INVISIBLE);

            }
//


            if(showBtnDelete){
                remove_item_cart_btn.setVisibility(View.VISIBLE);
            }
            else {
                remove_item_cart_btn.setVisibility(View.GONE);
            }
            remove_item_cart_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!ProductDetailActivity.cart_running){
                        ProductDetailActivity.cart_running = true;
                        DbQueries.removeFromCart(position, itemView.getContext(), totalCartAmount);
                    }
                }
            });
        }
    }
    //Class cho hóa đơn
    public class CartTotalAmountViewHolder extends RecyclerView.ViewHolder {

        private TextView totalItems;
        private TextView totalItemsPrice;
        private TextView dilivery;
        private TextView totalAmound;
        private TextView saveAmount;

        public CartTotalAmountViewHolder(@NonNull View itemView) {
            super(itemView);
            totalItems = itemView.findViewById(R.id.txt_so_san_pham_mua);
            totalItemsPrice = itemView.findViewById(R.id.txt_tong_tien_cac_sp);
            dilivery = itemView.findViewById(R.id.txt_phi_van_chuyen);
            totalAmound = itemView.findViewById(R.id.txt_tong_toan_bo_hoa_don);
            saveAmount = itemView.findViewById(R.id.txt_da_tiet_kiem_duoc);

        }

        private void setTotalAmount(int totalItemsText, int totalAmountText, String diliveryText, int totalallAmount, int savedAmount){
            totalItems.setText("Tổng tiền " + totalItemsText+" sản phẩm:" );
            totalItemsPrice.setText(totalAmountText + " Đ");
            if(diliveryText.equals("Free ship")){
                dilivery.setText("Free ship");
            }
            else {
                dilivery.setText(diliveryText + " Đ");
            }
            dilivery.setText(diliveryText);
            totalAmound.setText(totalallAmount + " Đ");
            totalCartAmount.setText(totalallAmount + " Đ");
            saveAmount.setText("Bạn đã tiết kiệm được: " + savedAmount + " Đ");

            LinearLayout parent = (LinearLayout) totalCartAmount.getParent().getParent();
            if(totalAmountText == 0){
                DbQueries.cartItemModelList.remove(DbQueries.cartItemModelList.size() -1);
                parent.setVisibility(View.GONE);
            }else {
                parent.setVisibility(View.VISIBLE);
            }
        }
    }
}
