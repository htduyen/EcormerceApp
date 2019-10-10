package com.thud.myecormerce.Fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.thud.myecormerce.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {


    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    private EditText edt_old_password, edt_new_pass, edt_pass_cofirm;
    private TextInputLayout textInputLayout_oldpass, textInputLayout_newpass, textInputLayout_cofirmpass;
    private Button btn_update;
    private Dialog loadingDialog;
    private String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        btn_update = view.findViewById(R.id.btn_update_password);
        textInputLayout_oldpass = view.findViewById(R.id.textInput_oldpass);
        textInputLayout_newpass = view.findViewById(R.id.textInput_newpass);
        textInputLayout_cofirmpass= view.findViewById(R.id.textInput_confirmpass);

        edt_old_password = view.findViewById(R.id.edt_old_password);
        edt_new_pass = view.findViewById(R.id.edt_new_password);
        edt_pass_cofirm = view.findViewById(R.id.edt_confirm_password);

        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_main));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        edt_old_password.addTextChangedListener(new TextWatcher() {
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
        edt_new_pass.addTextChangedListener(new TextWatcher() {
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
        edt_pass_cofirm.addTextChangedListener(new TextWatcher() {
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

        email = getArguments().getString("email");

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });
        return view;
    }

    private void checkInput() {
        if (!TextUtils.isEmpty(edt_old_password.getText()) && edt_old_password.length() >= 6) {
            if (!TextUtils.isEmpty(edt_new_pass.getText()) && edt_new_pass.length() >= 6) {
                if (!TextUtils.isEmpty(edt_pass_cofirm.getText()) && edt_pass_cofirm.length() >= 6) {
                    btn_update.setEnabled(true);
                } else {
                    btn_update.setEnabled(false);
                }

            } else {
                btn_update.setEnabled(false);
            }
        } else {
            btn_update.setEnabled(false);
        }
    }

    private void checkEmailAndPassword() {
        if (edt_new_pass.getText().toString().equals(edt_pass_cofirm.getText().toString())) {
            loadingDialog.show();
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential(email, edt_old_password.getText().toString());
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        user.updatePassword(edt_new_pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    edt_old_password.setText(null);
                                    edt_new_pass.setText(null);
                                    edt_pass_cofirm.setText(null);
                                    getActivity().finish();
                                    Toast.makeText(getContext(), "Đã update mật khẩu mới", Toast.LENGTH_SHORT).show();
                                }else {

                                    String error = task.getException().getMessage();
                                    Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                                }
                                loadingDialog.dismiss();
                            }
                        });
                    } else {
                        loadingDialog.dismiss();
                        String error = task.getException().getMessage();
                        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {

            edt_pass_cofirm.setError("Không khớp!");
        }
    }
}



