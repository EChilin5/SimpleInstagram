package com.bignerdranch.android.simpleinstagram;

import android.util.Log;
import android.widget.Toast;

import com.bignerdranch.android.simpleinstagram.fragments.PostsFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends PostsFragment {

    public static final String TAG = "ProfileImage";

    @Override
    protected void queryPost() {
        super.queryPost();
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground((posts, e) -> {

            Toast.makeText(getContext(), ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_SHORT ).show();
            if(e != null){
                Log.e(TAG, "Issue with getting posts", e);
                return;
            }
            for(Post post: posts){
                Log.i(TAG, "Post: " + post.getDescription() + " USERNAME: " + post.getUser().getUsername());
            }
            allPost.clear();
            allPost.addAll(posts);
            adapter.notifyDataSetChanged();

        });
    }

}
