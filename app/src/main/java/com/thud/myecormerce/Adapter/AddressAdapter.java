package com.thud.myecormerce.Adapter;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.ConnectionErrorMessages;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thud.myecormerce.Fragments.MyAccountFragment;
import com.thud.myecormerce.Models.AddressModel;
import com.thud.myecormerce.Presenter.DbQueries;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.AddAddressActivity;
import com.thud.myecormerce.View.DeliveryActivity;
import com.thud.myecormerce.View.MyAddressActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private  List<AddressModel> addressModelList;
    private  int MODE;
    private int preSelectedPosition;
    private boolean refesh = false;
    private Dialog loadingDialog;

    public AddressAdapter(List<AddressModel> addressModelList, int MODE,Dialog loadingDialog) {
        this.addressModelList = addressModelList;
        this.MODE = MODE;
        preSelectedPosition = DbQueries.addressselected;
        this.loadingDialog = loadingDialog;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_address_layout, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String name = addressModelList.get(position).getFullname();
        String gioitinh = addressModelList.get(position).getGioiTinh();
        String phone = addressModelList.get(position).getPhone();
        String province = addressModelList.get(position).getProvince();
        String country = addressModelList.get(position).getCountry();
        String locationDetail = addressModelList.get(position).getLocationDetail();
        boolean selected = addressModelList.get(position).getSelected();

        viewHolder.setData(province, country,locationDetail, name, gioitinh, phone, selected, position);
    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView fullname;
        private TextView address;
        private TextView phone;
        private ImageView iconcheck;
        private LinearLayout menu_option;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.txt_full_name_myaddress);
            address = itemView.findViewById(R.id.txt_address_myaddress);
            phone = itemView.findViewById(R.id.txt_phone_number_myaddress);
            iconcheck = itemView.findViewById(R.id.icon_check_myaddress);
            menu_option = itemView.findViewById(R.id.option_menu_address);
        }
        private void setData(String province,String country,String locationDetail, String username,String gender, String phonenum, final Boolean selected, final int position){

            fullname.setText(username + " - Giới tính: " + gender);
            address.setText(locationDetail  + " " + province+ " " + country);
            phone.setText(phonenum);


            if(MODE == DeliveryActivity.SELECT_ADDRESS){
                iconcheck.setImageResource(R.drawable.checked);
                if(selected){
                    iconcheck.setVisibility(View.VISIBLE);
                    preSelectedPosition = position;
                }
                else {
                    iconcheck.setVisibility(View.INVISIBLE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(preSelectedPosition != position){
                            addressModelList.get(position).setSelected(true);
                            addressModelList.get(preSelectedPosition).setSelected(false);
                            MyAddressActivity.refeshItem(preSelectedPosition,position);
                            preSelectedPosition = position;
                            DbQueries.addressselected  = position;
                        }
                    }
                });
            }
            else if(MODE == MyAccountFragment.MANAGE_ADDRESS) {
                menu_option.setVisibility(View.GONE);
                //edit address
                menu_option.getChildAt(0).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentAddNew = new Intent(itemView.getContext(), AddAddressActivity.class);
                        intentAddNew.putExtra("INTENT", "update_address");
                        intentAddNew.putExtra("position", position);
                        itemView.getContext().startActivity(intentAddNew);
                    }
                });
                //remove address
                menu_option.getChildAt(1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadingDialog.show();
                        Map<String, Object> address = new HashMap<>();
                        int x = 0;
                        int selected = -1;
                        for (int i = 0; i < addressModelList.size(); i++) {
                            if (i != position) {
                                x++;
                                address.put("province_" + x, addressModelList.get(i).getProvince());
                                address.put("country_" + x, addressModelList.get(i).getCountry());
                                address.put("locationDetail_" + x, addressModelList.get(i).getLocationDetail());
                                address.put("full_name_" + x, addressModelList.get(i).getFullname());
                                address.put("gender_" + x, addressModelList.get(i).getGioiTinh());
                                address.put("email_" + x, addressModelList.get(i).getEmail());
                                address.put("phone_number_" + x, addressModelList.get(i).getPhone());

                                if (addressModelList.get(position).getSelected()) {
                                    if (position - 1 >= 0) {
                                        if (x == position) {
                                            address.put("selected_" + x, true);
                                            selected = x;
                                        } else {
                                            address.put("selected_" + x, addressModelList.get(i).getSelected());
                                        }
                                    } else {
                                        if (x == 1) {
                                            address.put("selected_" + x, true);
                                            selected = x;
                                        } else {
                                            address.put("selected_" + x, addressModelList.get(i).getSelected());
                                        }
                                    }
                                } else {
                                    address.put("selected_" + x, addressModelList.get(i).getSelected());
                                    if(addressModelList.get(i).getSelected()){
                                        selected = x;
                                    }
                                }
                            }
                            }
                            address.put("list_size", x);
                            final int finalSlected = selected;
                            FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_ADDRESS")
                                    .set(address).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        DbQueries.addressModelList.remove(position);
                                        if(finalSlected != -1) {
                                            DbQueries.addressselected = finalSlected - 1;
                                            DbQueries.addressModelList.get(finalSlected -1).setSelected(true);
                                        }else if(DbQueries.addressModelList.size()== 0) {
                                            DbQueries.addressselected = -1;
                                        }
                                        notifyDataSetChanged();
                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(itemView.getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                                    }
                                    loadingDialog.dismiss();
                                }
                            });
                            refesh = false;
                        }
                });

                iconcheck.setImageResource(R.drawable.option_menu);
                iconcheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menu_option.setVisibility(View.VISIBLE);
                        if(refesh){
                            MyAddressActivity.refeshItem(preSelectedPosition,preSelectedPosition);
                        }else {
                            refesh = true;
                        }
                        preSelectedPosition = position;
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyAddressActivity.refeshItem(preSelectedPosition,preSelectedPosition);
                        preSelectedPosition = -1;
                    }
                });
            }
        }
    }
}
