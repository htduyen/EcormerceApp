package com.thud.myecormerce.Fragments;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Magnifier;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.thud.myecormerce.Presenter.DbQueries;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.DeliveryActivity;

import java.io.ByteArrayOutputStream;
import java.security.Permission;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserInforFragment extends Fragment {


    public UserInforFragment() {
        // Required empty public constructor
    }

    private CircleImageView profile_img;
    private Button btn_change_photo, btn_remove_photo, btn_update, btn_confirm;
    private EditText edt_username, edt_email, password_confirm;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Dialog loadingDialog, confirm_password_dialog;
    String username, email, profile;
    private Uri imageUri;
    private boolean updatePhoto = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user_infor, container, false);
        profile_img = view.findViewById(R.id.imv_circle_user_infor);
        btn_change_photo = view.findViewById(R.id.btn_change_photo);
        btn_remove_photo = view.findViewById(R.id.btn_remove_photo);
        btn_update = view.findViewById(R.id.btn_update_infor);
        edt_username = view.findViewById(R.id.edt_username_infor);
        edt_email = view.findViewById(R.id.edt_email_infor);

        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_main));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        confirm_password_dialog = new Dialog(getContext());
        confirm_password_dialog.setContentView(R.layout.dialog_password_confirm);
        confirm_password_dialog.setCancelable(true);
        confirm_password_dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_main));
        confirm_password_dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        password_confirm = confirm_password_dialog.findViewById(R.id.edt_password_confirm_dialog);
        btn_confirm = confirm_password_dialog.findViewById(R.id.btn_confirm_pwd_dialog);

        username = getArguments().getString("username");
        email = getArguments().getString("email");
        profile = getArguments().getString("profile");


        Glide.with(getContext()).load(profile).into(profile_img);
        edt_username.setText(username);
        edt_email.setText(email);

        btn_change_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Intent gallaryIntent = new Intent(Intent.ACTION_PICK);
                        gallaryIntent.setType("image/*");
                        startActivityForResult(gallaryIntent, 1);
                    }else {
                        getActivity().requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                    }
                }else {
                    Intent gallaryIntent = new Intent(Intent.ACTION_PICK);
                    gallaryIntent.setType("image/*");
                    startActivityForResult(gallaryIntent, 1);
                }
            }
        });
        btn_remove_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUri = null;
                updatePhoto = true;
                Glide.with(getContext()).load(R.drawable.user).into(profile_img);
            }
        });

        edt_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    checkEmailAndPassword();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == getActivity().RESULT_OK){
                if(data != null){
                    imageUri = data.getData();
                    updatePhoto = true;
                    Glide.with(getContext()).load(imageUri).into(profile_img);
                }else {
                    Toast.makeText(getContext(), "Image not found!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void checkInput() {
        if(!TextUtils.isEmpty(edt_username.getText())){
            if(!TextUtils.isEmpty(edt_email.getText())){
                btn_update.setEnabled(true);
            } else {
                btn_update.setEnabled(false);
            }
        }
        else {
            btn_update.setEnabled(false);
        }
    }
    private void checkEmailAndPassword(){
        if(edt_email.getText().toString().matches(emailPattern)){
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(edt_email.getText().toString().toLowerCase().trim().equals(email.toLowerCase().trim())){
                //update username
                loadingDialog.show();
                updatePhotoSer(user);
            }else {
                //Change email so show dislog
                confirm_password_dialog.show();
                btn_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        loadingDialog.show();
                        String userPassword = password_confirm.getText().toString();
                        confirm_password_dialog.dismiss();

                        AuthCredential credential = EmailAuthProvider.getCredential(email, userPassword);
                        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                 if(task.isSuccessful()){
                                     user.updateEmail(edt_email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                         @Override
                                         public void onComplete(@NonNull Task<Void> task) {
                                             if(task.isSuccessful()){

                                                updatePhotoSer(user);

                                             }else {
                                                 loadingDialog.dismiss();
                                                 String error = task.getException().getMessage();
                                                 Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     });

                                 }else {
                                     loadingDialog.dismiss();
                                     String error = task.getException().getMessage();
                                     Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                                 }
                            }
                        });

                    }
                });
            }
        }else {
            edt_email.setError("Email sai cú pháp!");
        }
    }

    private void updateFile(FirebaseUser user, final Map<String, Object> map) {
        FirebaseFirestore.getInstance().collection("USERS").document(user.getUid()).update(map
        ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(map.size() > 1){
                        DbQueries.email = edt_email.getText().toString().trim();
                        DbQueries.fullname = edt_username.getText().toString().trim();
                    }else {
                        DbQueries.fullname = edt_username.getText().toString().trim();
                    }
                    getActivity().finish();
                    Toast.makeText(getContext() , "Đã update thành công", Toast.LENGTH_SHORT).show();
                }else {
                    String error = task.getException().getMessage();
                    Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();

                }
                loadingDialog.dismiss();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 2){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent gallaryIntent = new Intent(Intent.ACTION_PICK);
                gallaryIntent.setType("image/*");
                startActivityForResult(gallaryIntent, 1);
            }else {
                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void updatePhotoSer(final FirebaseUser user){

        if(updatePhoto){
            final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Profiles/"  + user.getUid() + ".jpg");
            if(imageUri != null){
                Glide.with(getContext()).asBitmap().load(imageUri).circleCrop().into(new ImageViewTarget<Bitmap>(profile_img) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        resource.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();

                        UploadTask uploadTask = storageReference.putBytes(data);
                        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if(task.isSuccessful()){
                                    storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if(task.isSuccessful()){

                                                imageUri =task.getResult();
                                                DbQueries.profile =task.getResult().toString();
                                                Glide.with(getContext()).load(DbQueries.profile).into(profile_img);

                                                Map<String, Object> map = new HashMap<>();
                                                map.put("email", edt_email.getText().toString());
                                                map.put("username", edt_username.getText().toString());
                                                map.put("profile", DbQueries.profile);

                                                updateFile(user, map);

                                            }else {
                                                loadingDialog.dismiss();
                                                DbQueries.profile = "";
                                                String error = task.getException().getMessage();
                                                Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else {
                                    loadingDialog.dismiss();
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                        return;

                    }

                    @Override
                    protected void setResource(@Nullable Bitmap resource) {

                        profile_img.setImageResource(R.drawable.user);
                    }
                });
            }else {
                //remove photo
                storageReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            DbQueries.profile = "";

                            Map<String, Object> map = new HashMap<>();
                            map.put("email", edt_email.getText().toString());
                            map.put("username", edt_username.getText().toString());
                            map.put("profile", "");

                            updateFile(user, map);
                        }else {
                            loadingDialog.dismiss();
                            String error = task.getException().getMessage();
                            Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        }else {
            Map<String, Object> map = new HashMap<>();
            map.put("username", edt_username.getText().toString());

            updateFile(user, map);
        }

    }
}
