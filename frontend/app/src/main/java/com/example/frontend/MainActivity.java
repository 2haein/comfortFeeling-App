package com.example.frontend;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.bumptech.glide.Glide;
import com.example.frontend.callback.SessionCallback;
import com.example.frontend.common.ProfileData;
import com.example.frontend.ui.completion.CompletionFragment;
import com.example.frontend.ui.completion.OtherCompletionFragment;
import com.example.frontend.ui.main.HomeFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.frontend.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private static final String LOG_TAG = "MainActivity";

    // menu item initialization
    private SessionCallback sessionCallback = new SessionCallback();
    public String strUserId;
    CompletionFragment completionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_completion, R.id.nav_history, R.id.nav_graph)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        /**
         * Drawer 및 Navigation Setting 후 호출
         */
        View headerView = navigationView.getHeaderView(0);
        TextView userId = headerView.findViewById(R.id.userId);
        userId.setText(ProfileData.getUserId());
        TextView nickName = headerView.findViewById(R.id.nickName);
        nickName.setText(ProfileData.getNickName());
        ImageView profileImageUrl = headerView.findViewById(R.id.profileImageUrl);
        Glide.with(this)
                .load(ProfileData.getThumbnail())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .fallback(R.mipmap.ic_launcher_round)
                .into(profileImageUrl);

        completionFragment = new CompletionFragment();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Menu item selection
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_logout:
                UserManagement.getInstance()
                        .requestLogout(new LogoutResponseCallback() {
                            @Override
                            public void onSessionClosed(ErrorResult errorResult) {
                                super.onSessionClosed(errorResult);
                                Log.d(LOG_TAG, "onSessionClosed: " + errorResult.getErrorMessage());
                            }
                            @Override
                            public void onCompleteLogout() {
                                if (sessionCallback != null) {
                                    Session.getCurrentSession().removeCallback(sessionCallback);
                                }
                                Log.d(LOG_TAG, "onCompleteLogout:logout ");
                            }
                        });
                Intent i = new Intent(this,LoginActivity.class);
                this.startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        getSupportFragmentManager().popBackStack("completefrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //프래그먼트 이동용
    public void onFragmentChange(int index, int tag){
        CompletionFragment completionFragment = new CompletionFragment();
        OtherCompletionFragment ocompletionFragment = new OtherCompletionFragment();
        Bundle bundle = new Bundle(1);
        bundle.putInt("key", tag);
        completionFragment.setArguments(bundle);
        ocompletionFragment.setArguments(bundle);
        if(index == 1){
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.nav_host_fragment_content_main , completionFragment).addToBackStack("completefrag").commit();
        }
        else if(index == 2){
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.nav_host_fragment_content_main , ocompletionFragment).addToBackStack("ocompletefrag").commit();
        }
        //여기서 다른 프래그먼트로 이동하는 기능 구현가능
    }

     @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }
}