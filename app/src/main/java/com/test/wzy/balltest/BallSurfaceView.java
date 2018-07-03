package com.test.wzy.balltest;

import android.view.SurfaceHolder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

public class BallSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    MainActivity mMainActivity;
    private Ball ball;
    SurfaceHolder holder;

    public BallSurfaceView(Context context) {
        super(context);
        this.mMainActivity = (MainActivity)context;
        ball = new Ball(20, 20, this);
        holder = this.getHolder();
        holder.addCallback(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(canvas == null) canvas = holder.lockCanvas();//锁定画布
        Paint p = new Paint();
        int c = p.getColor();
        p.setColor(Color.GRAY);//设置背景白色
        if(canvas != null)
            canvas.drawRect(0, 0, mMainActivity.screenWidth, mMainActivity.screenHeight, p);
        p.setColor(c);
        ball.onDraw(canvas);
        holder.unlockCanvasAndPost(canvas);//释放锁
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new RefreshThread().start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private class RefreshThread extends Thread{

        @Override
        public void run() {
            while(!ball.stop){
                Canvas canvas = null;
                try{
                    draw(canvas);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void reSetBall() {
        if (!ball.stop) return;
        ball.stop = true;
        ball.reSet(20, 20);
        new RefreshThread().start();
    }

}
