package com.thud.myecormerce.View;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.TransactionInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.thud.myecormerce.Adapter.CartAdapter;
import com.thud.myecormerce.Fragments.CartFragment;
import com.thud.myecormerce.Models.CartItemModel;
import com.thud.myecormerce.Presenter.Constants;
import com.thud.myecormerce.Presenter.DbQueries;
import com.thud.myecormerce.R;
import com.thud.myecormerce.pay.PaymentsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class DeliveryActivity extends AppCompatActivity {

    public static  List<CartItemModel> cartItemModelList;
    public static CartAdapter cartAdapter;
    private Toolbar toolbar;
    private RecyclerView recyclerViewDelivery;
    private Button btn_changeOrAddAddress;
    private Button btn_continute;
    private TextView totalAmount;
    private Dialog paymentDialog;
    private Dialog loadingDialog;
    public static final int SELECT_ADDRESS = 0;

    private TextView txt_fullname;
    private TextView txt_address;
    private TextView txt_phonenumber;

    //Payment Method
    private ImageButton btn_cod_payment;
    private ImageButton btn_google_payment;
    //Payment Method

    //Order confirm layout
    private ConstraintLayout constraint_order_confirm_layout;
    private TextView txt_order_id_confirm;
    private TextView txt_continute_shopping_confirm;
    private ImageButton btn_continute_shopping_confirm;
    //Order confirm layout

    //Payment
    private static final int PAYMENT_DATA_REQUEST_CODE = 991;
    private static PayPalConfiguration configuration = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Constants.CLIENT_ID);
    String total_amount;
    private boolean successResponse = false;
    public static boolean fromCart;
    //private TextView mGooglePayStatusText;

    private FirebaseFirestore firebaseFirestore;
    public static boolean allProductAvailable;
    public static boolean getQuantityIDs = true;


    //Payment


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        firebaseFirestore = FirebaseFirestore.getInstance();
        getQuantityIDs = true;
        allProductAvailable = true;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Giao Hàng");

        paymentDialog = new Dialog(DeliveryActivity.this);
        paymentDialog.setContentView(R.layout.payment_mothod);
        paymentDialog.setCancelable(true);
        paymentDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_main));
        paymentDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        loadingDialog = new Dialog(DeliveryActivity.this);
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_main));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        recyclerViewDelivery = findViewById(R.id.recyclerview_delivery);
        btn_changeOrAddAddress = findViewById(R.id.btn_change_or_add_address);
        btn_continute = findViewById(R.id.btn_continue);
        totalAmount = findViewById(R.id.total_cart_cart_fragment);

        txt_fullname = findViewById(R.id.txt_full_name_shipping_detail);
        txt_address = findViewById(R.id.txt_full_address_detail_shipping);
        txt_phonenumber = findViewById(R.id.txt_sdt_shipping_detail);

        //Order confirm layout
        constraint_order_confirm_layout = findViewById(R.id.constrain_order_confirm_layout);
        txt_order_id_confirm = findViewById(R.id.txt_order_id_confirm);
        btn_continute_shopping_confirm = findViewById(R.id.imv_btn_continute_shopping);
        txt_continute_shopping_confirm = findViewById(R.id.txt_continute_shopping);
        //Order confirm layout


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewDelivery.setLayoutManager(linearLayoutManager);

        cartAdapter = new CartAdapter(cartItemModelList, totalAmount, false);
        recyclerViewDelivery.setAdapter(cartAdapter);
        //Toast.makeText(this, "Total Amound: " + totalAmount.getText().toString(), Toast.LENGTH_LONG).show();
        cartAdapter.notifyDataSetChanged();

        btn_changeOrAddAddress.setVisibility(View.VISIBLE);
        btn_changeOrAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuantityIDs = false;
                Intent intentAddress = new Intent(DeliveryActivity.this, MyAddressActivity.class);
                intentAddress.putExtra("Mode", SELECT_ADDRESS);
                startActivity(intentAddress);
            }
        });

        txt_fullname.setText("Họ & tên: " + DbQueries.addressModelList.get(DbQueries.addressselected).getFullname());
        txt_address.setText("Địa chỉ: " + DbQueries.addressModelList.get(DbQueries.addressselected).getAddress());
        txt_phonenumber.setText("SĐT: " + DbQueries.addressModelList.get(DbQueries.addressselected).getPhonenmuber());

        Intent startServiceIntent = new Intent(this, PayPalService.class);
        startServiceIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        startService(startServiceIntent);

        btn_continute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allProductAvailable){
                    paymentDialog.show();
                    btn_cod_payment = paymentDialog.findViewById(R.id.imv_btn_cod_payment);
                    btn_google_payment = paymentDialog.findViewById(R.id.imv_btn_online_payment);

                    btn_cod_payment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            paymentDialog.dismiss();
                            loadingDialog.dismiss();
                            getQuantityIDs = false;
    //                        loadingDialog.show();
                            if (ContextCompat.checkSelfPermission(DeliveryActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(DeliveryActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
                            }
                        }
                    });

                    btn_google_payment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(DeliveryActivity.this, "Total: " + totalAmount.getText().toString().substring(0,totalAmount.getText().length() -2), Toast.LENGTH_SHORT).show();
                            getQuantityIDs = false;
                            progressPayment();
                        }
                    });
                }else {
                    //khong
                }
            }


        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, PayPalService.class));
    }

    private void progressPayment() {
        total_amount = totalAmount.getText().toString().substring(0,totalAmount.getText().length() -2);
        //int vndong = Integer.parseInt(total_amount)/22000;
        int vndong = 1;
        PayPalPayment payPalPayment= new PayPalPayment(new BigDecimal(String.valueOf(vndong)), "USD", "Thanh toan mua hang", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(DeliveryActivity.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYMENT_DATA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PAYMENT_DATA_REQUEST_CODE){
            //Neu Thanh cong
            paymentDialog.dismiss();
            loadingDialog.show();
            successResponse = true;
            getQuantityIDs = false;
            Toast.makeText(this, "Truong hop thanh toan thanh cong", Toast.LENGTH_SHORT).show();

            //khi đã thanh toán thành công thì list giảm 1,set available = false, add user_id
            for(int x = 0; x< cartItemModelList.size()-1; x++) {
                for (String qtyID: cartItemModelList.get(x).getQuantityIDs()){
                    firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(x).getProduct_id()).collection("QUANTITY").document(qtyID).update("user_id", FirebaseAuth.getInstance().getUid());
                }
            }

            if(MainActivity.mainActivity != null){
                MainActivity.mainActivity.finish();
                MainActivity.mainActivity = null;
                MainActivity.SHOW_CART = false;
            }else {
                MainActivity.resetMainActivity = true;
            }
            if(ProductDetailActivity.productDetailActivity != null){
                ProductDetailActivity.productDetailActivity.finish();
                ProductDetailActivity.productDetailActivity = null;
            }
            if(fromCart){
                loadingDialog.show();
                Map<String,Object> updateCartlist = new HashMap<>();
                long cartListSize = 0;
                final List<Integer> indexList = new ArrayList<>();
                for(int x = 0; x < DbQueries.cartlist.size(); x++){
                    if(!cartItemModelList.get(x).isInstock()) {
                        updateCartlist.put("product_id_" + cartListSize, DbQueries.cartItemModelList.get(x).getProduct_id());
                        cartListSize++;
                    }
                    else {
                        indexList.add(x);
                    }
                }
                updateCartlist.put("list_size", (long)cartListSize);
                FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_CART")
                        .set(updateCartlist).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            for(int x =0; x < indexList.size(); x++){
                                DbQueries.cartlist.remove(indexList.get(x).intValue());
                                DbQueries.cartItemModelList.remove(indexList.get(x).intValue());
                                DbQueries.cartItemModelList.remove(DbQueries.cartItemModelList.size() -1);
                            }
                        }
                        else {
                            String ex = task.getException().getMessage();
                            Toast.makeText(DeliveryActivity.this, "Error: " + ex, Toast.LENGTH_SHORT).show();
                        }
                        loadingDialog.dismiss();
                    }
                });
            }
            btn_continute.setEnabled(false);
            btn_changeOrAddAddress.setEnabled(false);
            constraint_order_confirm_layout.setVisibility(View.VISIBLE);
            txt_order_id_confirm.setText("Your order id: " + "1234567");
            btn_continute_shopping_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            
            txt_continute_shopping_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            //TH thanh cong
            if(resultCode == RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation != null){
                    try {
                        String paymentDetail = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this, PaymentDetailsActivity.class)
                                        .putExtra("PaymentDetail", paymentDetail)
                                        .putExtra("PaymentAmount", total_amount));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Loi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }else if(requestCode == Activity.RESULT_CANCELED){
                Toast.makeText(this, "Cancel payment", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Toast.makeText(this, "Khong hop le", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(getQuantityIDs) {
            for (int x = 0; x < cartItemModelList.size() - 1; x++) {
                for(int y =0; y < cartItemModelList.get(x).getProductQuantity(); y++){
                    final String quantityDocName = UUID.randomUUID().toString().substring(0,20);
                    Map<String, Object> time = new HashMap<>();
                    time.put("time", FieldValue.serverTimestamp());
                    final int finalX = x;
                    final int finalY = y;
                    firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(x).getProduct_id()).collection("QUANTITY").document(quantityDocName).set(time).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                cartItemModelList.get(finalX).getQuantityIDs().add(quantityDocName);
                                if(finalY + 1 == cartItemModelList.get(finalX).getProductQuantity()){
                                    firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(finalX).getProduct_id()).collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING).limit(cartItemModelList.get(finalX).getStockQuantity()).get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()){
                                                        List<String> serverQuanties = new ArrayList<>();
                                                        for (QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()){
                                                            serverQuanties.add(queryDocumentSnapshot.getId());
                                                        }
                                                        long availableQty = 0;
                                                        boolean noLongerAvailable = true;
                                                        for (String qtyID: cartItemModelList.get(finalX).getQuantityIDs()){
                                                            if(!serverQuanties.contains(qtyID)){
                                                                if(noLongerAvailable){
                                                                    cartItemModelList.get(finalX).setInstock(false);
                                                                }else {
                                                                    cartItemModelList.get(finalX).setQtyError(true);
                                                                    cartItemModelList.get(finalX).setMaxQuantity(availableQty);
                                                                    Toast.makeText(DeliveryActivity.this, "Các sản phẩm không có sẵn", Toast.LENGTH_SHORT).show();
                                                                }
                                                                allProductAvailable = false;
                                                            }else {
                                                                availableQty++;
                                                                noLongerAvailable= false;
                                                            }
                                                        }
                                                        cartAdapter.notifyDataSetChanged();
                                                    }else {
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(DeliveryActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }

                            }
                            else {
                                String error = task.getException().getMessage();
                                Toast.makeText(DeliveryActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }else {
            getQuantityIDs  = true;
        }
        txt_fullname.setText("Họ & tên: " + DbQueries.addressModelList.get(DbQueries.addressselected).getFullname());
        txt_address.setText("Địa chỉ: " + DbQueries.addressModelList.get(DbQueries.addressselected).getAddress());
        txt_phonenumber.setText("SĐT: " + DbQueries.addressModelList.get(DbQueries.addressselected).getPhonenmuber());

    }

    @Override
    protected void onPause() {
        super.onPause();
        loadingDialog.dismiss();
        if(getQuantityIDs) {
            for (int x = 0; x < cartItemModelList.size() - 1; x++) {
                if(!successResponse) {
                    for (final String qtyID : cartItemModelList.get(x).getQuantityIDs()) {
                        final int finalX = x;
                        final int finalX1 = x;
                        firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(x).getProduct_id()).collection("QUANTITY").document(qtyID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    if(qtyID.equals(cartItemModelList.get(finalX).getQuantityIDs().get(cartItemModelList.get(finalX).getQuantityIDs().size() -1))){
                                        cartItemModelList.get(finalX1).getQuantityIDs().clear();

                                    }
                                }else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(DeliveryActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }else {
                    cartItemModelList.get(x).getQuantityIDs().clear();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(successResponse){
            finish();
            return;
        }
    }
}
