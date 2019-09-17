package com.thud.myecormerce.View;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thud.myecormerce.Adapter.AddressAdapter;
import com.thud.myecormerce.Models.AddressModel;
import com.thud.myecormerce.Presenter.DbQueries;
import com.thud.myecormerce.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAddressActivity extends AppCompatActivity {

    private int previousAddress;
    private Toolbar toolbar;
    private RecyclerView recyclerViewAddress;
    private static AddressAdapter addressAdapter;
    private Button btn_delivery_here;
    private LinearLayout add_new_address_myaddress;
    private TextView num_address;
    private int mode_intent;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");

        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(this.getDrawable(R.drawable.slider_main));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        previousAddress =DbQueries.addressselected;

        btn_delivery_here = findViewById(R.id.btn_delivery_here_myaddress);
        add_new_address_myaddress =findViewById(R.id.add_new_address_myaddress);
        num_address = findViewById(R.id.txt_title_num_address_saved);
        recyclerViewAddress = findViewById(R.id.recyclerview_item_address);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewAddress.setLayoutManager(linearLayoutManager);


        mode_intent = getIntent().getIntExtra("Mode",-1);
        if(mode_intent == DeliveryActivity.SELECT_ADDRESS){
            btn_delivery_here.setVisibility(View.VISIBLE);
        }
        else {
            btn_delivery_here.setVisibility(View.GONE);
        }
        addressAdapter = new AddressAdapter(DbQueries.addressModelList, mode_intent);
        recyclerViewAddress.setAdapter(addressAdapter);
        ((SimpleItemAnimator)recyclerViewAddress.getItemAnimator()).setSupportsChangeAnimations(false);
        addressAdapter.notifyDataSetChanged();

        add_new_address_myaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddNew = new Intent(MyAddressActivity.this, AddAddressActivity.class);
                intentAddNew.putExtra("INTENT", "null");
                startActivity(intentAddNew);
            }
        });
        btn_delivery_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DbQueries.addressselected != previousAddress){
                    final int previousIndexAddress = previousAddress;

                    loadingDialog.show();
                    Map<String, Object> selectUpdate = new HashMap<>();
                    selectUpdate.put("selected_" + String.valueOf(previousAddress +1), false);
                    selectUpdate.put("selected_" + String.valueOf(DbQueries.addressselected +1), true);

                    previousAddress = DbQueries.addressselected;
                    FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_ADDRESS")
                            .update(selectUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                finish();
                            }
                            else {
                                previousAddress = previousIndexAddress;
                                Toast.makeText(MyAddressActivity.this, "Loi updata", Toast.LENGTH_SHORT).show();
                            }
                            loadingDialog.dismiss();
                        }
                    });
                }
                else {
                    finish();
                }
            }
        });

    }
    public static void refeshItem(int deselect, int select){
        addressAdapter.notifyItemChanged(deselect);
        addressAdapter.notifyItemChanged(select);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            if(DbQueries.addressselected != previousAddress){
                DbQueries.addressModelList.get(DbQueries.addressselected).setSelected(false);
                DbQueries.addressModelList.get(previousAddress).setSelected(true);
                DbQueries.addressselected = previousAddress;
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(DbQueries.addressselected != previousAddress){
            DbQueries.addressModelList.get(DbQueries.addressselected).setSelected(false);
            DbQueries.addressModelList.get(previousAddress).setSelected(true);
            DbQueries.addressselected = previousAddress;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        num_address.setText(String.valueOf(DbQueries.addressModelList.size()) + " địa chỉ đã được lưu");
    }
}
