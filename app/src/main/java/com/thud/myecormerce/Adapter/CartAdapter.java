package com.thud.myecormerce.Adapter;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.thud.myecormerce.Fragments.CartFragment;
import com.thud.myecormerce.Models.CartItemModel;
import com.thud.myecormerce.Models.RewardModel;
import com.thud.myecormerce.Presenter.DbQueries;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.DeliveryActivity;
import com.thud.myecormerce.View.MainActivity;
import com.thud.myecormerce.View.ProductDetailActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.grpc.internal.LogId;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModel> cartItemModelList;
    //private FirebaseFirestore firebaseFirestore;
    private int lastposition = -1;
    private TextView totalCartAmount;
    private boolean showBtnDelete;

    private TextView discountTitle;
    private TextView discountTime;
    private TextView discountContent;
    private RecyclerView recyclerViewDiscount;
    private LinearLayout selectedDiscount;
    private TextView txt_discounted_price;
    private TextView txt_origin_price;

    private Button btn_remove_discount;
    private Button btn_apply_discount;
    private LinearLayout linearLayout_apply_discount;


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
                Long quantity = cartItemModelList.get(position).getProductQuantity();
                Long maxQuantity = cartItemModelList.get(position).getMaxQuantity();
                boolean qtyError = cartItemModelList.get(position).isQtyError();
                List<String> qtyIDs = cartItemModelList.get(position).getQuantityIDs();
                long stockQty = cartItemModelList.get(position).getStockQuantity();
                ((CartItemViewHolder) viewHolder).setCartDetail(product_id,resource,name,freeDiscount,price,cuttedprice,offerAplied, position,instock, String.valueOf(quantity),maxQuantity, qtyError, qtyIDs, stockQty);
                break;
            case CartItemModel.TOTAL_AMOUNT:

                int totalItems = 0;
                int totalItemsPrice = 0;
                String dilivery;
                int totalAmount;
                int  saveAmount = 0;

                for (int x = 0; x< cartItemModelList.size(); x++){
                    if(cartItemModelList.get(x).getType() == CartItemModel.CART_ITEM && cartItemModelList.get(x).isInstock()){
                        int quantity_pro = Integer.parseInt(String.valueOf(cartItemModelList.get(x).getProductQuantity()));
                        totalItems = totalItems + quantity_pro;
                        if(TextUtils.isEmpty(cartItemModelList.get(x).getSelectedDiscountID())) {
                            totalItemsPrice = totalItemsPrice + Integer.parseInt(cartItemModelList.get(x).getProductPrice())*quantity_pro;
                        }else {
                            totalItemsPrice = totalItemsPrice + Integer.parseInt(cartItemModelList.get(x).getDiscountPrice())*quantity_pro;
                        }
                        if(!TextUtils.isEmpty(cartItemModelList.get(x).getCuttedProductPrice())){
                            saveAmount = saveAmount + (Integer.parseInt(cartItemModelList.get(x).getCuttedProductPrice()) - Integer.parseInt(cartItemModelList.get(x).getProductPrice()))*quantity_pro;
                            if(!TextUtils.isEmpty(cartItemModelList.get(x).getSelectedDiscountID())) {
                                saveAmount = saveAmount + (Integer.parseInt(cartItemModelList.get(x).getProductPrice()) - Integer.parseInt(cartItemModelList.get(x).getDiscountPrice()))*quantity_pro;

                            }
                        }else {
                            if(!TextUtils.isEmpty(cartItemModelList.get(x).getSelectedDiscountID())) {
                                saveAmount = saveAmount + (Integer.parseInt(cartItemModelList.get(x).getProductPrice()) - Integer.parseInt(cartItemModelList.get(x).getDiscountPrice()))*quantity_pro;

                            }
                        }

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
                cartItemModelList.get(position).setTotalItems(totalItems);
                cartItemModelList.get(position).setTotalItemsPrice(totalItemsPrice);
                cartItemModelList.get(position).setTotalAmount(totalAmount);
                cartItemModelList.get(position).setSaveAmount(saveAmount);
                cartItemModelList.get(position).setDeliveryPrice(dilivery);
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
        private String productOriginPrice;
        private Button btn_discount_dialog;
        private TextView txt_discount_body_linear;


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
            btn_discount_dialog = itemView.findViewById(R.id.btn_redemption_detail);
            txt_discount_body_linear = itemView.findViewById(R.id.txt_redemption_detail);

        }
        private void setCartDetail(final String product_id, String resource, String name, Long freeDiscount, final String price, String cuttedprice, Long offerAplied, final int position, final boolean instock, final String quantity, final Long maxQuanity, boolean qtyError, final List<String> qtyIDs, final long stockQty){
            //productImage.setImageResource(resource);

            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.image_place)).into(productImage);
            productName.setText(name);

            final Dialog dialogDiscount = new Dialog(itemView.getContext());
            dialogDiscount.setContentView(R.layout.discount_dialog);
            dialogDiscount.setCancelable(false);
            dialogDiscount.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

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
                //productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.colorBlack));
                productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.colorBlack));
                productCuttedPrice.setText(cuttedprice + " Đ");

                linear_discount_cart_item.setVisibility(View.VISIBLE);
                //Discount dialog

                ImageView imv_togle_recycler = dialogDiscount.findViewById(R.id.imv_togle_discount_dialog);
                recyclerViewDiscount =  dialogDiscount.findViewById(R.id.recyclerView_discount_dialog);
                selectedDiscount = dialogDiscount.findViewById(R.id.linea_selected_discount_dialog);
                //Luu Y
                discountTitle = dialogDiscount.findViewById(R.id.txt_name_reward_reward);
                discountTime = dialogDiscount.findViewById(R.id.txt_time_reward);
                discountContent = dialogDiscount.findViewById(R.id.txt_content_reward);
                btn_apply_discount = dialogDiscount.findViewById(R.id.btn_apply_discount);
                btn_remove_discount = dialogDiscount.findViewById(R.id.btn_remove_discount);
                linearLayout_apply_discount = dialogDiscount.findViewById(R.id.linear_conform_discount_dialog);
                txt_origin_price = dialogDiscount.findViewById(R.id.txt_origin_price_dialog);
                txt_discounted_price = dialogDiscount.findViewById(R.id.txt_discounted_price_dialog);

                linearLayout_apply_discount.setVisibility(View.VISIBLE);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewDiscount.setLayoutManager(linearLayoutManager);

                txt_origin_price.setText(productPrice.getText());
                productOriginPrice = price;
                RewardAdapter discountAdapter = new RewardAdapter(position,DbQueries.rewardModelList, true, recyclerViewDiscount,selectedDiscount, productOriginPrice, discountTitle, discountTime, discountContent,txt_discounted_price, cartItemModelList);
                recyclerViewDiscount.setAdapter(discountAdapter);
                discountAdapter.notifyDataSetChanged();

                btn_apply_discount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!TextUtils.isEmpty(cartItemModelList.get(position).getSelectedDiscountID())) {
                            for (RewardModel rewardModel : DbQueries.rewardModelList) {
                                if (rewardModel.getDiscountID().equals(cartItemModelList.get(position).getSelectedDiscountID())) {
                                    rewardModel.setAlreadlyUsed(true);
                                    linear_discount_cart_item.setBackground(itemView.getContext().getResources().getDrawable(R.drawable.background_gradiant));
                                    txt_discount_body_linear.setText(rewardModel.getDiscountbBoby());

                                }
                            }

                            productDiscountAplied.setVisibility(View.VISIBLE);
                            productPrice.setText(txt_discounted_price.getText());
                            cartItemModelList.get(position).setDiscountPrice(txt_discounted_price.getText().toString());
                            String offerDiscountAmt = String.valueOf(Long.valueOf(productOriginPrice) - Long.valueOf(txt_discounted_price.getText().toString()));
                            productDiscountAplied.setText("Đã giảm - " + offerDiscountAmt + " đ");
                            notifyItemChanged(cartItemModelList.size() - 1);
                            dialogDiscount.dismiss();
                        }
                    }
                });
                btn_remove_discount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(RewardModel rewardModel: DbQueries.rewardModelList){
                            if(rewardModel.getDiscountID().equals(cartItemModelList.get(position).getSelectedDiscountID())){
                                rewardModel.setAlreadlyUsed(false);
                            }
                        }
                        discountTitle.setText("Khuyến mãi");
                        discountTime.setText("Có hiệu lực");
                        discountContent.setText("Chọn icon trên góc phải để áp dụng khuyến mãi");
                        productDiscountAplied.setVisibility(View.INVISIBLE);
                        linear_discount_cart_item.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.colorRed));
                        txt_discount_body_linear.setText("Kiểm tra giảm giá dành riêng cho bạn");
                        cartItemModelList.get(position).setSelectedDiscountID(null);
                        productPrice.setText(price + " Đ");
                        notifyItemChanged(cartItemModelList.size() - 1);
                        dialogDiscount.dismiss();
                    }
                });

                imv_togle_recycler.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialogDisCount();
                    }

                    private void showDialogDisCount() {
                        if(recyclerViewDiscount.getVisibility() == View.GONE){
                            recyclerViewDiscount.setVisibility(View.VISIBLE);
                            selectedDiscount.setVisibility(View.GONE);
                        }
                        else {
                            recyclerViewDiscount.setVisibility(View.GONE);
                            selectedDiscount.setVisibility(View.VISIBLE);
                        }
                    }
                });
                //Discount dialog
                if(!TextUtils.isEmpty(cartItemModelList.get(position).getSelectedDiscountID())) {
                    for (RewardModel rewardModel : DbQueries.rewardModelList) {
                        if (rewardModel.getDiscountID().equals(cartItemModelList.get(position).getSelectedDiscountID())) {
                            linear_discount_cart_item.setBackground(itemView.getContext().getResources().getDrawable(R.drawable.background_gradiant));
                            txt_discount_body_linear.setText(rewardModel.getDiscountbBoby());

                            discountContent.setText(rewardModel.getDiscountbBoby());
                            if(rewardModel.getType().equals("Discount")){
                                discountTitle.setText("Khuyến mãi giảm " + rewardModel.getDiscount()+ " %");
                            }else {
                                discountTitle.setText(rewardModel.getType());
                            }
                            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            discountTime.setText(simpleDateFormat.format(rewardModel.getTimestamp()));
                        }
                    }
                    txt_discounted_price.setText(cartItemModelList.get(position).getDiscountPrice() + " đ");
                    productDiscountAplied.setVisibility(View.VISIBLE);
                    productPrice.setText(cartItemModelList.get(position).getDiscountPrice() + " đ");
                    Log.d("AAAA", productOriginPrice + " - " + cartItemModelList.get(position).getDiscountPrice());
                    String offerDiscountAmt = String.valueOf(Long.valueOf(productOriginPrice) - Long.valueOf(cartItemModelList.get(position).getDiscountPrice()));
                    productDiscountAplied.setText("Đã giảm - " + offerDiscountAmt + " đ");
                }else {
                    productDiscountAplied.setVisibility(View.INVISIBLE);
                    linear_discount_cart_item.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.colorRed));
                    txt_discount_body_linear.setText("Kiểm tra giảm giá dành riêng cho bạn");

                }
                productQuantity.setText("Qty: " + quantity);

                if(!showBtnDelete){
                    if(qtyError){
                        productQuantity.setTextColor(itemView.getContext().getResources().getColor(R.color.colorRed));
                        productQuantity.setBackgroundTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.colorwhitewhite)));

                    }else {
                        productQuantity.setTextColor(itemView.getContext().getResources().getColor(R.color.black));
                        productQuantity.setBackgroundTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.black)));
                    }
                }
                productQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialogQuantity = new Dialog(itemView.getContext());
                        dialogQuantity.setContentView(R.layout.quantity_dialog);
                        dialogQuantity.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialogQuantity.setCancelable(false);

                        final EditText edt_quantity = dialogQuantity.findViewById(R.id.edt_quantity_dialog);
                        edt_quantity.setHint("Số lượng tối đa là " + String.valueOf(maxQuanity));
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
                                if(!TextUtils.isEmpty(edt_quantity.getText())) {
                                    if (Long.parseLong(edt_quantity.getText().toString()) <= maxQuanity && Long.parseLong(edt_quantity.getText().toString()) != 0 ) {
                                        if(itemView.getContext() instanceof MainActivity){
                                            cartItemModelList.get(position).setProductQuantity(Long.valueOf(edt_quantity.getText().toString()));
                                        }else {
                                            if (DeliveryActivity.fromCart) {
                                                cartItemModelList.get(position).setProductQuantity(Long.valueOf(edt_quantity.getText().toString()));
                                            } else {
                                                DeliveryActivity.cartItemModelList.get(position).setProductQuantity(Long.valueOf(edt_quantity.getText().toString()));
                                            }
                                        }
                                        productQuantity.setText("Qty: " + edt_quantity.getText());
                                        notifyItemChanged(cartItemModelList.size() - 1);
                                        //DeliveryActivity.loadingDialog.show();
                                        if(!showBtnDelete){
                                            DeliveryActivity.loadingDialog.show();
                                            DeliveryActivity.cartItemModelList.get(position).setQtyError(false);
                                            final int iniateQty = Integer.parseInt(quantity);
                                            final int finalQty = Integer.parseInt(edt_quantity.getText().toString());
                                            final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

                                            if(finalQty  > iniateQty) {

                                                for (int y = 0; y < finalQty - iniateQty; y++) {
                                                    final String quantityDocName = UUID.randomUUID().toString().substring(0, 20);
                                                    Map<String, Object> time = new HashMap<>();
                                                    time.put("time", FieldValue.serverTimestamp());
                                                    final int finalY = y;
                                                    firebaseFirestore.collection("PRODUCTS").document(product_id).collection("QUANTITY").document(quantityDocName).set(time).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                qtyIDs.add(quantityDocName);
                                                                if (finalY + 1 == finalQty - iniateQty) {
                                                                    firebaseFirestore.collection("PRODUCTS").document(product_id).collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING).limit(stockQty).get()
                                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        List<String> serverQuanties = new ArrayList<>();
                                                                                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                                                                            serverQuanties.add(queryDocumentSnapshot.getId());
                                                                                        }
                                                                                        long availableQty = 0;

                                                                                        for (String qtyID : qtyIDs) {
                                                                                            if (!serverQuanties.contains(qtyID)) {
                                                                                                DeliveryActivity.cartItemModelList.get(position).setQtyError(true);
                                                                                                DeliveryActivity.cartItemModelList.get(position  ).setMaxQuantity(availableQty);
                                                                                                Toast.makeText(itemView.getContext(), "Các sản phẩm không có sẵn", Toast.LENGTH_SHORT).show();

                                                                                            }else {
                                                                                                availableQty++;
                                                                                            }
                                                                                        }
                                                                                        DeliveryActivity.cartAdapter.notifyDataSetChanged();
                                                                                    } else {
                                                                                        String error = task.getException().getMessage();
                                                                                        Toast.makeText(itemView.getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                    DeliveryActivity.loadingDialog.dismiss();
                                                                                }
                                                                            });
                                                                }

                                                            } else {
                                                                String error = task.getException().getMessage();
                                                                Toast.makeText(itemView.getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }
                                            }else if(iniateQty > finalQty){
                                                for (int x =0; x < iniateQty - finalQty; x++) {
                                                    final String qtyID = qtyIDs.get(qtyIDs.size() -1 - x);
                                                    final int finalX = x;
                                                    firebaseFirestore.collection("PRODUCTS").document(product_id).collection("QUANTITY").document(qtyID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()){
                                                                qtyIDs.remove(qtyID);
                                                                DeliveryActivity.cartAdapter.notifyDataSetChanged();
                                                                if(finalX +1 < iniateQty - finalQty){
                                                                    DeliveryActivity.loadingDialog.dismiss();
                                                                }
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                    else {
                                        Toast.makeText(itemView.getContext(), "Số lượng tối đa là : " + maxQuanity.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                    dialogQuantity.dismiss();
                            }
                        });
                        dialogQuantity.show();
                    }
                });
                productOriginPrice = price;
                if(offerAplied > 0 ){
                    productOfferAplied.setVisibility(View.VISIBLE);
                    //Log.d("LOGLOG", cuttedprice + " - " + productOriginPrice);
                    String offerDiscountAmt = String.valueOf(Long.valueOf(cuttedprice) - Long.valueOf(productOriginPrice));
                    productOfferAplied.setText("Đã tiết kiệm - " + offerDiscountAmt + " Đ");
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

            btn_discount_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(RewardModel rewardModel: DbQueries.rewardModelList){
                        if(rewardModel.getDiscountID().equals(cartItemModelList.get(position).getSelectedDiscountID())){
                            rewardModel.setAlreadlyUsed(false);
                        }
                    }
                    dialogDiscount.show();
                }

            });

            remove_item_cart_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!TextUtils.isEmpty(cartItemModelList.get(position).getSelectedDiscountID())) {
                        for(RewardModel rewardModel: DbQueries.rewardModelList){
                            if(rewardModel.getDiscountID().equals(cartItemModelList.get(position).getSelectedDiscountID())){
                                rewardModel.setAlreadlyUsed(false);
                            }
                        }
                    }
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
                if (DeliveryActivity.fromCart) {
                    cartItemModelList.remove(cartItemModelList.size() - 1);
                    cartItemModelList.remove(DeliveryActivity.cartItemModelList.size() - 1);
                }
                if(showBtnDelete){
                    cartItemModelList.remove(cartItemModelList.size() - 1);
                }
                parent.setVisibility(View.GONE);
            }else {
                parent.setVisibility(View.VISIBLE);
            }
        }
    }
}
