package com.thud.myecormerce.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thud.myecormerce.Adapter.MyOrderAdapter;
import com.thud.myecormerce.Models.MyOrderItemModel;
import com.thud.myecormerce.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderFragment extends Fragment {


    public MyOrderFragment() {
        // Required empty public constructor
    }
    private RecyclerView recyclerViewMyOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_order, container, false);
        recyclerViewMyOrder = view.findViewById(R.id.recyclerViewMyOrder);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMyOrder.setLayoutManager(linearLayoutManager);

        List<MyOrderItemModel> myOrderItemModelList = new ArrayList<>();
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.phone1,"Iphone 7","Completed", 3));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.phone2,"Iphone 8","Completed", 4));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.phone4,"Iphone 10","Cancelled", 5));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.phone5,"Iphone X","Completed", 1));

        MyOrderAdapter myOrderAdapter = new MyOrderAdapter(myOrderItemModelList);
        recyclerViewMyOrder.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();

        return view;
    }

}
