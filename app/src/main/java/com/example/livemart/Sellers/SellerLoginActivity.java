package com.example.livemart.Sellers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.livemart.Admin.AdminCategoryActivity;
import com.example.livemart.Buyers.LoginActivity;
import com.example.livemart.Buyers.OtpActivity;
import com.example.livemart.Model.Admins;
import com.example.livemart.Model.Users;
import com.example.livemart.Prevalent.Prevalent;
import com.example.livemart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class SellerLoginActivity extends AppCompatActivity {


    private Button loginSellerBtn;
    private EditText phoneInput, passwordInput; //emailInput
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        //mAuth = FirebaseAuth.getInstance();

        //emailInput = findViewById(R.id.seller_login_email);
        phoneInput = findViewById(R.id.seller_login_email);
        passwordInput = findViewById(R.id.seller_Login_password);
        loginSellerBtn = findViewById(R.id.seller_login_btn);
        loadingBar = new ProgressDialog(this);

        loginSellerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSeller();
            }
        });
    }

    private void loginSeller() {
        //final String email = emailInput.getText().toString();
        final String phone = phoneInput.getText().toString();
        final String password = passwordInput.getText().toString();

        if(!phone.equals("") && !password.equals("")) {
            loadingBar.setTitle("Seller Account Login");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            final DatabaseReference RootRef;
            RootRef = FirebaseDatabase.getInstance().getReference();
            String parentDbName = "Admins";

            RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.child(parentDbName).child(phone).exists())
                    {
                        Admins usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Admins.class);

                        if (usersData.getPhone().equals(phone))
                        {
                            if (usersData.getPassword().equals(password))
                            {
                                Intent intent = new Intent(SellerLoginActivity.this, AdminCategoryActivity.class);
                                startActivity(intent);

                            }
                            else
                            {
                                loadingBar.dismiss();
                                Toast.makeText(SellerLoginActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(SellerLoginActivity.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            //////**************************************
            /*mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
               @Override
               public void onComplete(@NonNull Task<AuthResult> task){
                   if(task.isSuccessful()){
                       Intent intent = new Intent(SellerLoginActivity.this, SellerHomeActivity.class);
                       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       startActivity(intent);
                       finish();
                   }
               }
            });*/
            //////********************************************
        }
        else{
            Toast.makeText(this,"Please complete the login form.",Toast.LENGTH_SHORT).show();
        }
    }
}