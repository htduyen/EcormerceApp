package com.thud.myecormerce.View;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thud.myecormerce.Adapter.NotificationAdapter;
import com.thud.myecormerce.Models.NotificationModel;
import com.thud.myecormerce.Presenter.DbQueries;
import com.thud.myecormerce.R;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {


    private RecyclerView recyclerView_notification;
    public static NotificationAdapter adapter;
    private boolean runQuery = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView_notification = findViewById(R.id.recyclerview_notification);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Thông báo");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_notification.setLayoutManager(layoutManager);


        adapter = new NotificationAdapter(DbQueries.notificationModelList);
        recyclerView_notification.setAdapter(adapter);

        Map<String, Object> updateReaded = new HashMap<>();

        for(int x =0; x< DbQueries.notificationModelList.size(); x++){
            if(!DbQueries.notificationModelList.get(x).isReaded()){
                runQuery = true;
            }
            updateReaded.put("Readed_" + x, true);
        }
        if(runQuery) {
            FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_NOTIFICATIONS").update(updateReaded);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for(int x =0; x< DbQueries.notificationModelList.size(); x++){
            DbQueries.notificationModelList.get(x).setReaded(true);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




}
