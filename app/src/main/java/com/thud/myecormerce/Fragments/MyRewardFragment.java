package com.thud.myecormerce.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thud.myecormerce.Adapter.RewardAdapter;
import com.thud.myecormerce.Models.RewardModel;
import com.thud.myecormerce.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyRewardFragment extends Fragment {


    public MyRewardFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerViewReward;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_reward, container, false);
        recyclerViewReward = view.findViewById(R.id.recyclerview_my_reward);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewReward.setLayoutManager(linearLayoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("Lễ 30/4 - 1/5", "Từ 29-2/5/2019","Giảm 20% tất cả các sản phẩm."));
        rewardModelList.add(new RewardModel("Lễ Tình Nhân", "Từ 12-14/2/2019","Giảm 30% tất cả các sản phẩm cho các cặp tình nhân."));
        rewardModelList.add(new RewardModel("Lễ Quốc Tế Thiếu Nhi", "Từ 30-1/6/2019","Giảm 10% tất cả các sản phẩm."));
        rewardModelList.add(new RewardModel("Ngày Thương Binh Liệt Sĩ", "Từ 26-28/7/2019","Giảm 50% tất cả các sản phẩm đối với người có công với đất nước, CBNV nhà nước."));
        rewardModelList.add(new RewardModel("Lễ Quốc Khánh", "Từ 1-3/9/2019","Giảm 30% tất cả các sản phẩm."));
        rewardModelList.add(new RewardModel("Ngày Nhà Giáo Việt Nam", "Từ 19-21/11/2019","Giảm 50% tất cả các sản phẩm cho CBNV giáo viên trên cả nước."));

        RewardAdapter rewardAdapter = new RewardAdapter(rewardModelList, false);
        recyclerViewReward.setAdapter(rewardAdapter);
        rewardAdapter.notifyDataSetChanged();

        return  view;
    }

}
