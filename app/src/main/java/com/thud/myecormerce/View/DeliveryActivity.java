package com.thud.myecormerce.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.thud.myecormerce.Adapter.CartAdapter;
import com.thud.myecormerce.Fragments.CartFragment;
import com.thud.myecormerce.Models.CartItemModel;
import com.thud.myecormerce.R;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerViewDelivery;
    private Button btn_changeOrAddAddress;
    private Button btn_continute;
    public static final int SELECT_ADDRESS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");

        recyclerViewDelivery = findViewById(R.id.recyclerview_delivery);
        btn_changeOrAddAddress = findViewById(R.id.btn_change_or_add_address);
        btn_continute = findViewById(R.id.btn_continue);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewDelivery.setLayoutManager(linearLayoutManager);

        List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0,R.drawable.phone1, "Iphone 1", "10,000,000 Đ", "11,000,000 Đ",2,1,0,0 ));
        cartItemModelList.add(new CartItemModel(0,R.drawable.phone2, "Iphone 2", "10,000,000 Đ", "11,000,000 Đ",2,1,1,1 ));
        cartItemModelList.add(new CartItemModel(0,R.drawable.phone3, "Iphone 3", "10,000,000 Đ", "11,000,000 Đ",2,1,2,3 ));

        cartItemModelList.add(new CartItemModel(1, "Số sản phẩm: 3", "30,000,000 Đ","Free","3,000,000 Đ", "30,000,000 Đ"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        recyclerViewDelivery.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        btn_changeOrAddAddress.setVisibility(View.VISIBLE);
        btn_changeOrAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddress = new Intent(DeliveryActivity.this, MyAddressActivity.class);
                intentAddress.putExtra("Mode", SELECT_ADDRESS);
                startActivity(intentAddress);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
