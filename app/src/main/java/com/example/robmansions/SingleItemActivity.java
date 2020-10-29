package com.example.robmansions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Ref;

public class SingleItemActivity extends AppCompatActivity {

    private static String item_key=null;
    private ImageView cardImage;
    private TextView location, viewType,price ;



    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);

        cardImage = (ImageView) findViewById(R.id.cardImage);
        location = (TextView) findViewById(R.id.location);
        viewType = (TextView) findViewById(R.id.viewType);
        price = (TextView) findViewById(R.id.price);

        item_key = getIntent().getExtras().getString("item_key");
        myRef = FirebaseDatabase.getInstance().getReference().child("Item");


        myRef.child(item_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String cardimage=(String)dataSnapshot.child("Image").getValue();
                String Location =(String)dataSnapshot.child("Location").getValue();
                String Viewtype=(String)dataSnapshot.child("ViewType").getValue();
                String Price =(String)dataSnapshot.child("Price").getValue();


                Picasso.with(SingleItemActivity.this).load(cardimage).into(cardImage);
                location.setText(Location);
                viewType.setText(Viewtype);
                price.setText(Price);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
