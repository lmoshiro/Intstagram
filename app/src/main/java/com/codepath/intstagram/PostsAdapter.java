package com.codepath.intstagram;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.intstagram.fragments.ProfileFragment;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

public class PostsAdapter extends  RecyclerView.Adapter<PostsAdapter.ViewHolder>{
    private FragmentActivity activity;
    private List<Post> posts;
    private boolean isPostFragment;

    public PostsAdapter(FragmentActivity context, List<Post> posts, boolean isPostFragment) {
        this.activity = context;
        this.posts = posts;
        this.isPostFragment = isPostFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_post, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvHandle;
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvTimestamp;
        ImageView ivProfile;
        private ImageView ivLike;
        private ImageView ivComment;
        private ImageView ivMessage;
        private ImageView ivBookmark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHandle = itemView.findViewById(R.id.tvHandle);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvTimestamp = itemView.findViewById(R.id.tvTimeStamp);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            ivLike = itemView.findViewById(R.id.ivLike);
            ivComment = itemView.findViewById(R.id.ivComment);
            ivMessage = itemView.findViewById(R.id.ivMessage);
            ivBookmark = itemView.findViewById(R.id.ivBookmark);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // gets item position
                    int position = getAdapterPosition();
                    // make sure the position is valid, i.e. actually exists in the view
                    if (position != RecyclerView.NO_POSITION) {
                        // get the movie at the position, this won't work if the class is static
                        Post post = posts.get(position);
                        // create intent for the new activity
                        Intent intent = new Intent(activity, DetailsActivity.class);
                        // serialize the movie using parceler, use its short name as a key
                        intent.putExtra("Detailed", post.getObjectId());
                        // show the activity
                        activity.startActivity(intent);
                    }
                }
            });

            ivProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new ProfileFragment();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.flContainer, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }

        public void bind(Post post) {

            if(!(isPostFragment)) {
                tvTimestamp.setVisibility(View.GONE);
                tvHandle.setVisibility(View.GONE);
                tvDescription.setVisibility(View.GONE);
                ivProfile.setVisibility(View.GONE);
                ivLike.setVisibility(View.GONE);
                ivMessage.setVisibility(View.GONE);
                ivComment.setVisibility(View.GONE);
                ivBookmark.setVisibility(View.GONE);
            } else {
                tvHandle.setText(post.getUser().getUsername());
                Glide.with(activity).load(ParseUser.getCurrentUser().getParseFile("profile").getUrl()).into(ivProfile);
                tvDescription.setText(post.getDescription());
                tvTimestamp.setText(post.getCreatedAt().toString());
            }

            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(activity)
                    .load(image.getUrl())
                    .override(350, 300)
                     .into(ivImage);

            }



        }

    }
}
