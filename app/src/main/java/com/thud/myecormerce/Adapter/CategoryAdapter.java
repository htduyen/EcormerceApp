package com.thud.myecormerce.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thud.myecormerce.Models.CategoryModel;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.CategoryActivity;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryModel> categoryModelList;

    public CategoryAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cate_item_main, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder viewHolder, int i) {
        String icon = categoryModelList.get(i).getCateIconLink();
        String name = categoryModelList.get(i).getCateName();
        viewHolder.setCategory(name, i);

    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView cate_icon;
        private TextView cate_name;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            cate_icon = itemView.findViewById(R.id.imv_cate);
            cate_name = itemView.findViewById(R.id.txt_name_cate);
        }
        private void  setCate_icon(){
            //set ảnh cho catagory item


        }
        private void setCategory(final String name, final int position){
            //set name cho category item
            cate_name.setText(name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position != 0){
                        Intent cate_intent = new Intent(itemView.getContext(),CategoryActivity.class);
                        cate_intent.putExtra("name_cate", name);
                        itemView.getContext().startActivity(cate_intent);
                    }
                }
            });
        }
    }
}
