package com.thud.myecormerce.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thud.myecormerce.Models.CategoryModel;
import com.thud.myecormerce.R;

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
        viewHolder.setCate_name(name);

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
        private void setCate_name(String name){
            //set name cho category item
            cate_name.setText(name);
        }
    }
}
