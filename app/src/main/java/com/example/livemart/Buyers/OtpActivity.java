package com.example.livemart.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.livemart.Admin.AdminCategoryActivity;
import com.example.livemart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    private EditText OTPCode;
    private Button OTPBtn, ResendBtn;
    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;
    private static final String TAG = "PhoneAuthActivity";
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    String phoneNo;
    String isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        OTPCode = (EditText) findViewById(R.id.login_otp);
        OTPBtn = (Button) findViewById(R.id.login_otp_btn);
        ResendBtn = (Button) findViewById(R.id.resend_btn);
        loadingBar = new ProgressDialog(this);

        Intent intent1 = getIntent();
        phoneNo = intent1.getStringExtra("phoneNo");
        isAdmin = intent1.getStringExtra("isAdmin");

        mAuth = FirebaseAuth.getInstance();

        OTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerifyOTP();
            }
        });

        ResendBtn.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
               resendVerificationCode(phoneNo, mResendToken);
           }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };

        startVerificationCode(phoneNo);
    }

    public void startVerificationCode(String phone){

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
        // [END verify_with_code]
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .setForceResendingToken(token)     // ForceResendingToken from callbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            //FirebaseUser user = task.getResult().getUser();
                            if (isAdmin.equals("yes")){
                                loadingBar.dismiss();
                                Toast.makeText(OtpActivity.this, "OTP Authentication", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(OtpActivity.this, AdminCategoryActivity.class);
                                startActivity(intent);
                            }
                            else {
                                loadingBar.dismiss();
                                Toast.makeText(OtpActivity.this, "OTP Authentication", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(OtpActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                loadingBar.dismiss();
                                Toast.makeText(OtpActivity.this, "Entered OTP is incorrect.", Toast.LENGTH_SHORT).show();
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    private void VerifyOTP()
    {
        loadingBar.setTitle("OTP Verification");
        loadingBar.setMessage("Please wait, while we are checking the credentials.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        String codeByUser = OTPCode.getText().toString();
        if (codeByUser.matches("")) {
            loadingBar.dismiss();
            Toast.makeText(OtpActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
        }
        else {
            verifyPhoneNumberWithCode(mVerificationId, codeByUser);
        }
    }
}