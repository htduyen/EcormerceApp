package com.thud.myecormerce.Adapter;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.thud.myecormerce.Models.MyOrderItemModel;
import com.thud.myecormerce.Presenter.DbQueries;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.OrderDateilsActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    private  List<MyOrderItemModel> myOrderItemModelList;
    private Dialog loadingDialog;

    public MyOrderAdapter(List<MyOrderItemModel> myOrderItemModelList, Dialog loadingDialog) {
        this.myOrderItemModelList = myOrderItemModelList;
        this.loadingDialog = loadingDialog;
    }


    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_order_item_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder viewHolder, int i) {
        String resource = myOrderItemModelList.get(i).getProductImage();
        String product_id = myOrderItemModelList.get(i).getProduct_id();
        int rating = myOrderItemModelList.get(i).getNumRating();
        String name = myOrderItemModelList.get(i).getProductName();
        String orderStatus = myOrderItemModelList.get(i).getOrderStatus();
        Date date;
        switch (orderStatus){
            case "Ordered":
                date = myOrderItemModelList.get(i).getOrderdDate();
                break;
            case "Packed":
                date = myOrderItemModelList.get(i).getPackedDate();
                break;
            case "Shipped":
                date = myOrderItemModelList.get(i).getShipedDate();
                break;
            case "Delivered":
                date = myOrderItemModelList.get(i).getDeliveriedDate();
                break;
            case "Cancelled":
                date = myOrderItemModelList.get(i).getCancelDate();
                break;
            default:
                date = myOrderItemModelList.get(i).getCancelDate();
        }
        viewHolder.setData(resource, name,orderStatus, date, rating, product_id, i);
    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private ImageView dilivertyIndicator;
        private TextView productName;
        private  TextView diliveryStatus;
        private LinearLayout rating;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imv_product_myorder);
            productName = itemView.findViewById(R.id.txt_product_name_myorder);
            dilivertyIndicator = itemView.findViewById(R.id.imv_order_status);
            diliveryStatus= itemView.findViewById(R.id.txt_time_dilivered);
            rating = itemView.findViewById(R.id.linear_rating_now);


        }

        private void setData(String resource, String name, String orderStatus, Date date, final int numRating, final String product_id, final int position)
        {
            Glide.with(itemView.getContext()).load(resource).into(productImage);
            productName.setText(name);
            if(orderStatus.equals("Cancelled")){
                //Lưu ý getColor
                ColorStateList xam = AppCompatResources.getColorStateList(itemView.getContext(), R.color.colorRed);
                ImageViewCompat.setImageTintList(dilivertyIndicator, xam);
                //dilivertyIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getColor(R.color.colorGray)));
            }
            else {
                //dilivertyIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getColor(R.color.colorGreenDark)));
                ColorStateList xanh = AppCompatResources.getColorStateList(itemView.getContext(), R.color.colorGreenDark);
                ImageViewCompat.setImageTintList(dilivertyIndicator, xanh);

            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd/MM/YYYY hh:mm:aa");
            diliveryStatus.setText(orderStatus + " - " +  String.valueOf(simpleDateFormat.format(date)));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentMyOrderDetail = new Intent(itemView.getContext(), OrderDateilsActivity.class);
                    intentMyOrderDetail.putExtra("Position", position);
                    itemView.getContext().startActivity(intentMyOrderDetail);
                }
            });

            //Rating
            setRating(numRating);
            for (int x=0; x < rating.getChildCount(); x++){
                final  int  startPosition = x;
                rating.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadingDialog.show();
                        setRating(startPosition);
                        final DocumentReference documentReference = FirebaseFirestore.getInstance().collection("PRODUCTS").document(product_id);
                        FirebaseFirestore.getInstance().runTransaction(new Transaction.Function<Object>() {
                            @Nullable
                            @Override
                            public Object apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                                DocumentSnapshot documentSnapshot = transaction.get(documentReference);
                                if(numRating != 0){
                                    Long increase = documentSnapshot.getLong("star_" + startPosition + 1) + 1;
                                    Long decrease = documentSnapshot.getLong("star_" + numRating +1) - 1;
                                    transaction.update(documentReference, "star_" + startPosition + 1, increase);
                                    transaction.update(documentReference, "star_" + numRating +1, decrease);
                                }else {
                                    Long increase = documentSnapshot.getLong("star_" + startPosition +1) + 1;
                                    transaction.update(documentReference, "star_" + startPosition +1, increase);
                                }
                                return null;
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Object>() {

                            @Override
                            public void onSuccess(Object o) {

                                Map<String, Object> myratting = new HashMap<>();
                                if (DbQueries.myratting_ids.contains(product_id)) {
                                    myratting.put("ratting_" + DbQueries.myratting_ids.indexOf(product_id), (long) startPosition + 1);
                                } else {
                                    myratting.put("list_size", (long) DbQueries.myratting_ids.size() + 1);
                                    myratting.put("product_id_" + DbQueries.myratting_ids.size(), product_id);
                                    myratting.put("ratting_" + DbQueries.myratting_ids.size(), (long) startPosition + 1);
                                }

                                FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_RATTING")
                                        .update(myratting).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            DbQueries.myOrderItemModelList.get(position).setNumRating(startPosition);;
                                            if(DbQueries.myratting_ids.contains(product_id)){
                                                DbQueries.myRatting.set(DbQueries.myratting_ids.indexOf(product_id), Long.parseLong(String.valueOf(startPosition +1)));
                                            }else {
                                                DbQueries.myratting_ids.add(product_id);
                                                DbQueries.myRatting.add(Long.parseLong(String.valueOf(startPosition +1)));
                                            }

                                        }else {
                                            Toast.makeText(itemView.getContext(), "Lỗi ", Toast.LENGTH_SHORT).show();
                                        }
                                        loadingDialog.dismiss();
                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loadingDialog.dismiss();
                            }
                        });
                    }
                });

            }
            //Rating
        }
        private void setRating(int startPosition) {
            for(int x = 0; x < rating.getChildCount(); x++){
                ImageView starbtn = (ImageView) rating.getChildAt(x);
                starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
                if(x  <= startPosition){
                    starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
                }
            }
        }
    }


}
