package com.bignerdranch.android.simpleinstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bignerdranch.android.simpleinstagram.fragments.ComposeFragment;
import com.bignerdranch.android.simpleinstagram.fragments.PostsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;
    private ImageView ivCamera;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivCamera = findViewById(R.id.ivLogOut);
        getSupportActionBar().hide();

        ivCamera.setOnClickListener(v -> {
            ParseUser.logOut();
            ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
            Intent i = new Intent(MainActivity.this, activity_login.class);
            startActivity(i);
            finish();
        });

        //tvUserName.setText((CharSequence) currentUser);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        //etDescription.setVisibility(View.GONE);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.action_home:
                    ivCamera.setVisibility(View.GONE);
                    fragment = new PostsFragment();
                    break;
                case R.id.action_compose:
                    Toast.makeText(MainActivity.this, "Compose!", Toast.LENGTH_SHORT).show();
                    ivCamera.setVisibility(View.GONE);

                    fragment = new ComposeFragment();
                    break;
                case R.id.action_profile:
                    Toast.makeText(MainActivity.this, "Profile!", Toast.LENGTH_SHORT).show();
                    ivCamera.setVisibility(View.VISIBLE);
                    fragment = new ProfileFragment();
                    break;
                default:
                    fragment = new ProfileFragment();
                    break;
            }
            fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
            return  true;
        });
        bottomNavigationView.setSelectedItemId(R.id.action_home);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}