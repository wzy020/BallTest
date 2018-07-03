package com.test.wzy.balltest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Ball {

    private float reduceFloat; //递减系数
    private int stepReduce; //当前碰撞缩短的距离
    private float mReduceHeight; //总共缩短的距离

    private float runX;//球的X坐标
    private float runY;//球的Y坐标
    private float ballSize;//球的直径
    private BallSurfaceView bsv;
    private boolean upDirection = false; //t 向上 ; f 向下
    private Paint paint;

    private float Xv, Yv; // X,Y 轴上的速度
    private float mG = 8.0f; // 重力加速度

    Bitmap ballBitmap;

    public boolean stop = false;

    private int ScreenWidth, ScreenHeight;


    public Ball(float initX , float initY , BallSurfaceView bsv){
        reSet(initX, initY);

        ballBitmap = getBallBitmap(50,50, bsv.mMainActivity);
        ballSize = ballBitmap.getWidth();

        paint = new Paint();
        ScreenWidth = bsv.mMainActivity.screenWidth;
        ScreenHeight = bsv.mMainActivity.screenHeight;
    }

    public void onDraw(Canvas canvas) {
        int c = paint.getColor();//保存颜色，之后还原为之前颜色
        boundaryTest();
        if(canvas != null) canvas.drawBitmap(ballBitmap,runX,runY,paint);
        paint.setColor(c);
        move();
    }

    //移动小球
    private void move() {
        if(mReduceHeight >= (ScreenWidth - ballSize)) {
            runX = ScreenWidth - ballSize;
            stop = true;
            return;
        }

        if (runY >= (ScreenHeight - ballSize)) {
            reduceFloat = 1.0f;
            runY = ScreenHeight - ballSize;
            Yv = 0;
        }

        runY = runY + Yv;

        if(upDirection){//向上
            runX = runX - (Xv - mG/2);
            Xv = Xv - mG;
        }else{
            runX = runX + (Xv + mG/2);
            Xv = Xv + mG;
        }
    }

    private void boundaryTest(){

        if(runX > ScreenWidth - ballSize){//向下运动到头
            upDirection = !upDirection;//方向置反
            runX = ScreenWidth - ballSize;
            stepReduce = (int) ((ScreenWidth - mReduceHeight) * reduceFloat);
            mReduceHeight = mReduceHeight + stepReduce;

        }
        if(runX < mReduceHeight){//向上运动到头
            upDirection = !upDirection;//方向置反
            if(mReduceHeight >= (ScreenWidth - ballSize)) return;
            runX = mReduceHeight;
        }
    }

    public void reSet(float initX , float initY) {
        this.runX  = initX;
        this.runY = initY;
        mReduceHeight = initY;
        Xv = 0;
        Yv = 100;
        stop = false;
        reduceFloat = 0.25f;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        while (width / inSampleSize > reqWidth) {
            inSampleSize++;
        }
        while (height / inSampleSize > reqHeight) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    private Bitmap getBallBitmap(int width, int height, Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_round, options);
        options.inSampleSize = calculateInSampleSize(options, width, height);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_round, options);
    }

}


