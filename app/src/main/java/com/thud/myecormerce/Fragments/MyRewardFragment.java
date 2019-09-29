package com.thud.myecormerce.Fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.thud.myecormerce.Adapter.RewardAdapter;
import com.thud.myecormerce.Models.RewardModel;
import com.thud.myecormerce.Presenter.DbQueries;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyRewardFragment extends Fragment {


    public MyRewardFragment() {
        // Required empty public constructor
    }
    private Dialog loadingDialog;
    private RecyclerView recyclerViewReward;
    public static RewardAdapter rewardAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_reward, container, false);
        recyclerViewReward = view.findViewById(R.id.recyclerview_my_reward);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewReward.setLayoutManager(linearLayoutManager);

        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_main));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();


        rewardAdapter = new RewardAdapter(DbQueries.rewardModelList, false);
        recyclerViewReward.setAdapter(rewardAdapter);

        if(DbQueries.rewardModelList.size() ==0 ){
            DbQueries.loadReward(getContext(), loadingDialog, true);
        }else {
            loadingDialog.dismiss();
        }


        rewardAdapter.notifyDataSetChanged();

        return  view;
    }

}
