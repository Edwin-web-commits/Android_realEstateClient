package com.example.robmansions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Testimonial_Fragment extends Fragment {

 private ImageView profilePic;
 private EditText textMessage;
 //private TextView textUsername;
 private Button sendButton;

 private DatabaseReference myRef, testimonials;
 private StorageReference firebaseStorage;
 private FirebaseUser mUser;

 private RecyclerView recyclerView;
 private FirebaseAuth mAuth;
 private FirebaseAuth.AuthStateListener authStateListener;

    private Uri uri;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.testimonial_fragment, container, false);

        profilePic=view.findViewById(R.id.testimonialProfilePic);
        textMessage=view.findViewById(R.id.testimonialmessage);
        sendButton=view.findViewById(R.id.sendingBtn);

        mUser= FirebaseAuth.getInstance().getCurrentUser();
        myRef= FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid());
        testimonials=FirebaseDatabase.getInstance().getReference("Testimonials");

        mAuth=FirebaseAuth.getInstance();

        recyclerView=view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseStorage = FirebaseStorage.getInstance().getReference();




        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


               // String name=(String)dataSnapshot.child("username").getValue();
               // textUsername.setText(name);

                String Pimage=(String)dataSnapshot.child("imageURL").getValue();
                Picasso.with(getContext()).load(Pimage).into(profilePic);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String message = textMessage.getText().toString().trim();


                if (!TextUtils.isEmpty(message)) {


                    final DatabaseReference newPost = testimonials.push();


                     newPost.child("Message").setValue(message);


                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            newPost.child("Username").setValue(dataSnapshot.child("username").getValue());
                            newPost.child("Image").setValue(dataSnapshot.child("imageURL").getValue());

                            Toast.makeText(getContext(), "Send ", Toast.LENGTH_LONG).show();



                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    textMessage.setText("");



                }
                else
                {
                    Toast.makeText(getContext(), "Message field cannot be left empty !! ", Toast.LENGTH_LONG).show();
                }
            }
        });


        //check if the user logged in or not
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                {
                    Intent loginIntent=new Intent(getContext(),LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginIntent);
                }
            }
        };





        return view;



    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);

        FirebaseRecyclerAdapter<testiClass,TestimonHolder> FBRA=new FirebaseRecyclerAdapter<testiClass, TestimonHolder>(

                testiClass.class,
                R.layout.testimonial_row,
                TestimonHolder.class,
                testimonials


        ) {
            @Override
            protected void populateViewHolder(TestimonHolder testimonHolder, testiClass testimonClass, int i) {

                testimonHolder.setMessage(testimonClass.getMessage());
                testimonHolder.setUsername(testimonClass.getUsername());
                testimonHolder.setImage(getContext(),testimonClass.getImage());

            }
        };
        recyclerView.setAdapter(FBRA);
    }
    public static class TestimonHolder extends RecyclerView.ViewHolder{

        View myview ;
        public TestimonHolder(@NonNull View itemView) {
            super(itemView);

            myview=itemView;
        }

        public void setImage(Context context, String image)
        {
            ImageView myImage=(ImageView) myview.findViewById(R.id.testiRowProfile);
            Picasso.with(context).load(image).into(myImage);
        }
        public void setUsername(String username){

            TextView name=(TextView)myview.findViewById(R.id.testRowUsername);
            name.setText(username);

        }
        public void setMessage(String message)
        {
            TextView mymessage=(TextView)myview.findViewById(R.id.testRow_message);
            mymessage.setText(message);
        }

    }
}
