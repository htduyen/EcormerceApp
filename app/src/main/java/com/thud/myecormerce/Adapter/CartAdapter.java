package com.thud.myecormerce.Adapter;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.thud.myecormerce.Models.CartItemModel;
import com.thud.myecormerce.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModel> cartItemModelList;

    public CartAdapter(List<CartItemModel> cartItemModelList) {
        this.cartItemModelList = cartItemModelList;
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
                int resource = cartItemModelList.get(position).getProductImage();
                String name = cartItemModelList.get(position).getProductName();
                String price = cartItemModelList.get(position).getProductPrice();
                String cuttedprice = cartItemModelList.get(position).getCuttedProductPrice();
                int freeDiscount = cartItemModelList.get(position).getFreeDiscount();
                int offerAplied = cartItemModelList.get(position).getOfferAplied();

                ((CartItemViewHolder) viewHolder).setCartDetail(resource,name,freeDiscount,price,cuttedprice,offerAplied);
                break;
            case CartItemModel.TOTAL_AMOUNT:
                String totalItems = cartItemModelList.get(position).getTotalItems();
                String totalItemsPrice = cartItemModelList.get(position).getTotalItemsPrice();
                String dilivery = cartItemModelList.get(position).getPhi_chuyen_hang();
                String totalAmount = cartItemModelList.get(position).getTotalAmount();
                String saveAmount = cartItemModelList.get(position).getTiet_kiem_duoc();

                ((CartTotalAmountViewHolder) viewHolder).setTotalAmount(totalItems,totalItemsPrice,dilivery, totalAmount, saveAmount);

                break;
            default:
                return;
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
        }
        private void setCartDetail(int resource, String name, int freeDiscount,String price, String cuttedprice, int offerAplied){
            productImage.setImageResource(resource);
            productName.setText(name);
            if(freeDiscount > 0){
                productIconDiscount.setVisibility(View.VISIBLE);
                productfreeDiscount.setVisibility(View.VISIBLE);
                if(freeDiscount == 1){
                    productfreeDiscount.setText("giảm ít " + freeDiscount + " %");
                }
                else
                {
                    productfreeDiscount.setText("giảm nhiều" + freeDiscount + " %");
                }
            }
            else {
                productIconDiscount.setVisibility(View.INVISIBLE);
                productfreeDiscount.setVisibility(View.INVISIBLE);
            }
            productPrice.setText(price);
            productCuttedPrice.setText(cuttedprice);
            if(offerAplied > 0 ){
                productOfferAplied.setVisibility(View.VISIBLE);
                productOfferAplied.setText(offerAplied + " offers aplied");
            }
            else {
                productOfferAplied.setVisibility(View.INVISIBLE);
            }
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

        private void setTotalAmount(String totalItemsText, String totalAmountText, String diliveryText, String totalallAmount, String savedAmount){
            totalItems.setText(totalItemsText);
            totalItemsPrice.setText(totalAmountText);
            dilivery.setText(diliveryText);
            totalAmound.setText(totalallAmount);
            saveAmount.setText(savedAmount);

        }
    }
}
