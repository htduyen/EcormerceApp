package com.thud.myecormerce.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thud.myecormerce.Adapter.CartAdapter;
import com.thud.myecormerce.Models.CartItemModel;
import com.thud.myecormerce.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {


    public CartFragment() {
        // Required empty public constructor
    }
    private RecyclerView recyclerViewCartItems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerViewCartItems = view.findViewById(R.id.cart_items_recyclerview_cartfragment);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewCartItems.setLayoutManager(linearLayoutManager);

        List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0,R.drawable.phone1, "Iphone 1", "10,000,000 Đ", "11,000,000 Đ",2,1,0,0 ));
        cartItemModelList.add(new CartItemModel(0,R.drawable.phone2, "Iphone 2", "10,000,000 Đ", "11,000,000 Đ",2,1,1,1 ));
        cartItemModelList.add(new CartItemModel(0,R.drawable.phone3, "Iphone 3", "10,000,000 Đ", "11,000,000 Đ",2,1,2,3 ));

        cartItemModelList.add(new CartItemModel(1, "Số sản phẩm: 3", "30,000,000 Đ","Free","3,000,000 Đ", "30,000,000 Đ"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        recyclerViewCartItems.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        return view;
    }

}
