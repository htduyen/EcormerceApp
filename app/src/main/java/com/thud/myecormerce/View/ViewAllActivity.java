package com.thud.myecormerce.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.thud.myecormerce.Adapter.GridProductAdapter;
import com.thud.myecormerce.Adapter.WishlistAdapter;
import com.thud.myecormerce.Models.ProductHorizonModel;
import com.thud.myecormerce.Models.WishlistModel;
import com.thud.myecormerce.R;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerViewViewAll;
    private GridView gridViewViewAll;
    public static List<ProductHorizonModel> productHorizonModelList;
    public static List<WishlistModel> wishlistModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("group_product_title"));

        recyclerViewViewAll = findViewById(R.id.recyclerView_ViewAll);
        gridViewViewAll = findViewById(R.id.gridView_viewall);
        int view_all_code = getIntent().getIntExtra("view_all_code", -1);

        if(view_all_code == 0 ){
            //RecyclerView
            recyclerViewViewAll.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewViewAll.setLayoutManager(linearLayoutManager);

            WishlistAdapter viewall_Adapter = new WishlistAdapter(wishlistModelList, false);
            recyclerViewViewAll.setAdapter(viewall_Adapter);
            viewall_Adapter.notifyDataSetChanged();
            //End Recyclerview
        }
        else if(view_all_code ==1) {
            //  Gidview product
            gridViewViewAll.setVisibility(View.VISIBLE);


//            productHorizonModelList.add(new ProductHorizonModel(R.drawable.phone1, "Phone 1", "Descr 1", "2 000 000"));
//            productHorizonModelList.add(new ProductHorizonModel(R.drawable.phone2, "Phone 2", "Descr 1", "2 000 000"));
//            productHorizonModelList.add(new ProductHorizonModel(R.drawable.phone3, "Phone 3", "Descr 1", "2 000 000"));
//            productHorizonModelList.add(new ProductHorizonModel(R.drawable.phone4, "Phone 4", "Descr 1", "2 000 000"));
//            productHorizonModelList.add(new ProductHorizonModel(R.drawable.phone5, "Phone 5", "Descr 1", "2 000 000"));
//            productHorizonModelList.add(new ProductHorizonModel(R.drawable.phone6, "Phone 6", "Descr 1", "2 000 000"));
//            productHorizonModelList.add(new ProductHorizonModel(R.drawable.phone7, "Phone 7", "Descr 1", "2 000 000"));
//            productHorizonModelList.add(new ProductHorizonModel(R.drawable.phone1, "Phone 8", "Descr 1", "2 000 000"));


            GridProductAdapter adapter = new GridProductAdapter(productHorizonModelList);
            gridViewViewAll.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            //End  Gidview  product
        }




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
