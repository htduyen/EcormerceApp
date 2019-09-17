package com.thud.myecormerce.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.thud.myecormerce.Presenter.Constants;
import com.thud.myecormerce.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetailsActivity extends AppCompatActivity {

    private TextView txtID, txtAmount, txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        txtID = findViewById(R.id.txtID);
        txtAmount= findViewById(R.id.txtAmount);
        txtStatus = findViewById(R.id.txt_status);

        Intent intent = getIntent();
        String totalAmount = intent.getStringExtra("PaymentAmount");

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetail"));
            showDetail(jsonObject.getJSONObject("response"), totalAmount);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showDetail(JSONObject response, String paymentAmount) {
        try {
            txtID.setText("id");
            txtAmount.setText(response.getString(paymentAmount));
            txtStatus.setText(response.getString("state"));
        }
        catch (Exception ex){
            Toast.makeText(this, "Error: e" + ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
