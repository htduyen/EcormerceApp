package com.thud.myecormerce.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thud.myecormerce.R;
import com.thud.myecormerce.View.MainActivity;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    private TextView txt_alreadly_have_account;
    private FrameLayout parentframeLayout;

    private ImageButton btn_close;
    private TextInputLayout layout_username, layout_email, layout_password, layout_conform_pass;
    private EditText edt_username, edt_password, edt_email, edt_conform_pass;
    private Button btn_signup;
    private ProgressBar process;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String username, email, password, conform_pass;

    //private Fibabase

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);
        txt_alreadly_have_account = view.findViewById(R.id.txt_signin_signup);
        parentframeLayout = getActivity().findViewById(R.id.register_frame_layout);

        btn_close = view.findViewById(R.id.icon_lose_signup);
        layout_username = view.findViewById(R.id.layout_username_signup);
        layout_email = view.findViewById(R.id.layout_email_signup);
        layout_password = view.findViewById(R.id.layout_password_signup);
        layout_conform_pass = view.findViewById(R.id.layout_conform_pass_password);

        edt_username = view.findViewById(R.id.edt_username_signup);
        edt_email = view.findViewById(R.id.edt_email_signup);
        edt_password = view.findViewById(R.id.edt_password_signup);
        edt_conform_pass = view.findViewById(R.id.edt_conform_pass_signup);

        //Settext mặc định
        edt_username.setText("Thanh Duyen....");
        edt_email.setText("thanhduyen@gmail.com");
        edt_password.setText("123456");
        edt_conform_pass.setText("123456");


        process = view.findViewById(R.id.progress_signup);
        btn_signup = view.findViewById(R.id.btn_signup_signup);

        email = edt_email.getText().toString();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt_alreadly_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainIntent();
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
        edt_conform_pass.addTextChangedListener(new TextWatcher() {
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

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edt_email.getText().toString().trim();
                username = edt_username.getText().toString().trim();
                password = edt_password.getText().toString();
                conform_pass = edt_conform_pass.getText().toString();

                //Đặt lại icon warning
//                Drawable customIconWarning = getResources().getDrawable(R.drawable.triangle);
//                customIconWarning.setBounds(0,0, customIconWarning.getIntrinsicWidth(), customIconWarning.getIntrinsicHeight());
//                edt_conform_pass.setError("Mật khẩu không khớp", customIconWarning);  //Su dung

                if (email.matches(emailPattern) && email.length() > 0)
                {
                    edt_email.setError(null);
                    if (password.equals(conform_pass)){

                        process.setVisibility(View.VISIBLE);
                        btn_signup.setEnabled(false);

                        edt_conform_pass.setError(null);
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){

                                            Map<Object, String> userData = new HashMap<>();
                                            userData.put("username", username);
                                            //Toast.makeText(getActivity(), "Username: " + username, Toast.LENGTH_SHORT).show();
                                            firebaseFirestore.collection("USERS")
                                                    .add(userData)
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                                            if (task.isSuccessful()){
                                                                Toast.makeText(getActivity(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                                                MainIntent();
                                                            }
                                                            else
                                                            {
                                                                process.setVisibility(View.INVISIBLE);
                                                                btn_signup.setEnabled(true);
                                                                edt_conform_pass.setError("Mật khẩu không khớp");
                                                                Toast.makeText(getActivity(), "Pass" + edt_password.getText() + "\n" + "Conform: " + edt_conform_pass.getText(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                        else {
                                            process.setVisibility(View.INVISIBLE);
                                            String error = task.getException().toString();
                                            Toast.makeText(getActivity(), "Tài khoản đã tồn tại!" ,Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    else {
                        process.setVisibility(View.INVISIBLE);
                        btn_signup.setEnabled(true);
                        edt_conform_pass.setError("Mật khẩu không khớp");
                        Toast.makeText(getActivity(), "Pass" + edt_password.getText() + "\n" + "Conform: " + edt_conform_pass.getText(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    process.setVisibility(View.INVISIBLE);
                    edt_email.setError("Sai định dạng email");
                }
            }
        });

    }

    private void MainIntent() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void checkInput() {
        if(!TextUtils.isEmpty(edt_username.getText())){
            if(!TextUtils.isEmpty(edt_email.getText())){
                if(!TextUtils.isEmpty(edt_password.getText())){
                    if (!TextUtils.isEmpty(edt_conform_pass.getText())){
                        btn_signup.setEnabled(true);
                    }
                    else {
                        btn_signup.setEnabled(false);
                    }
                }
                else {
                    btn_signup.setEnabled(false);
                }
            }
            else {
                btn_signup.setEnabled(false);
            }
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_left, R.anim.slide_out_right);
        fragmentTransaction.replace(parentframeLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}
