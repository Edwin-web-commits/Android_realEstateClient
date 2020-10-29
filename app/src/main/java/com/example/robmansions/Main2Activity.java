package com.example.robmansions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
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

public class Main2Activity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;

    public TextView username;
    public TextView useremail;
    private ImageView profileImg;
    private DatabaseReference userRef ,reference;

    private static final int GALLERY_REQ=2;
    private Uri uri;
    private FirebaseUser fuser;

    private StorageReference storageReference;

    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        navigationView=(NavigationView)findViewById(R.id.nav_view);
        View headerView=navigationView.getHeaderView(0);
         username=(TextView)headerView.findViewById(R.id.profileName);
         useremail=(TextView)headerView.findViewById(R.id.userEmail);
         profileImg=(ImageView)headerView.findViewById(R.id.profileImage);


        userRef= FirebaseDatabase.getInstance().getReference().child("Users");
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference().child("Users").child(fuser.getUid());
        fuser= FirebaseAuth.getInstance().getCurrentUser();

        storageReference= FirebaseStorage.getInstance().getReference("uploads");


        drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

       if(savedInstanceState==null){

           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Gallery_Fragment()).commit();
           navigationView.setCheckedItem(R.id.viewgallery);
        }



        userRef.child(fuser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String userNAme=(String)dataSnapshot.child("username").getValue();
                String email =(String)dataSnapshot.child("Email").getValue();
                String profileimage=(String)dataSnapshot.child("imageURL").getValue();


               // Toast.makeText(Main2Activity.this,email,Toast.LENGTH_LONG).show();
                username.setText(userNAme);
                useremail.setText(email);
                Picasso.with(getApplicationContext()).load(profileimage).into(profileImg);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

      profileImg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               Intent galleryIntent = new Intent();
               galleryIntent.setType("image/*");
               galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

               startActivityForResult(galleryIntent, GALLERY_REQ);
           }

           });




    }

    public void profileImageClicked(View view)
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(galleryIntent, GALLERY_REQ);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQ && resultCode == RESULT_OK) {
            uri = data.getData();
            profileImg.setImageURI(uri);


            uploadImage();
        }

    }

    private void uploadImage() {

        final ProgressDialog pd=new ProgressDialog(Main2Activity.this);
        pd.setMessage("Uploading");
        pd.show();

        if(uri !=null) {

            final StorageReference filePath = storageReference.child("uploads").child(uri.getLastPathSegment());

            final UploadTask uploadTask = filePath.putFile(uri);

            // getting the DownloadUri of the image

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL


                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {


                @Override      //if task is completed and the DownloadUri is returned ,
                public void onComplete(@NonNull Task<Uri> task) {

                    if (task.isSuccessful()) {
                        final Uri downloadUri = task.getResult();


                        // final DatabaseReference newPost = reference.push();
                        reference=FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

                        reference.child("imageURL").setValue(downloadUri.toString());

                        pd.dismiss();


                    } else {
                        // Handle failures
                        // ...
                        ;
                        Toast.makeText(Main2Activity.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(Main2Activity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    pd.dismiss();

                }
            });
        }else
        {
            Toast.makeText(Main2Activity.this,"No image selected",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {

        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.viewgallery:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Gallery_Fragment()).commit();
                break;
            case R.id.locations:
               Intent mapsIntent=new Intent(Main2Activity.this,MapsActivity.class);
               startActivity(mapsIntent);
                break;
            case R.id.testimonials:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Testimonial_Fragment()).commit();
                break;
            case R.id.contactus:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Conatct_Fragment()).commit();
                break;
            case R.id.nav_send:
                Toast.makeText(this,"Send",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_share:
                Toast.makeText(this,"Share",Toast.LENGTH_LONG).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
