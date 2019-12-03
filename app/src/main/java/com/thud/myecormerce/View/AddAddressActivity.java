package com.thud.myecormerce.View;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thud.myecormerce.Models.AddressModel;
import com.thud.myecormerce.Presenter.DbQueries;
import com.thud.myecormerce.R;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private Button btn_save;
    private EditText edt_province;
    private EditText edt_country;
    private EditText edt_locationDetail;
    private EditText edt_fullname;
    private AppCompatSpinner spnGioiTinh;
    private EditText edt_email;
    private EditText edt_phone;

    private String [] list_gender;
    private String genderSelected;
    private Dialog loadingDialog;
    private boolean  updateAddress = false;
    private AddressModel addressModel;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        toolbar = findViewById(R.id.toolbar);

        list_gender = getResources().getStringArray(R.array.gioitinh);

        edt_province= findViewById(R.id.edt_province_newaddress);
        edt_country= findViewById(R.id.edt_country_newaddress);
        edt_locationDetail= findViewById(R.id.edt_location_newaddress);
        edt_fullname= findViewById(R.id.edt_full_name_newaddress);
        spnGioiTinh = findViewById(R.id.spnGioiTinh);
        edt_email= findViewById(R.id.edt_email_address_newaddress);
        edt_phone= findViewById(R.id.edt_phonenumber_newaddress);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Add New Address");

        loadingDialog = new Dialog(AddAddressActivity.this);
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_main));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btn_save = findViewById(R.id.btn_save_newaddress);
        if(getIntent().getStringExtra("INTENT").equals("update_address")){
            updateAddress = true;
            position = getIntent().getIntExtra("position", -1);
            addressModel =DbQueries.addressModelList.get(position);

            edt_province.setText(addressModel.getProvince());
            edt_country.setText(addressModel.getCountry());
            for (int x = 0; x < list_gender.length; x++){
                if(list_gender[x].equals(addressModel.getGioiTinh())) {
                    spnGioiTinh.setSelection(x);
                }
            }
            edt_locationDetail.setText(addressModel.getLocationDetail());
            edt_fullname.setText(addressModel.getFullname());
            edt_phone.setText(addressModel.getPhone());
            edt_email.setText(addressModel.getEmail());
            btn_save.setText("Cập nhật");
        }else {
            position = DbQueries.addressModelList.size();
        }



        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(edt_province.getText())){
                    if(!TextUtils.isEmpty(edt_country.getText())){
                        if(!TextUtils.isEmpty(edt_locationDetail.getText())){
                            if(!TextUtils.isEmpty(edt_email.getText())){
                                if(!TextUtils.isEmpty(edt_phone.getText()) && edt_phone.getText().length() >= 10){
                                    if(!TextUtils.isEmpty(edt_fullname.getText())){

                                        loadingDialog.show();
                                        final String fullAddress = edt_locationDetail.getText().toString() + ", " +  edt_province.getText().toString()+ ", " + edt_country.getText().toString() + ".";

                                        Map<String, Object> addressMap = new HashMap<>();

                                        addressMap.put("province_" + String.valueOf(position +1), edt_province.getText().toString());
                                        addressMap.put("country_" + String.valueOf(position +1), edt_country.getText().toString());
                                        addressMap.put("gender_" + String.valueOf(position +1), genderSelected);
                                        addressMap.put("locationDetail_" + String.valueOf(position +1), edt_locationDetail.getText().toString());
                                        addressMap.put("full_name_"+ String.valueOf(position+1), edt_fullname.getText().toString());
                                        addressMap.put("email_"+ String.valueOf(position+1), edt_email.getText().toString());
                                        addressMap.put("address_"+ String.valueOf(position+1),fullAddress);
                                        addressMap.put("phone_number_"+ String.valueOf(position +1), edt_phone.getText().toString());
                                        //selected = true
                                        if(!updateAddress) {
                                            addressMap.put("list_size", (long) DbQueries.addressModelList.size() + 1);
                                            if(getIntent().getStringExtra("INTENT").equals("manage")){
                                                if(DbQueries.addressModelList.size() == 0 ){
                                                    addressMap.put("selected_"+ String.valueOf(position +1), true);
                                                }else {
                                                    addressMap.put("selected_"+ String.valueOf(position +1), false);
                                                }
                                            }else {
                                                addressMap.put("selected_"+ String.valueOf(position +1), true);
                                            }

                                            if(DbQueries.addressModelList.size() > 0) {
                                                addressMap.put("selected_" + (DbQueries.addressselected + 1), false);
                                            }
                                        }
                                        FirebaseFirestore.getInstance().collection("USERS")
                                                .document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                                                .document("MY_ADDRESS")
                                                .update(addressMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    if(!updateAddress) {
                                                        if (DbQueries.addressModelList.size() > 0) {
                                                            DbQueries.addressModelList.get(DbQueries.addressselected).setSelected(false);
                                                        }
                                                        DbQueries.addressModelList.add(new AddressModel(edt_province.getText().toString(), edt_country.getText().toString(), edt_locationDetail.getText().toString(), edt_fullname.getText().toString(), genderSelected, edt_email.getText().toString(), edt_phone.getText().toString(), true));
                                                        if(getIntent().getStringExtra("INTENT").equals("manage")){
                                                            if(DbQueries.addressModelList.size() == 0 ){
                                                                DbQueries.addressselected = DbQueries.addressModelList.size() - 1;
                                                            }
                                                        }else {
                                                            DbQueries.addressselected = DbQueries.addressModelList.size() - 1;
                                                        }
                                                    }else {
                                                        DbQueries.addressModelList.set(position, new AddressModel(edt_province.getText().toString(), edt_country.getText().toString(), edt_locationDetail.getText().toString(), edt_fullname.getText().toString(), genderSelected, edt_email.getText().toString(), edt_phone.getText().toString(), true));
                                                    }
                                                    if(getIntent().getStringExtra("INTENT").equals("deliveryIntent")) {
                                                        Intent deliveryIntent = new Intent(AddAddressActivity.this, DeliveryActivity.class);
                                                        startActivity(deliveryIntent);
                                                    }
                                                    else {
                                                        MyAddressActivity.refeshItem(DbQueries.addressselected, DbQueries.addressModelList.size() -1);
                                                    }
                                                    DbQueries.addressselected = DbQueries.addressModelList.size() -1;
                                                    finish();
                                                }
                                                else {
                                                    Toast.makeText(AddAddressActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                                                }
                                                loadingDialog.dismiss();
                                            }
                                        });
                                    }else {
                                        edt_fullname.requestFocus();
                                        Toast.makeText(AddAddressActivity.this, "Bạn hãy nhập họ tên để giao dịch!", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    edt_phone.requestFocus();
                                    Toast.makeText(AddAddressActivity.this, "Số điện thoại phải chính xác!", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                               edt_email.requestFocus();
                            }
                        }else {
                            edt_locationDetail.requestFocus();
                        }
                    }else {
                        edt_country.requestFocus();
                    }
                }else {
                    edt_province.requestFocus();
                }
            }
        });
        ArrayAdapter spnGioiTinhAdapter= new ArrayAdapter(this, android.R.layout.simple_spinner_item, list_gender);
        spnGioiTinhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnGioiTinh.setAdapter(spnGioiTinhAdapter);
        spnGioiTinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderSelected = list_gender[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
