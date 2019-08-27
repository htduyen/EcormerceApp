package com.thud.myecormerce.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thud.myecormerce.Adapter.ProductSpecificationAdapter;
import com.thud.myecormerce.Models.ProductSpecificationModel;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.ProductDetailActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductSpecificationFragment extends Fragment {




    public ProductSpecificationFragment() {
        // Required empty public constructor
    }
    private RecyclerView productSpecification;
    public List<ProductSpecificationModel> productSpecificationModelList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_product_specification, container, false);
        productSpecification = view.findViewById(R.id.recycler_specification_tab);

        try{
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        productSpecification.setLayoutManager(linearLayoutManager);

        //productSpecificationModelList = new ArrayList<>();
//        productSpecificationModelList.add(new ProductSpecificationModel(0,"General"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"ROM", "1T"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"CPU", "8x"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Core", "i7"));
//        productSpecificationModelList.add(new ProductSpecificationModel(0,"Waranty"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"ROM", "1T"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"CPU", "8x"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Core", "i7"));

            ProductSpecificationAdapter adapter = new ProductSpecificationAdapter(productSpecificationModelList);

            productSpecification.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }catch (Exception ex){
            Log.d("AAAA", ex.toString());
        }


        return view;
    }

}
