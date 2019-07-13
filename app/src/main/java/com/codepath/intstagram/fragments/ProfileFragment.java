package com.codepath.intstagram.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.codepath.intstagram.Post;
import com.codepath.intstagram.PostsAdapter;
import com.codepath.intstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends PostsFragment {

    private final String TAG = "ProfileFragment";
    boolean isPostFragment = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceStat) {
        rvPosts = view.findViewById(R.id.rvPosts);
        mPosts = new ArrayList<>();
        adapter = new PostsAdapter(getActivity(), mPosts, isPostFragment);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                queryPosts();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        queryPosts();
    }

    @Override
    protected void queryPosts() {
        super.queryPosts();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
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
                adapter.clear();
                // ...the data has come back, add new items to your adapter...
                adapter.addAll(mPosts);
            }
        });
    }

}
