package com.example.chatandroid.controller;

import android.view.View;
import android.widget.Toast;

import com.example.chatandroid.R;
import com.example.chatandroid.model.User;
import com.example.chatandroid.view.SignUpActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpController implements View.OnClickListener {

    private SignUpActivity activity;

    public SignUpController(SignUpActivity activity) {
        this.activity = activity;
        activity.getiHaveUserTV().setOnClickListener(this);
        activity.getSignInBtn().setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iHaveUserTV:
                activity.finish();
                break;
            case R.id.signInBtn:

                String name  = activity.getNameET().getText().toString();
                String email  = activity.getEmailET().getText().toString();
                String userName = activity.getUsernameET().getText().toString();
                String password  = activity.getPasswordET().getText().toString();
                String rePassword  = activity.getRePasswordET().getText().toString();

                if(!password.equals(rePassword)){
                    Toast.makeText(activity,"las contraseñas no coinciden",Toast.LENGTH_LONG).show();
                    return;
                }


                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener(authResult -> {
                        String uid = FirebaseAuth.getInstance().getUid();
                        User user = new User(uid, name, email, userName, password);
                        FirebaseDatabase.getInstance().getReference().child("users").child(user.getId()).setValue(user);
                        activity.finish();
                    }).addOnFailureListener(e->{
                        Toast.makeText(activity,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                });
                break;
        }
    }
}
