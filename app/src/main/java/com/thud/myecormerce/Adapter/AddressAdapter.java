package com.thud.myecormerce.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thud.myecormerce.Fragments.MyAccountFragment;
import com.thud.myecormerce.Models.AddressModel;
import com.thud.myecormerce.Presenter.DbQueries;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.DeliveryActivity;
import com.thud.myecormerce.View.MyAddressActivity;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private  List<AddressModel> addressModelList;
    private  int MODE;
    private int preSelectedPosition;

    public AddressAdapter(List<AddressModel> addressModelList, int MODE) {
        this.addressModelList = addressModelList;
        this.MODE = MODE;
        preSelectedPosition = DbQueries.addressselected;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_address_layout, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String name = addressModelList.get(i).getFullname();
        String address = addressModelList.get(i).getAddress();
        String phone = addressModelList.get(i).getPhonenmuber();
        Boolean selected = addressModelList.get(i).getSelected();

        viewHolder.setData(name, address, phone, selected, i);
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
        private void setData(String username, String addresss, String phonenum, final Boolean selected, final int position){
            fullname.setText(username);
            address.setText(addresss);
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
                iconcheck.setImageResource(R.drawable.option_menu);
                iconcheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menu_option.setVisibility(View.VISIBLE);
                        MyAddressActivity.refeshItem(preSelectedPosition,preSelectedPosition);
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
