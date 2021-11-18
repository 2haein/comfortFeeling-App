package com.comfortfeeling.frontend;

import androidx.constraintlayout.widget.ConstraintLayout;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends Activity {
    Animation anim_FadeIn;
    Animation anim_ball;
    ConstraintLayout constraintLayout;
    ImageView lcklockImageView;
    ImageView oImageView;
    ImageView faceRecgnitionImageView;

    MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        constraintLayout=findViewById(R.id.constraintLayout);
        lcklockImageView=findViewById(R.id.lock_lck);
        oImageView=findViewById(R.id.lock_o);

        mp = MediaPlayer.create(
                getApplicationContext(), // 현재 화면의 제어권자
                R.raw.sound); // 음악파일
        mp.setLooping(false); // true:무한반복
        mp.start(); // 노래 재생 시작

        anim_FadeIn=AnimationUtils.loadAnimation(this,R.anim.anim_splash_fade_in);
        anim_ball=AnimationUtils.loadAnimation(this,R.anim.anim_splash_ball);


        anim_FadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                if (mp!=null) {
                    mp.release(); // 자원해제
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        lcklockImageView.startAnimation(anim_FadeIn);
        oImageView.startAnimation(anim_ball);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp!=null) {
            mp.release(); // 자원해제
        }
    }
}