package com.codepath.intstagram.fragments;

import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

import com.codepath.intstagram.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends PostsFragment {

    private final String TAG = "ProfileFragment";
    @Override
    protected void queryPosts() {
        super.queryPosts();
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(getContext(), 3);
// Attach the layout manager to the recycler view
        rvPosts.setLayoutManager(gridLayoutManager);

        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // Specify the object id
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }

                mPosts.addAll(posts);
                adapter.notifyDataSetChanged();

                for (int i = 0; i < posts.size(); i++) {
                    Post post = posts.get(i);
                    Log.d(TAG, "Post:" + post.getDescription()
                            + "Username" + post.getUser().getUsername());
                }
            }
        });
    }

}
