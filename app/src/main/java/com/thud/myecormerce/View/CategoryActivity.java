package com.thud.myecormerce.View;

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
import com.thud.myecormerce.R;

import java.util.ArrayList;
import java.util.List;

import static com.thud.myecormerce.Presenter.DbQueries.listNameCategories;
import static com.thud.myecormerce.Presenter.DbQueries.lists;
import static com.thud.myecormerce.Presenter.DbQueries.setLayout;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView_cate;
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


        //Banner Slider
//        List<SliderModel> sliderModelList = new ArrayList<SliderModel>();
//
//        sliderModelList.add(new SliderModel(R.drawable.banner5, "#BADBF7"));
//        sliderModelList.add(new SliderModel(R.drawable.banner6, "#BADBF7"));
//        sliderModelList.add(new SliderModel(R.drawable.banner7, "#BADBF7"));
//        sliderModelList.add(new SliderModel(R.drawable.banner8, "#BADBF7"));
//
//        sliderModelList.add(new SliderModel(R.drawable.banner1, "#BADBF7"));
//        sliderModelList.add(new SliderModel(R.drawable.banner2, "#BADBF7"));
//        sliderModelList.add(new SliderModel(R.drawable.banner3, "#BADBF7"));
//        sliderModelList.add(new SliderModel(R.drawable.banner4, "#BADBF7"));
//        sliderModelList.add(new SliderModel(R.drawable.banner5, "#BADBF7"));
//        sliderModelList.add(new SliderModel(R.drawable.banner6, "#BADBF7"));
//        sliderModelList.add(new SliderModel(R.drawable.banner7, "#BADBF7"));
//        sliderModelList.add(new SliderModel(R.drawable.banner8, "#BADBF7"));


        ////////////////////////End BANNER SLIDER

        //***************** Product Horizon ********************

//        List<ProductHorizonModel> horizonModels = new ArrayList<>();
//        horizonModels.add(new ProductHorizonModel(R.drawable.phone1, "Phone 1", "Descr 1", "2 000 000"));
//        horizonModels.add(new ProductHorizonModel(R.drawable.phone2, "Phone 2", "Descr 1", "2 000 000"));
//        horizonModels.add(new ProductHorizonModel(R.drawable.phone3, "Phone 3", "Descr 1", "2 000 000"));
//        horizonModels.add(new ProductHorizonModel(R.drawable.phone4, "Phone 4", "Descr 1", "2 000 000"));
//        horizonModels.add(new ProductHorizonModel(R.drawable.phone5, "Phone 5", "Descr 1", "2 000 000"));
//        horizonModels.add(new ProductHorizonModel(R.drawable.phone6, "Phone 6", "Descr 1", "2 000 000"));
//        horizonModels.add(new ProductHorizonModel(R.drawable.phone7, "Phone 7", "Descr 1", "2 000 000"));
//        horizonModels.add(new ProductHorizonModel(R.drawable.phone1, "Phone 8", "Descr 1", "2 000 000"));

        //*****************End Product Horizon ********************

        //***************** Product Product ***********************
        //*****************RcuclerView Testing ********************
        recyclerView_cate = findViewById(R.id.recyclerview_cate_category);

        LinearLayoutManager testingLinearlayout = new LinearLayoutManager(this);
        testingLinearlayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_cate.setLayoutManager(testingLinearlayout);

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
            adapter = new HomePageAdapter(lists.get(listNameCategories.size() -1));
            setLayout(adapter,this, listNameCategories.size() -1, title);
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
