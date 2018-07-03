package com.test.wzy.balltest;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    public int screenWidth ;
    public int screenHeight ;
    BallSurfaceView bsv ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics dm = this.getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        bsv = new BallSurfaceView(this);

        //设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(bsv);

        bsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bsv.reSetBall();
            }
        });

    }


}
