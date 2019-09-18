package com.thud.myecormerce.Fragments;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thud.myecormerce.Adapter.CartAdapter;
import com.thud.myecormerce.Adapter.WishlistAdapter;
import com.thud.myecormerce.Models.CartItemModel;
import com.thud.myecormerce.Presenter.DbQueries;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.AddAddressActivity;
import com.thud.myecormerce.View.DeliveryActivity;
import com.thud.myecormerce.View.MainActivity;
import com.thud.myecormerce.View.ProductDetailActivity;

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
    private Button btn_continute;
    private TextView txt_total_amount;

    private Dialog loadingDialog;
    public static CartAdapter cartlistAdapter;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerViewCartItems = view.findViewById(R.id.cart_items_recyclerview_cartfragment);
        btn_continute = view.findViewById(R.id.btn_continue);
        txt_total_amount = view.findViewById(R.id.total_cart_cart_fragment);
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCancelable(false);
        //loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_main));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewCartItems.setLayoutManager(linearLayoutManager);
//        List<CartItemModel> cartItemModelList = new ArrayList<>();
//        cartItemModelList.add(new CartItemModel(1, "Số sản phẩm: 3", "30,000,000 Đ","Free","3,000,000 Đ", "30,000,000 Đ"));
        cartlistAdapter = new CartAdapter(DbQueries.cartItemModelList, txt_total_amount, true);
        recyclerViewCartItems.setAdapter(cartlistAdapter);
        cartlistAdapter.notifyDataSetChanged();


        btn_continute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DeliveryActivity.cartItemModelList = new ArrayList<>();
               DeliveryActivity.fromCart = true;
               for(int x = 0; x < DbQueries.cartItemModelList.size(); x ++){
                   CartItemModel cartItemModel = DbQueries.cartItemModelList.get(x);
                   if(cartItemModel.isInstock()){
                       DeliveryActivity.cartItemModelList.add(cartItemModel);
                   }
               }
               DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));
                loadingDialog.show();
//               Intent intent = new Intent(getContext(), AddAddressActivity.class);
//               intent.putExtra("INTENT", "deliveryIntent");
//               startActivity(intent);
//               loadingDialog.dismiss();
                if(DbQueries.addressModelList.size() == 0) {
                    DbQueries.loadAddress(getContext(), loadingDialog);
                }else {
                    loadingDialog.dismiss();
                    Intent intentDelivery = new Intent(getContext(), DeliveryActivity.class );
                    startActivity(intentDelivery);
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        cartlistAdapter.notifyDataSetChanged();
        if(DbQueries.cartItemModelList.size() == 0){
            DbQueries.cartlist.clear();
            DbQueries.loadCartList(getContext(), loadingDialog, true,new TextView(getContext()), txt_total_amount);
        }
        else {
            if(DbQueries.cartItemModelList.get(DbQueries.cartItemModelList.size() -1).getType() == CartItemModel.TOTAL_AMOUNT)
            {
                LinearLayout parent = (LinearLayout) txt_total_amount.getParent().getParent();
                parent.setVisibility(View.VISIBLE);

            }
            loadingDialog.dismiss();
        }
    }
}
