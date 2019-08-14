package com.thud.myecormerce.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.thud.myecormerce.Adapter.AddressAdapter;
import com.thud.myecormerce.Models.AddressModel;
import com.thud.myecormerce.R;

import java.util.ArrayList;
import java.util.List;

public class MyAddressActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerViewAddress;
    private static AddressAdapter addressAdapter;
    private Button btn_delivery_here;
    private int mode_intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");

        btn_delivery_here = findViewById(R.id.btn_delivery_here_myaddress);
        recyclerViewAddress = findViewById(R.id.recyclerview_item_address);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewAddress.setLayoutManager(linearLayoutManager);

        List<AddressModel> addressModelList = new ArrayList<>();
        addressModelList.add(new AddressModel("Huynh Thanh Duyen", "67, Mậu Thân, Ninh Kiều, Cần Thơ", "0357027748", true));
        addressModelList.add(new AddressModel("Huynh Thanh Duyen", "68, Mậu Thân, Ninh Kiều, Cần Thơ", "0357027748", false));
        addressModelList.add(new AddressModel("Huynh Thanh Duyen", "69, Mậu Thân, Ninh Kiều, Cần Thơ", "0357027748", false));
        addressModelList.add(new AddressModel("Huynh Thanh Duyen", "66, Mậu Thân, Ninh Kiều, Cần Thơ", "0357027748", false));

        mode_intent = getIntent().getIntExtra("Mode",-1);
        if(mode_intent == DeliveryActivity.SELECT_ADDRESS){
            btn_delivery_here.setVisibility(View.VISIBLE);
        }
        else {
            btn_delivery_here.setVisibility(View.GONE);
        }
        addressAdapter = new AddressAdapter(addressModelList, mode_intent);
        recyclerViewAddress.setAdapter(addressAdapter);
        ((SimpleItemAnimator)recyclerViewAddress.getItemAnimator()).setSupportsChangeAnimations(false);
        addressAdapter.notifyDataSetChanged();
    }
    public static void refeshItem(int deselect, int select){
        addressAdapter.notifyItemChanged(deselect);
        addressAdapter.notifyItemChanged(select);
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
