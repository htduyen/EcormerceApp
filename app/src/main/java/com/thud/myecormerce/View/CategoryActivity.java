package com.thud.myecormerce.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.thud.myecormerce.Adapter.CategoryAdapter;
import com.thud.myecormerce.Adapter.HomePageAdapter;
import com.thud.myecormerce.Models.CategoryModel;
import com.thud.myecormerce.Models.HomePageModel;
import com.thud.myecormerce.Models.ProductHorizonModel;
import com.thud.myecormerce.Models.SliderModel;
import com.thud.myecormerce.Models.WishlistModel;
import com.thud.myecormerce.R;

import java.util.ArrayList;
import java.util.List;

import static com.thud.myecormerce.Presenter.DbQueries.listNameCategories;
import static com.thud.myecormerce.Presenter.DbQueries.lists;
import static com.thud.myecormerce.Presenter.DbQueries.setLayout;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView_cate;
    private List<HomePageModel> homePageModePlacelList = new ArrayList<>();
    private HomePageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("name_cate"); //tu cate_adapter
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // HomePage frefresh
        List<SliderModel> sliderModelListPlace = new ArrayList<>();
        sliderModelListPlace.add(new SliderModel("", "#ffffff"));
        sliderModelListPlace.add(new SliderModel("", "#ffffff"));
        sliderModelListPlace.add(new SliderModel("", "#ffffff"));
        sliderModelListPlace.add(new SliderModel("", "#ffffff"));
        sliderModelListPlace.add(new SliderModel("", "#ffffff"));
        sliderModelListPlace.add(new SliderModel("", "#ffffff"));
        sliderModelListPlace.add(new SliderModel("", "#ffffff"));
        sliderModelListPlace.add(new SliderModel("", "#ffffff"));

        List<ProductHorizonModel> productHorizonModelListPlace = new ArrayList<>();
        productHorizonModelListPlace.add(new ProductHorizonModel("","","","",""));
        productHorizonModelListPlace.add(new ProductHorizonModel("","","","",""));
        productHorizonModelListPlace.add(new ProductHorizonModel("","","","",""));
        productHorizonModelListPlace.add(new ProductHorizonModel("","","","",""));
        productHorizonModelListPlace.add(new ProductHorizonModel("","","","",""));
        productHorizonModelListPlace.add(new ProductHorizonModel("","","","",""));
        productHorizonModelListPlace.add(new ProductHorizonModel("","","","",""));

        homePageModePlacelList.add(new HomePageModel(0, sliderModelListPlace));
        homePageModePlacelList.add(new HomePageModel(1,"" ,"#ffffff"));
        homePageModePlacelList.add(new HomePageModel(2,"", "#ffffff",productHorizonModelListPlace, new ArrayList<WishlistModel>()));
        homePageModePlacelList.add(new HomePageModel(3, "", "#ffffff",productHorizonModelListPlace));
        // HomePage frefresh

        //***************** Product Product ***********************
        //*****************RcuclerView Testing ********************
        recyclerView_cate = findViewById(R.id.recyclerview_cate_category);

        LinearLayoutManager testingLinearlayout = new LinearLayoutManager(this);
        testingLinearlayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_cate.setLayoutManager(testingLinearlayout);

        adapter = new HomePageAdapter(homePageModePlacelList);


        //       List<HomePageModel> homePageModelList = new ArrayList<>();
//        homePageModelList.add(new HomePageModel(1, R.drawable.stript1,"#ffff00"));
//        homePageModelList.add(new HomePageModel(0, sliderModelList));
//        homePageModelList.add(new HomePageModel(2,"Sales Product", horizonModels));
//        homePageModelList.add(new HomePageModel(3,"Phone Product", horizonModels));
//        homePageModelList.add(new HomePageModel(3,"Shoes Product", horizonModels));
//        homePageModelList.add(new HomePageModel(3,"Laptop Product", horizonModels));
        int listposition = 0;
        for (int x = 0; x < listNameCategories.size(); x++){
            if(listNameCategories.get(x).equals(title.toUpperCase())){
                listposition = x;
            }
        }
        if (listposition == 0 ){
            listNameCategories.add(title.toUpperCase());
            lists.add(new ArrayList<HomePageModel>());
            setLayout(recyclerView_cate,this, listNameCategories.size() -1, title);
        }
        else {
            adapter = new HomePageAdapter(lists.get(listposition));
        }
        recyclerView_cate.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intentSearch =new Intent(CategoryActivity.this, SearchActivity.class);
        startActivity(intentSearch);
        //noinspection SimplifiableIfStatement
        if (id == R.id.icon_search) {

            return true;
        }
        else if(id == android.R.id.home)
        {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
