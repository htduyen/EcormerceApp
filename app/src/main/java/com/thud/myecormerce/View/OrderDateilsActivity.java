package com.thud.myecormerce.View;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.thud.myecormerce.Models.MyOrderItemModel;
import com.thud.myecormerce.Presenter.DbQueries;
import com.thud.myecormerce.R;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class OrderDateilsActivity extends AppCompatActivity {

    private int position;
    private TextView productName, productQuantity, productPrice;
    private ImageView productImage, orderedIndicator, packedIndicator, shippedIndicator, deliveriedIndicator;
    private ProgressBar orderedProgress, packedProgress, shippedProgress;
    private TextView orderedDate, packedDate, shippedDate, deliveriedDate;
    private TextView orderedTitle, packedTitle, shippedTitle, deliveriedTitle;
    private TextView orderedBody, packedBody, shippedBody, deliveriedBody;

    private LinearLayout ratingNowContainer;
    private int numRating;
    private TextView fullname, address, phoneNumber;
    private TextView totalItems, totalItemsPrice, deliveryPrice, totalAmount, savedAmount;
    private SimpleDateFormat simpleDateFormat;
    private Dialog loadingDialog, cancelDialog;
    private Button btn_cancel_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_dateils);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Chi Tiết Đặt Hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadingDialog = new Dialog(OrderDateilsActivity.this);
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_main));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        cancelDialog = new Dialog(OrderDateilsActivity.this);
        cancelDialog.setContentView(R.layout.order_cancel_dialog);
        cancelDialog.setCancelable(true);
        cancelDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_main));
        //cancelDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        position = getIntent().getIntExtra("Position", -1);
        final MyOrderItemModel myOrderItemModel = DbQueries.myOrderItemModelList.get(position);

        productName = findViewById(R.id.txt_product_name_order_detail);
        productPrice = findViewById(R.id.txt_product_price_order_detail);
        productQuantity = findViewById(R.id.txt_quantity_cart_item);
        productImage = findViewById(R.id.imv_product_image_order_detail);

        orderedIndicator = findViewById(R.id.imv_ordered_indicator);
        packedIndicator = findViewById(R.id.imv_packed_indicator);
        shippedIndicator = findViewById(R.id.imv_shipped_indicator);
        deliveriedIndicator = findViewById(R.id.imv_delivered_indicator);

        orderedProgress = findViewById(R.id.ordered_packed_progress);
        packedProgress = findViewById(R.id.packed_shiped_progress);
        shippedProgress = findViewById(R.id.shipped_delivered_progress);

        orderedTitle = findViewById(R.id.txt_ordered);
        packedTitle = findViewById(R.id.txt_packed);
        shippedTitle = findViewById(R.id.txt_shipped);
        deliveriedTitle = findViewById(R.id.txt_delivered);

        orderedDate = findViewById(R.id.txt_ordered_date);
        packedDate = findViewById(R.id.txt_packed_date);
        shippedDate = findViewById(R.id.txt_shipped_date);
        deliveriedDate = findViewById(R.id.txt_delivered_date);

        orderedBody = findViewById(R.id.txt_ordered_body);
        packedBody = findViewById(R.id.txt_packed_body);
        shippedBody = findViewById(R.id.txt_shipped_body);
        deliveriedBody = findViewById(R.id.txt_delivered_body);

        ratingNowContainer = findViewById(R.id.linear_rating_now_orderdetail);
        fullname = findViewById(R.id.txt_full_name_shipping_detail);
        address = findViewById(R.id.txt_full_address_detail_shipping);
        phoneNumber = findViewById(R.id.txt_sdt_shipping_detail);
        totalItemsPrice = findViewById(R.id.txt_tong_tien_cac_sp);
        //deliveriedBody = findViewById(R.id.txt_phi_van_chuyen);
        totalAmount = findViewById(R.id.txt_tong_toan_bo_hoa_don);
        totalItems = findViewById(R.id.txt_so_san_pham_mua);
        savedAmount = findViewById(R.id.txt_da_tiet_kiem_duoc);
        deliveryPrice = findViewById(R.id.txt_phi_van_chuyen);

        btn_cancel_order = findViewById(R.id.btn_cancel_order);

        productName.setText(myOrderItemModel.getProductName());
        if(!myOrderItemModel.getDiscountedPrice().equals("")) {
            productPrice.setText(myOrderItemModel.getDiscountedPrice() + " đ");
        }else {
            productPrice.setText(myOrderItemModel.getProductPrice() + " đ");
        }
        productQuantity.setText("Q.ty: " + String.valueOf(myOrderItemModel.getProductQuantity()));
        Glide.with(this).load(myOrderItemModel.getProductImage()).into(productImage);

        simpleDateFormat = new SimpleDateFormat("EEE, dd/MM/YYYY hh:mm:aa");
        switch (myOrderItemModel.getOrderStatus()){
            case "Ordered":
                orderedTitle.setText("Ordered");
                orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                orderedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getOrderdDate())));
                orderedBody.setText("Hàng đã được đặt");

                orderedProgress.setVisibility(View.GONE);
                packedProgress.setVisibility(View.GONE);
                shippedProgress.setVisibility(View.GONE);

                packedIndicator.setVisibility(View.GONE);
                packedTitle.setVisibility(View.GONE);
                packedDate.setVisibility(View.GONE);
                packedBody.setVisibility(View.GONE);

                shippedIndicator.setVisibility(View.GONE);
                shippedTitle.setVisibility(View.GONE);
                shippedDate.setVisibility(View.GONE);
                shippedBody.setVisibility(View.GONE);

                deliveriedIndicator.setVisibility(View.GONE);
                deliveriedTitle.setVisibility(View.GONE);
                deliveriedDate.setVisibility(View.GONE);
                deliveriedBody.setVisibility(View.GONE);


                break;
            case "Packed":
                orderedTitle.setText("Ordered");
                orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                orderedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getOrderdDate())));
                orderedBody.setText("Hàng đã được đặt");

                packedTitle.setText("Packed");
                packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                packedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getOrderdDate())));
                packedBody.setText("Hàng đã đóng gói");

                orderedProgress.setProgress(100);
                packedProgress.setVisibility(View.GONE);
                shippedProgress.setVisibility(View.GONE);

                shippedIndicator.setVisibility(View.GONE);
                shippedTitle.setVisibility(View.GONE);
                shippedDate.setVisibility(View.GONE);
                shippedBody.setVisibility(View.GONE);

                deliveriedIndicator.setVisibility(View.GONE);
                deliveriedTitle.setVisibility(View.GONE);
                deliveriedDate.setVisibility(View.GONE);
                deliveriedBody.setVisibility(View.GONE);
                break;
            case "Shipped":
                orderedTitle.setText("Ordered");
                orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                orderedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getOrderdDate())));
                orderedBody.setText("Hàng đã được đặt");

                packedTitle.setText("Packed");
                packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                packedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getPackedDate())));
                packedBody.setText("Hàng đã đóng gói");

                shippedTitle.setText("Shipped");
                shippedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                shippedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getShipedDate())));
                shippedBody.setText("Hàng đã vận chuyển");

                orderedProgress.setProgress(100);
                packedProgress.setProgress(100);
                shippedProgress.setVisibility(View.GONE);

                deliveriedIndicator.setVisibility(View.GONE);
                deliveriedTitle.setVisibility(View.GONE);
                deliveriedDate.setVisibility(View.GONE);
                deliveriedBody.setVisibility(View.GONE);
                break;
            case "Out for delivery":
                orderedTitle.setText("Ordered");
                orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                orderedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getOrderdDate())));
                orderedBody.setText("Hàng đã được đặt");

                packedTitle.setText("Packed");
                packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                packedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getPackedDate())));
                packedBody.setText("Hàng đã đóng gói");

                shippedTitle.setText("Shipped");
                shippedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                shippedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getShipedDate())));
                shippedBody.setText("Hàng đã vận chuyển");

                deliveriedTitle.setText("Out for Delivery");
                deliveriedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                deliveriedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getDeliveriedDate())));
                deliveriedBody.setText("Hàng đã chuyển giao đến nơi");

                orderedProgress.setProgress(100);
                packedProgress.setProgress(100);
                shippedProgress.setProgress(100);
            case "Delivered":
                orderedTitle.setText("Ordered");
                orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                orderedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getOrderdDate())));
                orderedBody.setText("Hàng đã được đặt");

                packedTitle.setText("Packed");
                packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                packedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getPackedDate())));
                packedBody.setText("Hàng đã đóng gói");

                shippedTitle.setText("Shipped");
                shippedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                shippedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getShipedDate())));
                shippedBody.setText("Hàng đã vận chuyển");

                deliveriedTitle.setText("Deliveried");
                deliveriedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                deliveriedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getDeliveriedDate())));
                deliveriedBody.setText("Hàng đã chuyển giao đến nơi");

                orderedProgress.setProgress(100);
                packedProgress.setProgress(100);
                shippedProgress.setProgress(100);

                break;
            case "Cancelled":
                if(myOrderItemModel.getPackedDate().after(myOrderItemModel.getOrderdDate())) {
                    if (myOrderItemModel.getShipedDate().after(myOrderItemModel.getPackedDate())) {
                        orderedTitle.setText("Ordered");
                        orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                        orderedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getOrderdDate())));

                        packedTitle.setText("Packed");
                        packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                        packedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getPackedDate())));

                        shippedTitle.setText("Shipped");
                        shippedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                        shippedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getShipedDate())));

                        deliveriedTitle.setText("Cancelled");
                        deliveriedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorRed)));
                        deliveriedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getCancelDate())));
                        deliveriedBody.setText("Hàng đã hủy đặt");
                        deliveriedBody.setTextColor(Color.parseColor("#ff142b"));
                        deliveriedBody.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff142b")));

                        orderedProgress.setProgress(100);
                        packedProgress.setProgress(100);
                        shippedProgress.setProgress(100);
                    }else {
                        orderedTitle.setText("Ordered");
                        orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                        orderedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getOrderdDate())));

                        packedTitle.setText("Packed");
                        packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                        packedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getPackedDate())));

                        shippedTitle.setText("Cancelled");
                        shippedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorRed)));
                        shippedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getCancelDate())));
                        shippedBody.setText("Hàng đã hủy đặt");
                        shippedBody.setTextColor(Color.parseColor("#ff142b"));
                        shippedBody.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff142b")));


                        orderedProgress.setProgress(100);
                        packedProgress.setProgress(100);
                        shippedProgress.setVisibility(View.GONE);

                        deliveriedIndicator.setVisibility(View.GONE);
                        deliveriedTitle.setVisibility(View.GONE);
                        deliveriedDate.setVisibility(View.GONE);
                        deliveriedBody.setVisibility(View.GONE);
                    }
                }else {
                    orderedTitle.setText("Ordered");
                    orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                    orderedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getOrderdDate())));

                    packedTitle.setText("Cancelled");
                    packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorRed)));
                    packedDate.setText(String.valueOf(simpleDateFormat.format(myOrderItemModel.getCancelDate())));
                    packedBody.setText("Đã hủy đặt hàng");
                    packedBody.setTextColor(Color.parseColor("#ff142b"));
                    packedBody.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff142b")));


                    orderedProgress.setProgress(100);
                    packedProgress.setVisibility(View.GONE);
                    shippedProgress.setVisibility(View.GONE);

                    shippedIndicator.setVisibility(View.GONE);
                    shippedTitle.setVisibility(View.GONE);
                    shippedDate.setVisibility(View.GONE);
                    shippedBody.setVisibility(View.GONE);

                    deliveriedIndicator.setVisibility(View.GONE);
                    deliveriedTitle.setVisibility(View.GONE);
                    deliveriedDate.setVisibility(View.GONE);
                    deliveriedBody.setVisibility(View.GONE);

                }
        }
        //Rating
        numRating = myOrderItemModel.getNumRating();
        setRating(numRating);
        for (int x=0; x < ratingNowContainer.getChildCount(); x++){
            final  int  startPosition = x;
            ratingNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadingDialog.show();
                    setRating(startPosition);
                    final DocumentReference documentReference = FirebaseFirestore.getInstance().collection("PRODUCTS").document(myOrderItemModel.getProduct_id());
                    FirebaseFirestore.getInstance().runTransaction(new Transaction.Function<Object>() {
                        @Nullable
                        @Override
                        public Object apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                            DocumentSnapshot documentSnapshot = transaction.get(documentReference);
                            if(numRating != 0){
                                Long increase = documentSnapshot.getLong(startPosition + 1 + "_star") + 1;
                                Long decrease = documentSnapshot.getLong(numRating +1  + "_star") - 1;
                                transaction.update(documentReference, startPosition + 1 + "_star", increase);
                                transaction.update(documentReference, numRating +1  + "_star", decrease);
                            }else {
                                Long increase = documentSnapshot.getLong(startPosition +1 + "_star") + 1;
                                transaction.update(documentReference, startPosition +1 + "_star", increase);
                            }
                            return null;
                        }
                    }).addOnSuccessListener(new OnSuccessListener<Object>() {

                        @Override
                        public void onSuccess(Object o) {
                            Map<String, Object> myratting = new HashMap<>();
                            if (DbQueries.myratting_ids.contains(myOrderItemModel.getProduct_id())) {
                                myratting.put("ratting_" + DbQueries.myratting_ids.indexOf(myOrderItemModel.getProduct_id()), (long) startPosition + 1);
                            } else {
                                myratting.put("list_size", (long) DbQueries.myratting_ids.size() + 1);
                                myratting.put("product_id_" + DbQueries.myratting_ids.size(), myOrderItemModel.getProduct_id());
                                myratting.put("ratting_" + DbQueries.myratting_ids.size(), (long) startPosition + 1);
                            }

                            FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_RATTING")
                                    .update(myratting).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        DbQueries.myOrderItemModelList.get(position).setNumRating(startPosition);;
                                        if(DbQueries.myratting_ids.contains(myOrderItemModel.getProduct_id())){
                                            DbQueries.myRatting.set(DbQueries.myratting_ids.indexOf(myOrderItemModel.getProduct_id()), Long.parseLong(String.valueOf(startPosition +1)));
                                        }else {
                                            DbQueries.myratting_ids.add(myOrderItemModel.getProduct_id());
                                            DbQueries.myRatting.add(Long.parseLong(String.valueOf(startPosition +1)));
                                        }

                                    }else {
                                        Toast.makeText(OrderDateilsActivity.this, "Lỗi ", Toast.LENGTH_SHORT).show();
                                    }
                                    loadingDialog.dismiss();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loadingDialog.dismiss();
                        }
                    });
                }
            });
        }
        //Rating
        if(myOrderItemModel.isCancelationOrderRequest()){
            btn_cancel_order.setVisibility(View.VISIBLE);
            btn_cancel_order.setEnabled(false);
            btn_cancel_order.setText("Đang hủy order");
            btn_cancel_order.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
            btn_cancel_order.setTextColor(getResources().getColor(R.color.white));
        }else {
            if(myOrderItemModel.getOrderStatus().equals("Ordered") || myOrderItemModel.getOrderStatus().equals("Packed") ){
                btn_cancel_order.setVisibility(View.VISIBLE);
                btn_cancel_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelDialog.findViewById(R.id.btn_no_cancel_order).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cancelDialog.dismiss();
                            }
                        });
                        cancelDialog.findViewById(R.id.btn_cancel_order).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cancelDialog.dismiss();
                                loadingDialog.show();
                                Map<String, Object> map = new HashMap<>();
                                map.put("Order_ID", myOrderItemModel.getOrderID());
                                map.put("Product_ID", myOrderItemModel.getProduct_id());
                                map.put("Order_Cancelled", false);

                                FirebaseFirestore.getInstance().collection("CANCELLED ORDERS").document().set(map)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    FirebaseFirestore.getInstance().collection("ORDERS").document(myOrderItemModel.getOrderID()).collection("OrderItems").document(myOrderItemModel.getProduct_id()).update("Cancellation_Requested", true)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if(task.isSuccessful()){
                                                                        myOrderItemModel.setCancelationOrderRequest(true);
                                                                        btn_cancel_order.setEnabled(false);
                                                                        btn_cancel_order.setText("Đang hủy order");
                                                                        btn_cancel_order.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                                                                        btn_cancel_order.setTextColor(getResources().getColor(R.color.colorRed));

                                                                    }else {
                                                                        String error = task.getException().getMessage();
                                                                        Toast.makeText(OrderDateilsActivity.this, "Error: "  + error, Toast.LENGTH_SHORT).show();
                                                                    }
                                                                    loadingDialog.dismiss();
                                                                }
                                                            });
                                                }else {
                                                    loadingDialog.dismiss();
                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(OrderDateilsActivity.this, "Error: "  + error, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        });
                        cancelDialog.show();
                    }
                });
            }
        }
        fullname.setText(myOrderItemModel.getFullname());
        address.setText(myOrderItemModel.getAddress());
        phoneNumber.setText(myOrderItemModel.getPhoneNumber());

        Long totalItemsPriceValue;

        totalItems.setText("Số tiền của "+ myOrderItemModel.getProductQuantity()  + " sản phẩm: ");
        if(myOrderItemModel.getDiscountedPrice().equals("")){
            totalItemsPriceValue = myOrderItemModel.getProductQuantity()*Long.valueOf(myOrderItemModel.getProductPrice());
            totalItemsPrice.setText(totalItemsPriceValue + " đ");
        }else {
            totalItemsPriceValue = myOrderItemModel.getProductQuantity()*Long.valueOf(myOrderItemModel.getDiscountedPrice());
            totalItemsPrice.setText(totalItemsPriceValue + " đ");
        }
        if(Integer.parseInt(myOrderItemModel.getProductPrice()) > 1000000){
            deliveryPrice.setText("Miễn phí giao hàng");
            totalAmount.setText(totalItemsPrice.getText());
        }else {
            deliveryPrice.setText("40000đ");
            totalAmount.setText(Long.valueOf(40000) + totalItemsPriceValue + " đ");
        }
