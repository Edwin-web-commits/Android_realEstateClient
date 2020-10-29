package com.example.robmansions;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Conatct_Fragment extends Fragment {

    private DatabaseReference myRef;
    private EditText nametxt;
    private EditText emailtxt;
    private EditText phonetxt;
    private EditText propertiestxt;
     private Button submitText;
     private EditText enquirytxt;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.contact_fragment, container, false);

        nametxt=view.findViewById(R.id.nameText);
        emailtxt=view.findViewById(R.id.emailTxt);
        phonetxt=view.findViewById(R.id.phoneTxt);
        propertiestxt=view.findViewById(R.id.propertyTxt);
        submitText=view.findViewById(R.id.submitText);
        enquirytxt=view.findViewById(R.id.enquiryTxt);

        myRef= FirebaseDatabase.getInstance().getReference("Contacted");


        submitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=nametxt.getText().toString().trim();
                String email=emailtxt.getText().toString().trim();
                String phone=phonetxt.getText().toString().trim();
                String property=propertiestxt.getText().toString().trim();
                String enquiry=enquirytxt.getText().toString().trim();

                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(property) && !TextUtils.isEmpty(enquiry))
                {
                   final DatabaseReference newPost=myRef.push();
                    newPost.child("Name").setValue(name);
                    newPost.child("Email").setValue(email);
                    newPost.child("Phone").setValue(phone);
                    newPost.child("Properties").setValue(property);
                    newPost.child("Enquiry").setValue(enquiry);

                    Toast.makeText(getContext(),"Submitted successfuly ",Toast.LENGTH_LONG).show();



                }

               nametxt.setText(" ");
                emailtxt.setText(" ");
                phonetxt.setText(" ");
                propertiestxt.setText(" ");
                enquirytxt.setText( " ");




            }
        });



        return view;


    }
}
