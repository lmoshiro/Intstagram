package com.codepath.intstagram;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.intstagram.fragments.ComposeFragment;
import com.codepath.intstagram.fragments.PostsFragment;
import com.codepath.intstagram.fragments.ProfileFragment;
import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miCompose:
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_home);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        bottomNavigationView = findViewById(R.id.bnv);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = new Fragment();
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        fragment = new PostsFragment();
//                        Toast.makeText(HomeActivity.this, "Home!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_search:
                        // TODO: swap fragment here
//                        Toast.makeText(HomeActivity.this, "Search!", Toast.LENGTH_SHORT).show();
                        fragment = new ComposeFragment();
                        break;
                    case R.id.navigation_add:
                        // TODO: swap fragment here
                        fragment = new ComposeFragment();
//                        Toast.makeText(HomeActivity.this, "Add!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_notifications:
                        // TODO: swap fragment here
                        fragment = new ComposeFragment();
//                        Toast.makeText(HomeActivity.this, "Notifs!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_profile:
                        default:
                        fragment = new ProfileFragment();
//                        Toast.makeText(HomeActivity.this, "Profile!", Toast.LENGTH_SHORT).show();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }

        });
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }
}
