package com.thud.myecormerce.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.thud.myecormerce.R;
import com.thud.myecormerce.View.MyAddressActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {


    public MyAccountFragment() {
        // Required empty public constructor
    }
    public static final int MANAGE_ADDRESS = 1;
    private Button btn_viewall_address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_account, container, false);
        btn_viewall_address = view.findViewById(R.id.btn_viewall_address_account);
        btn_viewall_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentManage = new Intent(getContext(), MyAddressActivity.class);
                intentManage.putExtra("Mode", MANAGE_ADDRESS);
                startActivity(intentManage);
            }
        });
        return view;
    }

}