//        String delivry = myOrderItemModel.getDeliveryPrice();
//        Log.d("delivery", delivry);
//        if(myOrderItemModel.getDeliveryPrice().equals("Free")){
//            deliveryPrice.setText("Miễn phí giao hàng");
//            totalAmount.setText(totalItemsPrice.getText() + "đ");
//        }else {
//            deliveryPrice.setText(myOrderItemModel.getDeliveryPrice()+"đ");
//            totalAmount.setText((myOrderItemModel.getDeliveryPrice() + totalItemsPriceValue) + " đ");
//        }
        if(!myOrderItemModel.getCuttedPrice().equals("")){
            if(!myOrderItemModel.getDiscountedPrice().equals("")){
                savedAmount.setText("Bạn đã tiết kiệm được " + myOrderItemModel.getProductQuantity() *(Long.valueOf(myOrderItemModel.getCuttedPrice()) - Long.valueOf(myOrderItemModel.getDiscountedPrice())) + "đ");
            }else {
                savedAmount.setText("Bạn đã tiết kiệm được " + myOrderItemModel.getProductQuantity() *(Long.valueOf(myOrderItemModel.getCuttedPrice()) - Long.valueOf(myOrderItemModel.getProductPrice())) + "đ");
            }
        }else {
            if(!myOrderItemModel.getDiscountedPrice().equals("")){
                savedAmount.setText("Bạn đã tiết kiệm được 0đ");
            }else {
                savedAmount.setText("Bạn đã tiết kiệm được " + myOrderItemModel.getProductQuantity() *(Long.valueOf(myOrderItemModel.getProductPrice()) - Long.valueOf(myOrderItemModel.getDiscountedPrice())) + "đ");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private void setRating(int startPosition) {
        for(int x = 0; x < ratingNowContainer.getChildCount(); x++){
            ImageView starbtn = (ImageView) ratingNowContainer.getChildAt(x);
            starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if(x  <= startPosition){
                starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }
        }
    }
}
