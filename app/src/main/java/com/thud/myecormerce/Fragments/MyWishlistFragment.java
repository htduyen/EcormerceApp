package com.thud.myecormerce.Fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.thud.myecormerce.Adapter.WishlistAdapter;
import com.thud.myecormerce.Models.WishlistModel;
import com.thud.myecormerce.Presenter.DbQueries;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.ProductDetailActivity;

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
    private Dialog loadingDialog;
    public static WishlistAdapter wishlistAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wishlist, container, false);
        recyclerView_wishlist = view.findViewById(R.id.recyclerview_my_wishlist_fragment);

        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCancelable(false);
        //loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_main));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_wishlist.setLayoutManager(linearLayoutManager);


        if(DbQueries.wishlistModelList.size() == 0){

            DbQueries.id_wishlist.clear();
            DbQueries.loadWishlist(getContext(), loadingDialog, true);

        }
        else {
            loadingDialog.dismiss();
        }

        wishlistAdapter = new WishlistAdapter(DbQueries.wishlistModelList, true);
        recyclerView_wishlist.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();

        return  view;
    }

}
