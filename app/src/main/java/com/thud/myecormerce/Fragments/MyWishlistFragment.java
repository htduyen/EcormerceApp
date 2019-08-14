package com.thud.myecormerce.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thud.myecormerce.Adapter.WishlistAdapter;
import com.thud.myecormerce.Models.WishlistModel;
import com.thud.myecormerce.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyWishlistFragment extends Fragment {


    public MyWishlistFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView_wishlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wishlist, container, false);
        recyclerView_wishlist = view.findViewById(R.id.recyclerview_my_wishlist_fragment);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_wishlist.setLayoutManager(linearLayoutManager);

        List<WishlistModel> wishlistModelList = new ArrayList<>();
        wishlistModelList.add(new WishlistModel(R.drawable.phone1, "Iphone 1","4,000,000 Đ","5,000,000 Đ", 2,"Tiền mặt","4.5", 67));
        wishlistModelList.add(new WishlistModel(R.drawable.phone2, "Iphone 2","4,000,000 Đ","5,000,000 Đ", 0,"Chuyển khoản","4.5", 27));
        wishlistModelList.add(new WishlistModel(R.drawable.phone3, "Iphone 3","4,000,000 Đ","5,000,000 Đ", 2,"Tiền mặt","4.5", 27));
        wishlistModelList.add(new WishlistModel(R.drawable.phone4, "Iphone 4","4,000,000 Đ","5,000,000 Đ", 3,"Trả góp","3.5", 32));
        wishlistModelList.add(new WishlistModel(R.drawable.phone5, "Iphone 5","4,000,000 Đ","5,000,000 Đ", 1,"Tiền mặt","2.5", 34));
        wishlistModelList.add(new WishlistModel(R.drawable.phone6, "Iphone 6","4,000,000 Đ","5,000,000 Đ", 5,"Chuyển khoản","4.0", 100));

        WishlistAdapter wishlistAdapter = new WishlistAdapter(wishlistModelList, true);
        recyclerView_wishlist.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();

        return  view;
    }

}
