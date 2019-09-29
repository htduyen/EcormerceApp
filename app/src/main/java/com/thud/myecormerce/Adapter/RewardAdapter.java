package com.thud.myecormerce.Adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.thud.myecormerce.Models.CartItemModel;
import com.thud.myecormerce.Models.RewardModel;
import com.thud.myecormerce.Presenter.DbQueries;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.ProductDetailActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.ViewHolder> {

    private List<RewardModel> rewardModelList;
    private Boolean miniLayout = false;
    private RecyclerView recyclerViewDiscount;
    private LinearLayout selectedDiscount;
    private String productOriginPrice;
    private TextView selectedDiscountTitle;
    private TextView selectedDisCountExpiryDate;
    private TextView selectedDisCountBody;
    private TextView disCountedPrice;
    private int cartItemPosition = -1;
    private List<CartItemModel> cartItemModelList;

    public RewardAdapter(List<RewardModel> rewardModelList, Boolean miniLayout) {
        this.rewardModelList = rewardModelList;
        this.miniLayout = miniLayout;
    }

    public RewardAdapter(List<RewardModel> rewardModelList, Boolean miniLayout, RecyclerView recyclerViewDiscount, LinearLayout selectedDiscount, String productOriginPrice, TextView discountTitle, TextView disCountExpiryDate, TextView disCountBody, TextView disCountedPrice) {
        this.rewardModelList = rewardModelList;
        this.miniLayout = miniLayout;
        this.recyclerViewDiscount = recyclerViewDiscount;
        this.selectedDiscount = selectedDiscount;
        this.productOriginPrice = productOriginPrice;
        this.selectedDiscountTitle = discountTitle;
        this.selectedDisCountExpiryDate = disCountExpiryDate;
        this.selectedDisCountBody = disCountBody;
        this.disCountedPrice = disCountedPrice;
    }
    public RewardAdapter(int cartItemPosition, List<RewardModel> rewardModelList, Boolean miniLayout, RecyclerView recyclerViewDiscount, LinearLayout selectedDiscount, String productOriginPrice, TextView discountTitle, TextView disCountExpiryDate, TextView disCountBody, TextView disCountedPrice, List<CartItemModel> cartItemModelList) {
        this.cartItemPosition = cartItemPosition;
        this.rewardModelList = rewardModelList;
        this.miniLayout = miniLayout;
        this.recyclerViewDiscount = recyclerViewDiscount;
        this.selectedDiscount = selectedDiscount;
        this.productOriginPrice = productOriginPrice;
        this.selectedDiscountTitle = discountTitle;
        this.selectedDisCountExpiryDate = disCountExpiryDate;
        this.selectedDisCountBody = disCountBody;
        this.disCountedPrice = disCountedPrice;
        this.cartItemModelList = cartItemModelList;
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
        String discountID = rewardModelList.get(i).getDiscountID();
        String title = rewardModelList.get(i).getType();
        Date validity = rewardModelList.get(i).getTimestamp();
        String body = rewardModelList.get(i).getDiscountbBoby();
        String lower_limit = rewardModelList.get(i).getLowerLlimit();
        String upper_limit = rewardModelList.get(i).getUpperLimit();
        String disount = rewardModelList.get(i).getDiscount();
        Boolean alreadlyUesd = rewardModelList.get(i).getAlreadlyUsed();
        viewHolder.setData(discountID,title,validity,body, lower_limit, upper_limit, disount, alreadlyUesd);
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
        private void setData(final String discountID, final String title, final Date validity, final String body, final String lower_limit, final String upper_limit, final String discount, final boolean alreadlyUsed){

            if(title.equals("Discount")){
                reward_title.setText("Khuyến mãi giảm " + discount+ " %");
            }else {
                reward_title.setText(title);
            }
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            if(alreadlyUsed){
                reward_time.setText("Đã sử dụng");
                reward_time.setTextColor(itemView.getContext().getResources().getColor(R.color.black));
                reward_title.setTextColor(Color.parseColor("#50ffffff"));
                reward_content.setTextColor(Color.parseColor("#50ffffff"));
            }else {
                reward_time.setText(simpleDateFormat.format(validity));
                reward_title.setTextColor(Color.parseColor("#ffffff"));
                reward_content.setTextColor(Color.parseColor("#ffffff"));
            }
            reward_content.setText(body);

            if(miniLayout){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!alreadlyUsed) {
                            selectedDiscountTitle.setText(title);
                            selectedDisCountExpiryDate.setText(simpleDateFormat.format(validity));
                            selectedDisCountBody.setText(body);

                            if (Long.valueOf(productOriginPrice) > Long.valueOf(lower_limit) && Long.valueOf(productOriginPrice) < Long.valueOf(upper_limit)) {
                                if (title.equals("Discount")) {
                                    Long discountAmount = Long.valueOf(productOriginPrice) * Long.valueOf(discount) / 100;
                                    disCountedPrice.setText(String.valueOf(Long.valueOf(productOriginPrice) - Long.valueOf(discountAmount)));
                                } else {
                                    disCountedPrice.setText(String.valueOf(Long.valueOf(productOriginPrice) - Long.valueOf(discount)));
                                }
                                if(cartItemPosition != -1) {
                                    cartItemModelList.get(cartItemPosition).setSelectedDiscountID(discountID);
                                }
                            } else {
                                if(cartItemPosition != -1) {
                                    cartItemModelList.get(cartItemPosition).setSelectedDiscountID(null);
                                }
                                disCountedPrice.setText("Không giảm giá");
                                Toast.makeText(itemView.getContext(), "Sản phẩm không được giảm giá!", Toast.LENGTH_SHORT).show();
                            }

                            if (recyclerViewDiscount.getVisibility() == View.GONE) {
                                recyclerViewDiscount.setVisibility(View.VISIBLE);
                                selectedDiscount.setVisibility(View.GONE);
                            } else {
                                recyclerViewDiscount.setVisibility(View.GONE);
                                selectedDiscount.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
            }
        }

    }
}
