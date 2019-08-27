package com.thud.myecormerce.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thud.myecormerce.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDescriptionFragment extends Fragment {


    public ProductDescriptionFragment() {
        // Required empty public constructor
    }
    private TextView  txt_description;
    public  String body;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_product_description, container, false);
        txt_description = view.findViewById(R.id.txt_product_descr_tab);
        txt_description.setText(body);
//        if(tabPosition == 0){
//            txt_description.setText(productDescription);
//        }
//        else {
//            txt_description.setText(productOrtherDetails);
//        }

        return view;
    }

}
