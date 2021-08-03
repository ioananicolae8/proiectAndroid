package com.example.licenta.smartdoctor.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.licenta.R;
import com.example.licenta.smartdoctor.enums.ETipUtilizator;
import com.example.licenta.smartdoctor.interfaces.IAuthentication;
import com.example.licenta.smartdoctor.utils.common.AnimationHelper;
import com.example.licenta.smartdoctor.utils.common.AuthenticationHelper;
import com.example.licenta.smartdoctor.utils.common.ConnectionHelper;
import com.example.licenta.smartdoctor.utils.common.TipUtilizatorLogatHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Doctor_LogInActivity extends AppCompatActivity implements View.OnClickListener, IAuthentication {

    private static int ANIMATION_DURATION = 150;

    private AutoCompleteTextView viewEmail;
    private EditText viewPassword;
    private ProgressBar mProgressBar;

    private Button btnSignIn;
    private TextView viewGoToRegistration;
    private TextView viewForgotPassword;

    private FirebaseAuth mAuth;

    private Pair<String, String> mCredentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__log_in);

        mAuth = FirebaseAuth.getInstance();

        mProgressBar = findViewById(R.id.loginProgressBar);
        viewEmail = findViewById(R.id.viewEmail);
        viewPassword = findViewById(R.id.viewPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        viewGoToRegistration = findViewById(R.id.goToRegistrationTextView);
        viewForgotPassword = findViewById(R.id.forgotPasswordTextView);

        btnSignIn.setOnClickListener(this);
        viewGoToRegistration.setOnClickListener(this);
        viewForgotPassword.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectionHelper.hasConnection(Doctor_LogInActivity.this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                boolean success = checkCredentials();
                if(success) {
                    startAnimation();
                    signIn(mCredentials.first, mCredentials.second);
                }
                break;

            case R.id.goToRegistrationTextView:
                Intent register = new Intent(Doctor_LogInActivity.this, Doctor_RegisterActivity.class);
                startActivity(register);
                break;

            case R.id.forgotPasswordTextView:
                Intent forgotPassword = new Intent(Doctor_LogInActivity.this, ForgotPasswordActivity.class);
                startActivity(forgotPassword);
                break;
        }
    }

    private void startAnimation() {
        AnimationHelper.fadeInAnimation(mProgressBar, ANIMATION_DURATION);
    }

    private void resetAnimation() {
        AnimationHelper.fadeOutAnimation(mProgressBar, ANIMATION_DURATION);
    }

    private boolean checkCredentials() {
        // Reset errors.
        viewEmail.setError(null);
        viewPassword.setError(null);

        // Store values at the time of the login attempt.
        String email = viewEmail.getText().toString();
        String password = viewPassword.getText().toString();
        mCredentials = new Pair<>(email, password);

        boolean success = true;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            viewPassword.setError(getString(R.string.error_field_required));
            focusView = viewPassword;
            success = false;
        } else if (!isPasswordValid(password)) {
            viewPassword.setError(getString(R.string.error_invalid_password));
            focusView = viewPassword;
            success = false;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            viewEmail.setError(getString(R.string.error_field_required));
            focusView = viewEmail;
            success = false;
        } else if (!isEmailValid(email)) {
            viewEmail.setError(getString(R.string.error_invalid_email));
            focusView = viewEmail;
            success = false;
        }

        if (!success) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        return success;
    }

    private boolean isEmailValid(String email) {
        return AuthenticationHelper.isEmailValid(email);
    }

    private boolean isPasswordValid(String password) {
        return AuthenticationHelper.isPasswordValid(password);
    }

    @Override
    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            TipUtilizatorLogatHelper.seteazaTipUtilizatorLogat(Doctor_LogInActivity.this, ETipUtilizator.DOCTOR);
//                            ToastHelper.showToastWithImage(Doctor_LogInActivity.this, "Bun venit!", R.drawable.icon_success);
                            Intent intent = new Intent(Doctor_LogInActivity.this, Doctor_MenuActivity.class);
                            startActivity(intent);
                        } else {
                            viewPassword.getText().clear();
                            resetAnimation();
//                            ToastHelper.showToastWithImage(Doctor_LogInActivity.this, getString(R.string.login_access_denied), R.drawable.icon_access_denied);
                        }
                    }
                });
    }
}