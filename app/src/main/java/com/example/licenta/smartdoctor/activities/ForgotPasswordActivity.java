package com.example.licenta.smartdoctor.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licenta.R;
import com.example.licenta.smartdoctor.utils.common.AuthenticationHelper;
import com.example.licenta.smartdoctor.utils.common.ConnectionHelper;
import com.example.licenta.smartdoctor.utils.common.ToastHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView emailTextView;
    private Button resetButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_pacient);

        emailTextView = findViewById(R.id.forgotEmailTextView);
        resetButton = findViewById(R.id.resetButton);
        progressBar = findViewById(R.id.forgotProgressBar);

        resetButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectionHelper.hasConnection(ForgotPasswordActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resetButton:
                boolean success = checkCredentials();
                if (success) {
                    String email = emailTextView.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    resetPassword(email);
                }
                break;
        }
    }

    private void resetPassword(String email) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            ToastHelper.showToastWithImage(
                                    ForgotPasswordActivity.this,
                                    ForgotPasswordActivity.this.getString(R.string.reset_password_success),
                                    R.drawable.icon_success,
                                    Toast.LENGTH_LONG
                            );
                            ForgotPasswordActivity.this.onBackPressed();
                        } else {
                            ToastHelper.showToastWithImage(
                                    ForgotPasswordActivity.this,
                                    ForgotPasswordActivity.this.getString(R.string.reset_password_failure),
                                    R.drawable.icon_error,
                                    Toast.LENGTH_LONG
                            );
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    private boolean checkCredentials() {
        // Reset errors.
        emailTextView.setError(null);

        // Store values at the time of the login attempt.
        String email = emailTextView.getText().toString();

        boolean success = true;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailTextView.setError(getString(R.string.error_field_required));
            focusView = emailTextView;
            success = false;
        } else if (!AuthenticationHelper.isEmailValid(email)) {
            emailTextView.setError(getString(R.string.error_invalid_email));
            focusView = emailTextView;
            success = false;
        }

        if (!success) {
            focusView.requestFocus();
        }

        return success;
    }
}
