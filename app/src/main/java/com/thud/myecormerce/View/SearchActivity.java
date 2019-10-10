package com.thud.myecormerce.View;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.thud.myecormerce.Adapter.WishlistAdapter;
import com.thud.myecormerce.Models.WishlistModel;
import com.thud.myecormerce.R;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private TextView txt_not_found_product;
    private RecyclerView recyclerView_serach_product;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.searchview_product);
        txt_not_found_product = findViewById(R.id.txt_not_found_product);
        recyclerView_serach_product = findViewById(R.id.recyclerview_search_product);

        recyclerView_serach_product.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView_serach_product.setLayoutManager(linearLayoutManager);

        final List<WishlistModel> list = new ArrayList<>();
        final List<String> ids = new ArrayList<>();

        final Adapter adapter = new Adapter(list, false);
        adapter.setFromSearch(true);
        recyclerView_serach_product.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                list.clear();
                ids.clear();

                final String[] tags = s.toLowerCase().split(" ");
                for (String tag: tags){
                    tag.trim();
                    FirebaseFirestore.getInstance().collection("PRODUCTS").whereArrayContains("tags", tag)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                         for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                                            WishlistModel model =   new WishlistModel(
                                                    documentSnapshot.getId(),
                                                    documentSnapshot.get("product_image_1").toString(),
                                                    documentSnapshot.get("product_fullname").toString(),
                                                    documentSnapshot.get("product_price").toString(),
                                                    documentSnapshot.get("product_cutted_price").toString(),
                                                    (long) documentSnapshot.get("free_discount"),
                                                    (boolean) documentSnapshot.get("cod"),
                                                    documentSnapshot.get("average_rating").toString(),
                                                    (long) documentSnapshot.get("total_rating"),
                                                    true);
                                            model.setTags((ArrayList<String>) documentSnapshot.get("tags"));
                                            if(!ids.contains(model.getProduct_id())){
                                                list.add(model);
                                                ids.add(model.getProduct_id());
                                            }

                                         }
                                         if(tags.equals(tags[tags.length -1])){
                                             if(list.size() == 0){
                                                 txt_not_found_product.setVisibility(View.VISIBLE);
                                                 recyclerView_serach_product.setVisibility(View.GONE);
                                             }else {
                                                 txt_not_found_product.setVisibility(View.GONE);
                                                 recyclerView_serach_product.setVisibility(View.VISIBLE);
                                                 adapter.getFilter().filter(s);
                                             }
                                         }
                                    }else {
                                        String error= task.getException().getMessage();
                                        Toast.makeText(SearchActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }
    class  Adapter extends WishlistAdapter implements Filterable{


        private List<WishlistModel> originalList;

        public Adapter(List<WishlistModel> wishlistModelList, Boolean wishlist) {
            super(wishlistModelList, wishlist);
            //filterList = new ArrayList<>();
            originalList = wishlistModelList;
        }

        @Override
        public Filter getFilter() {

            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                    FilterResults results = new FilterResults();
                    List<WishlistModel> filterList = new ArrayList<>();
                    final String[] tags = constraint.toString().toLowerCase().split(" ");

                    for(WishlistModel model: originalList){
                        ArrayList<String >presentTags = new ArrayList<>();
                        for(String tag: tags){
                            if(model.getTags().contains(tag)){
                                presentTags.add(tag);
                            }
                        }
                        model.setTags(presentTags);
                    }
                    for (int i = tags.length; i > 0; i--){
                        for(WishlistModel model: originalList){
                            if(model.getTags().size() == i){
                                filterList.add(model);
                            }
                        }
                    }
                    results.values = filterList;
                    results.count = filterList.size();

                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    if(results.count > 0){
                        setWishlistModelList((List<WishlistModel>) results.values);
                    }
                    notifyDataSetChanged();
                }
            };
        }
    }
}
