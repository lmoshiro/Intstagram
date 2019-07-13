package com.codepath.intstagram;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private TextView tvHandle;
    private ImageView ivImage;
    private TextView tvDescription;
    private Context context;
    private TextView tvTimestamp;
    private ImageView ivProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = DetailsActivity.this;
        setContentView(R.layout.detail);
        tvHandle = findViewById(R.id.tvHandle);
        ivImage = findViewById(R.id.ivDetailsImage);
        tvDescription = findViewById(R.id.tvDescription);
        tvTimestamp = findViewById(R.id.tvTimeStamp);
        ivProfile = findViewById(R.id.ivProfile);

        // unwrap the movie passed in via intent, using its simple name as a key
        String postId = getIntent().getStringExtra("Detailed");
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // Specify the object id
        query.include(Post.KEY_USER);
        query.whereEqualTo("objectId", postId);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                Post post = objects.get(0);
                tvHandle.setText(post.getUser().getUsername());
                tvDescription.setText(post.getDescription());
                tvTimestamp.setText(post.getCreatedAt().toString());
                Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
                Glide.with(context).load(ParseUser.getCurrentUser().getParseFile("profile").getUrl()).into(ivProfile);
            }
        });
    }
}
