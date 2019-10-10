package com.thud.myecormerce.Fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.thud.myecormerce.Models.MyOrderItemModel;
import com.thud.myecormerce.Presenter.DbQueries;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.DeliveryActivity;
import com.thud.myecormerce.View.MainActivity;
import com.thud.myecormerce.View.MyAddressActivity;
import com.thud.myecormerce.View.RegisterActivity;
import com.thud.myecormerce.View.UpdateUserInforActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {


    public MyAccountFragment() {
        // Required empty public constructor
    }
    public static final int MANAGE_ADDRESS = 1;
    private Button btn_viewall_address, btn_sign_out;
    private Dialog loadingDialog;

    private CircleImageView profile_image, curent_order_image;
    private TextView fulname, email, currentOrderStatus;
    private LinearLayout layoutContainer, layoutRecent;

    private ImageView orderedIndicator, packedIndicator, shippedIndicator, deliveriedIndicator;
    private ProgressBar orderedProgress, packedProgress, shippedProgress;

    private TextView recent_title;
    private TextView fullname_address, address, phonenumber;

    private FloatingActionButton settingAccount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_account, container, false);
        btn_viewall_address = view.findViewById(R.id.btn_viewall_address_account);
        btn_sign_out = view.findViewById(R.id.btn_signout_account);
        profile_image = view.findViewById(R.id.profile_image_myaccount);
        fulname = view.findViewById(R.id.txt_username_account);
        email = view.findViewById(R.id.txt_email_account);
        layoutContainer = view.findViewById(R.id.layoutContainer);
        curent_order_image = view.findViewById(R.id.imv_status_order_myaccount);
        currentOrderStatus = view.findViewById(R.id.txt_current_order_status_account);
        settingAccount = view.findViewById(R.id.floatint_setting_account);

        orderedIndicator = view.findViewById(R.id.imv_ordered_indicator_myaccount);
        packedIndicator = view.findViewById(R.id.imv_packed_indocator_myaccount);
        shippedIndicator = view.findViewById(R.id.imv_shipped_indicator_myaccount);
        deliveriedIndicator = view.findViewById(R.id.imv_dilivered_indicator_myaccount);

        orderedProgress = view.findViewById(R.id.progress_ordered_packed_myaccount);
        packedProgress = view.findViewById(R.id.progress_packed_shipped_myaccount);
        shippedProgress = view.findViewById(R.id.progress_shipped_delivered_myaccount);

        recent_title = view.findViewById(R.id.txt_title_your_recent_order_account);
        layoutRecent = view.findViewById(R.id.liner_order_recent_account);

        fulname= view.findViewById(R.id.txt_full_name_address_account);
        address = view.findViewById(R.id.txt_address_account);
        phonenumber = view.findViewById(R.id.txt_phonenumber_account);
        fullname_address = view.findViewById(R.id.txt_full_name_address_account);



        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_main));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();



        layoutContainer.getChildAt(1).setVisibility(View.GONE);

        loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                for(MyOrderItemModel myOrderItemModel: DbQueries.myOrderItemModelList){
                    if(!myOrderItemModel.isCancelationOrderRequest()){
                        if(!myOrderItemModel.getOrderStatus().equals("Delivered") && !myOrderItemModel.getOrderStatus().equals("Cancelled")){
                            layoutContainer.getChildAt(1).setVisibility(View.VISIBLE);
                            Glide.with(getContext()).load(myOrderItemModel.getProductImage()).apply(new RequestOptions().placeholder(R.drawable.image_place)).into(curent_order_image);
                            currentOrderStatus.setText(myOrderItemModel.getOrderStatus());
                            switch (myOrderItemModel.getOrderStatus()){
                                case "Ordered":
                                    orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                                    break;
                                case "Packed":
                                    orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                                    packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                                    orderedProgress.setProgress(100);
                                    break;
                                case "Shipped":
                                    orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                                    packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                                    shippedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                                    orderedProgress.setProgress(100);
                                    packedProgress.setProgress(100);
                                    break;
                                case "Out for delivery":
                                    orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                                    packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                                    shippedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                                    deliveriedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreenDark)));
                                    orderedProgress.setProgress(100);
                                    packedProgress.setProgress(100);
                                    shippedProgress.setProgress(100);
                                    break;
                            }

                        }
                    }
                }
                int i = 0;
                for(MyOrderItemModel orderItemModel: DbQueries.myOrderItemModelList) {
                    if(i < 4) {
                        if (orderItemModel.getOrderStatus().equals("Delivered")) {
                            Glide.with(getContext()).load(orderItemModel.getProductImage()).apply(new RequestOptions().placeholder(R.drawable.image_place)).into((CircleImageView) layoutRecent.getChildAt(i));
                            i++;
                        }
                    }else {
                        break;
                    }
                }
                if(i == 0){
                    recent_title.setText("Không có san pham order gần đây");
                }
                if(i < 3){
                    for (int x = i; x < 4; x++){
                        layoutRecent.getChildAt(x).setVisibility(View.GONE);
                    }
                }
                loadingDialog.show();
                loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        loadingDialog.setOnDismissListener(null);
                        if(DbQueries.addressModelList.size() == 0 ){
                            address.setText("Address: ----");
                            fullname_address.setText("Họ & tên: ---");
                            phonenumber.setText("DĐ: ---");
                        }else {
                            setAddress();
                        }
                    }
                });
                DbQueries.loadAddress(getContext(), loadingDialog, false);
            }
        });
        DbQueries.loadOrders(getContext(), null, loadingDialog);
        btn_viewall_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentManage = new Intent(getContext(), MyAddressActivity.class);
                intentManage.putExtra("Mode", MANAGE_ADDRESS);
                startActivity(intentManage);
            }
        });
        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                DbQueries.clearData();
                Intent intentRegis = new Intent(getContext(), RegisterActivity.class);
                startActivity(intentRegis);
                getActivity().finish();
            }
        });
        //loadingDialog.dismiss();
        settingAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateUserInfor = new Intent(getContext(), UpdateUserInforActivity.class);
                updateUserInfor.putExtra("username", fullname_address.getText());
                updateUserInfor.putExtra("email", email.getText());
                updateUserInfor.putExtra("profile", DbQueries.profile);

                startActivity(updateUserInfor);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        fulname.setText(DbQueries.fullname);
        email.setText(DbQueries.email);
        if(!DbQueries.profile.equals("")){
            Glide.with(getContext()).load(DbQueries.profile).apply(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground)).into(profile_image);
        }else {
            profile_image.setImageResource(R.drawable.ic_launcher_foreground);
        }

        if(!loadingDialog.isShowing()){
            if(DbQueries.addressModelList.size() == 0 ){
                address.setText("Address: ----");
                fullname_address.setText("Họ & tên: ---");
                phonenumber.setText("DĐ: ---");
            }else {
                setAddress();
            }
        }
    }

    private void setAddress() {
            fullname_address.setText(DbQueries.addressModelList.get(DbQueries.addressselected).getFullname());
            address.setText(DbQueries.addressModelList.get(DbQueries.addressselected).getLocationDetail() + DbQueries.addressModelList.get(DbQueries.addressselected).getProvince() + " " + DbQueries.addressModelList.get(DbQueries.addressselected).getCountry());
            phonenumber.setText(DbQueries.addressModelList.get(DbQueries.addressselected).getPhone());
    }

}
