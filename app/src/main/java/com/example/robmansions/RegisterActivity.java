package com.example.robmansions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class RegisterActivity extends AppCompatActivity {

    private MaterialEditText username_text;
    private MaterialEditText email_text;
    private MaterialEditText password_text;


    private DatabaseReference userRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username_text=(MaterialEditText)findViewById(R.id.username);
        email_text=(MaterialEditText)findViewById(R.id.email);
        password_text=(MaterialEditText)findViewById(R.id.password);

        userRef= FirebaseDatabase.getInstance().getReference("Users");
        mAuth=FirebaseAuth.getInstance();



    }
    public void RegisterButtonClicked(View view)
    {
        final String username=username_text.getText().toString().trim();
       final  String password=password_text.getText().toString().trim();
       final  String email=email_text.getText().toString().trim();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(username))
        {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful())
                    {



                        String user_id = mAuth.getCurrentUser().getUid();

                        DatabaseReference newUser = userRef.child(user_id);



                        newUser.child("username").setValue( username);
                        newUser.child("Email").setValue(email);

                        Intent intent=new Intent(RegisterActivity.this,Main2Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                }
            });
        }
    }
}
