package com.thud.myecormerce.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thud.myecormerce.Models.RewardModel;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.ProductDetailActivity;

import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.ViewHolder> {

    private List<RewardModel> rewardModelList;
    private Boolean miniLayout = false;

    public RewardAdapter(List<RewardModel> rewardModelList, Boolean miniLayout) {
        this.rewardModelList = rewardModelList;
        this.miniLayout = miniLayout;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        if(miniLayout){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reward_item_mini_recycler_layout, viewGroup,false);
        }
        else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reward_item_layout, viewGroup,false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String title = rewardModelList.get(i).getReward_title();
        String time = rewardModelList.get(i).getReward_time();
        String content = rewardModelList.get(i).getReward_content();

        viewHolder.setData(title,time,content);
    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView reward_title;
        private TextView reward_time;
        private TextView reward_content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reward_title = itemView.findViewById(R.id.txt_name_reward_reward);
            reward_time = itemView.findViewById(R.id.txt_time_reward);
            reward_content = itemView.findViewById(R.id.txt_content_reward);
        }
        private void setData(final String title, final String time, final String content){
            reward_title.setText(title);
            reward_time.setText(time);
            reward_content.setText(content);

            if(miniLayout){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProductDetailActivity.discountTitle.setText(title);
                        ProductDetailActivity.discountTime.setText(time);
                        ProductDetailActivity.discountContent.setText(content);

                        ProductDetailActivity.showDialogDisCount();
                    }
                });
            }
        }

    }
}
