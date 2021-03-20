package com.bignerdranch.android.simpleinstagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bignerdranch.android.simpleinstagram.Post;
import com.bignerdranch.android.simpleinstagram.PostAdapter;
import com.bignerdranch.android.simpleinstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class PostsFragment extends Fragment {

    public static final String TAG ="POSTFRAGMENT";
    private RecyclerView rvPost;
    protected PostAdapter adapter;
    protected List<Post> allPost;
    private ImageView ivCamera;
    SwipeRefreshLayout swipeContainter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostsFragment newInstance(String param1, String param2) {
        PostsFragment fragment = new PostsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPost = view.findViewById(R.id.rvPost);


        swipeContainter = view.findViewById(R.id.swipeContainer);
        swipeContainter.setOnRefreshListener(() -> {
            Log.i(TAG, "fetching Data");
            queryPost();
        });
        swipeContainter.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        allPost = new ArrayList<>();
        adapter = new PostAdapter(getContext(), allPost);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvPost.setLayoutManager(layoutManager);
        rvPost.setAdapter(adapter);


        queryPost();

    }

    protected void queryPost() {

        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground((posts, e) -> {
            if(e != null){
                Log.e(TAG, "Issue with getting posts", e);
                return;
            }
            for(Post post: posts){
                Log.i(TAG, "Post: " + post.getDescription() + " USERNAME: " + post.getUser().getUsername());
            }
            adapter.clear();
            adapter.addAll(posts);
            swipeContainter.setRefreshing(false);

        });
    }
}