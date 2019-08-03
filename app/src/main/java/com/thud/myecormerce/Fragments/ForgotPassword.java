package com.thud.myecormerce.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.RegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPassword extends Fragment {

    private EditText edt_key_reset;
    private Button btn_reset;
    private TextView txt_goback;
    private ProgressBar progressBar;
    private LinearLayout layout_forgot;
    private String email;
    private FrameLayout parentframeLayout;
    private FirebaseAuth firebaseAuth;

    public ForgotPassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_forgot_password, container, false);
        edt_key_reset = view.findViewById(R.id.edt_key_resetpass_signin);
        btn_reset = view.findViewById(R.id.btn_reset_pass_forgot);
        txt_goback = view.findViewById(R.id.txt_goback_forgot);
        progressBar = view.findViewById(R.id.progress_forgot);
        layout_forgot = view.findViewById(R.id.layout_mail_forgot);
        parentframeLayout = getActivity().findViewById(R.id.register_frame_layout);
        firebaseAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edt_key_reset.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInPut();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = edt_key_reset.getText().toString();
                btn_reset.setEnabled(false);

                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getActivity(), "Tin nhắn đã được gửi", Toast.LENGTH_SHORT).show();
                                    layout_forgot.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    Toast.makeText(getActivity(), "Lỗi reset", Toast.LENGTH_SHORT).show();
                                }
                                btn_reset.setEnabled(true);
                            }
                        });
            }
        });

    }

    private void checkInPut() {
        if(TextUtils.isEmpty(edt_key_reset.getText())){
            btn_reset.setEnabled(false);
        }
        else
        {
            btn_reset.setEnabled(true);
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_left, R.anim.slide_out_right);
        fragmentTransaction.replace(parentframeLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}
