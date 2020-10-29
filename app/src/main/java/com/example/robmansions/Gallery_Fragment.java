package com.example.robmansions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Gallery_Fragment extends Fragment {

   private DatabaseReference myRef;
   private RecyclerView postList;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth mAuth ;

   private Context ctx;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.gallery_fragment, container, false);

        mAuth=FirebaseAuth.getInstance();



        myRef= FirebaseDatabase.getInstance().getReference().child("Item");
        postList=view.findViewById(R.id.postsList);
        postList.setLayoutManager(new GridLayoutManager(ctx,2)) ;
       // postList.setHasFixedSize(true);

        //check if the user logged in or not
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                {
                    Intent loginIntent=new Intent(ctx,LoginActivity.class);
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

        FirebaseRecyclerAdapter<Post,PostListHolder> FBRA=new FirebaseRecyclerAdapter<Post, PostListHolder>(
                Post.class,
                R.layout.post_row,
                PostListHolder.class,
                myRef
        ) {
            @Override
            protected void populateViewHolder(PostListHolder postListHolder, Post post, int i) {

                final   String item_key=getRef(i).getKey().toString();

                postListHolder.setViewType(post.getViewType());
                postListHolder.setLocation(post.getLocation());
                postListHolder.setPrice(post.getPrice());
                postListHolder.setImage(getContext(),post.getImage());

                postListHolder.myView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent singleFoodActivityIntent=new Intent(getContext(),SingleItemActivity.class);
                        singleFoodActivityIntent.putExtra("item_key",item_key);
                        startActivity(singleFoodActivityIntent);
                    }
                });



            }
        };
        postList.setAdapter(FBRA);


    }
    public static class PostListHolder extends RecyclerView.ViewHolder{

        View myView;

        public PostListHolder(@NonNull View itemView) {
            super(itemView);
            myView=itemView;
        }
        public void setViewType(String viewtype)
        {
            TextView ViewType=(TextView)myView.findViewById(R.id.viewType);
            ViewType.setText(viewtype);
        }
        public void setLocation(String location){
            TextView Location=(TextView)myView.findViewById(R.id.location);
            Location.setText(location);
        }
        public void setPrice(String price)
        {
            TextView itemPrice=(TextView)myView.findViewById(R.id.price);
            itemPrice.setText(price);
        }
        public void setImage(Context ctx, String image)
        {
            ImageView HouseImage=(ImageView)myView.findViewById(R.id.cardImage);
            Picasso.with(ctx).load(image).into(HouseImage);
        }

    }
}
