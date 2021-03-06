package com.comfortfeeling.frontend;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.bumptech.glide.Glide;
import com.comfortfeeling.frontend.callback.SessionCallback;
import com.comfortfeeling.frontend.common.ProfileData;
import com.comfortfeeling.frontend.http.CommonMethod;
import com.comfortfeeling.frontend.ui.completion.CompletionFragment;
import com.comfortfeeling.frontend.ui.completion.OtherCompletionFragment;
import com.comfortfeeling.frontend.ui.depressionTest.DepressionTestFragment;
import com.comfortfeeling.frontend.ui.depressionTest.TestResultFragment;
import com.comfortfeeling.frontend.ui.history.DetailFragment;
import com.comfortfeeling.frontend.ui.main.HomeFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.comfortfeeling.frontend.databinding.ActivityMainBinding;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private static final String LOG_TAG = "MainActivity";
    private String comfortMsg = "";

    // menu item initialization
    private SessionCallback sessionCallback = new SessionCallback();
    private static int flag = 0;
    CompletionFragment completionFragment;
    OtherCompletionFragment otherCompletionFragment;
    DepressionTestFragment depressionTestFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_completion, R.id.nav_history, R.id.nav_graph, R.id.nav_test)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        /**
         * Drawer ??? Navigation Setting ??? ??????
         */
        View headerView = navigationView.getHeaderView(0);
//        TextView userId = headerView.findViewById(R.id.userId);
//        userId.setText(ProfileData.getUserId());
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
        otherCompletionFragment = new OtherCompletionFragment();
        depressionTestFragment = new DepressionTestFragment();
        if(flag == 0) {
            comfortMsg = getComfortMsg();
            LayoutInflater inflater = this.getLayoutInflater();
            View titleView = inflater.inflate(R.layout.custom_title, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this).setCustomTitle(titleView);
            builder.setMessage(comfortMsg);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            flag++;
        }
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
            case R.id.empathy:
                this.startActivity(new Intent(this,SplashActivity.class));
                Toast.makeText(getApplicationContext(), "????????? ????????? ????????????", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        getSupportFragmentManager().popBackStack("completefrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().popBackStack("ocompletefrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().popBackStack("detailfrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().popBackStack("depressiontestfrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().popBackStack("testresultfrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //??????????????? ?????????
    public void onFragmentChange(int index, int tag){
        CompletionFragment completionFragment = new CompletionFragment();
        OtherCompletionFragment ocompletionFragment = new OtherCompletionFragment();
        DepressionTestFragment depressionTestFragment = new DepressionTestFragment();
        TestResultFragment testResultFragment = new TestResultFragment();

        Bundle bundle = new Bundle(1);
        bundle.putInt("key", tag);
        completionFragment.setArguments(bundle);
        ocompletionFragment.setArguments(bundle);
        depressionTestFragment.setArguments(bundle);
        testResultFragment.setArguments(bundle);

        if(index == 1){
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.nav_host_fragment_content_main , completionFragment).addToBackStack("completefrag").commit();
        }
        else if(index == 2){
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.nav_host_fragment_content_main , ocompletionFragment).addToBackStack("ocompletefrag").commit();
        }
        else if(index == 3){
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.nav_host_fragment_content_main , depressionTestFragment).addToBackStack("depressiontestfrag").commit();
        }
        else if(index == 4){
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.test_fragment , testResultFragment).addToBackStack("testresultfrag").commit();
        }


        //????????? ?????? ?????????????????? ???????????? ?????? ????????????
    }

    //??????????????? ????????? String Data ???
    public void onFragmentChange(int index, String tag){
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString("seq", tag);
        detailFragment.setArguments(bundle);

        if(index == 3){
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.nav_host_fragment_content_main , detailFragment).addToBackStack("detailfrag").commit();
        }


        //????????? ?????? ?????????????????? ???????????? ?????? ????????????
    }

    //?????? ??? ??????
    public String getComfortMsg(){
        String rtnStr="";
        String url = CommonMethod.ipConfig +"/api/message";


            RequestHttpURLConnection.NetworkAsyncTask networkTask = new RequestHttpURLConnection.NetworkAsyncTask(url, "");
        try {
            rtnStr = networkTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return rtnStr;
    }

    private long lastTimeBackPressed;

    public interface onKeyBackPressedListener {
        void onBackKey();
    }
    private onKeyBackPressedListener mOnKeyBackPressedListener;

    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener) {
        mOnKeyBackPressedListener = listener;
    } //???????????? ???????????? ????????? ??????????????? ?????? ?????? ????????????


    @Override public void onBackPressed() {
        if (mOnKeyBackPressedListener != null) {
            mOnKeyBackPressedListener.onBackKey();
        } else {
            //?????? BackStack ????????? ?????? Toast??? ?????????, ????????????
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                //* ?????? EndToast Bean ??????
                //??? ??? ????????? ?????? ??????
                if(System.currentTimeMillis() - lastTimeBackPressed < 1500){
                    finish();
                    return;
                }
                lastTimeBackPressed = System.currentTimeMillis();
                Toast.makeText(this,"'??????' ????????? ??? ??? ??? ????????? ???????????????.",Toast.LENGTH_SHORT).show();

                } else {
                    super.onBackPressed();
               }
        }

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
        flag = 0;
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }
}
