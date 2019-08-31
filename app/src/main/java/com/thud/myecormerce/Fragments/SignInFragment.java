package com.thud.myecormerce.Fragments;


import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.MainActivity;
import com.thud.myecormerce.View.RegisterActivity;



/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    private TextView txt_dont_have_account;
    private TextView txt_forgot_password;
    private FrameLayout parentframeLayout;
    private EditText edt_email, edt_password;
    private ImageButton btn_close_signin;
    private CheckBox ckb_rememberme;
    private Button btn_signin;
    private ProgressBar progressBar;
    private String email, password;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private FirebaseAuth firebaseAuth;
    public static boolean disibleCloseBtn = false;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_in, container, false);
        txt_dont_have_account = view.findViewById(R.id.txt_signup_signin);
        parentframeLayout = getActivity().findViewById(R.id.register_frame_layout);

        btn_close_signin = view.findViewById(R.id.icon_lose_sigin);
        edt_email = view.findViewById(R.id.edt_email_signin);
        edt_password = view.findViewById(R.id.edt_password_signin);
        ckb_rememberme = view.findViewById(R.id.ckb_remember_me);
        txt_forgot_password = view.findViewById(R.id.txt_fogot_pass_signin);
        progressBar = view.findViewById(R.id.progress_signin);
        btn_signin = view.findViewById(R.id.btn_signin_signin);

        firebaseAuth = FirebaseAuth.getInstance();
        if(disibleCloseBtn){
            btn_close_signin.setVisibility(View.GONE);
        }
        else
        {
            btn_close_signin.setVisibility(View.VISIBLE);
        }
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt_dont_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }
        });
        txt_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.onResetPass = true;
                setFragment(new ForgotPassword());
            }
        });
        btn_close_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainIntent();
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
        edt_password.addTextChangedListener(new TextWatcher() {
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
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edt_email.getText().toString();
                password = edt_password.getText().toString();
                CheckEmailPassword(email, password);
            }
        });
    }

    private void CheckEmailPassword(String email, String password) {
        if(email.matches(emailPattern)){
            if(password.length() >= 6){

                progressBar.setVisibility(View.VISIBLE);
                btn_signin.setEnabled(false);

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    MainIntent();
                                }
                                else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    btn_signin.setEnabled(true);
                                    Toast.makeText(getActivity(), "Sai email hoặc password, Vui lòng kiểm tra lại!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else {
                edt_password.setError("Mật khẩu quá ngắn!");
            }
        }
        else {
            edt_email.setError("Email sai định dạng!");
        }
    }

    private void MainIntent() {
        if (disibleCloseBtn){
            disibleCloseBtn = false;
        }
        else {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
        getActivity().finish();
    }

    private void checkInput() {
        if(!TextUtils.isEmpty(edt_email.getText())){
            if(!TextUtils.isEmpty(edt_password.getText())){
                btn_signin.setEnabled(true);
            }
            else {
                btn_signin.setEnabled(false);
            }
        }
        else {
            btn_signin.setEnabled(false);        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_left, R.anim.slide_out_right);
        fragmentTransaction.replace(parentframeLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}
