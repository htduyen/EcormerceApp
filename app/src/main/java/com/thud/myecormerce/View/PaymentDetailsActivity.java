package com.thud.myecormerce.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FieldValue;
import com.thud.myecormerce.Presenter.Constants;
import com.thud.myecormerce.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.UUID;

public class PaymentDetailsActivity extends AppCompatActivity {

    private EditText txtID;
    private  EditText txtAmount;
    private EditText txtStatus;
    int totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        txtID = findViewById(R.id.txtID);
        txtAmount= findViewById(R.id.txtAmount);
        txtStatus = findViewById(R.id.txt_status);

        Intent intent = getIntent();
        totalAmount = intent.getIntExtra("PaymentAmount", 0);

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetail"));
            showDetail(jsonObject.getJSONObject("response"), totalAmount);
            //Toast.makeText(this, "Total amount recieve: " + totalAmount, Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showDetail(JSONObject response, int paymentAmount) {
        try {
            txtID.setText("ID: " + response.getString("id"));
            txtAmount.setText("Total Amount: " + totalAmount*22000 + "đ");
            txtStatus.setText("Time: " +  Calendar.getInstance().getTime());

        }
        catch (Exception ex){
            Toast.makeText(this, "Lỗi phản hồi: " + ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Id: "+ txtID.getText().toString() + "\n Toatal amount: " + txtAmount.getText().toString() + "\n Status: " + txtStatus.getText().toString() , Toast.LENGTH_SHORT).show();
        }
    }
}
