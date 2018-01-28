package com.ofx.andtool.img;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;


import com.ofx.andtool.Logg;

/**
 * Created by Ofx on 2018/1/6.
 */

public class ZImageView extends android.support.v7.widget.AppCompatImageView {

    private PointF lastPt = new PointF();
    private Matrix mMatrix;
    private float mMaxScale = 2f;
    /**
     * 0 down, 1 up, 2 move, 3 point down ,4 point up
     */
    private int currentMode = -1;

    long lastUpTime = 0;
    long lastDownTime = 0;
    float lastMoveValueX = 0f;
    float lastMoveValueY = 0f;
    float originPointDis = 0f;
    int lastMotionType = -1;

    public ZImageView(Context context) {
        super(context);
        initView();
    }

    public ZImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ZImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        super.setScaleType(ScaleType.MATRIX);
        mMatrix = new Matrix();
        setClickable(true);
//        PhotoViewAttacher;
//        PhotoView
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.concat(mMatrix);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logg.i("onTouchEvent ACTION_DOWN");
                lastPt.x = event.getX();
                lastPt.y = event.getY();
                currentMode = 1;
                if (System.currentTimeMillis() - lastDownTime < 500) {
//                    matrix.postScale(10f,10f)
                    float scaleNum = getMatrixValue(0);

                    if (scaleNum < mMaxScale) {
                        Logg.i("before " + mMatrix);
                        mMatrix.postScale(scaleNum + 0.2f, scaleNum + 0.2f);
                        Logg.i("after " + mMatrix);
                    } else {
                        if (getMatrixValue(0) == 1){
                            mMatrix.setScale(1f, 1f);
                        }
                    }
//                    setImageMatrix();
                    //move to center

                }
                lastDownTime = System.currentTimeMillis();
                break;

            case MotionEvent.ACTION_UP:
                currentMode = 2;
                originPointDis = 0;
                Logg.i("onTouchEvent ACTION_UP");
                lastUpTime = System.currentTimeMillis();
                break;

            case MotionEvent.ACTION_MOVE:
                currentMode = 3;
                Logg.i("onTouchEvent ACTION_MOVE " + event.getPointerCount());
                if (event.getPointerCount() == 1) {
                    float value = getMatrixValue(1);
                    float offsetX = 0;
                    float offsetY = 0;

                    if (getMatrixValue(0) == 0){

                    }

                    if ((0f != lastMoveValueX || 0f != lastMoveValueY) && lastMotionType == MotionEvent.ACTION_MOVE) {
                        float left_limit = (getWidth() * getMatrixValue(0) - getWidth());
                        float height_limit = (getHeight() * getMatrixValue(0) - getHeight());

                        float tempOffsetX = event.getX() - (lastMoveValueX);
                        float tempOffsetY = event.getY() - (lastMoveValueY);

                        if (tempOffsetY > 0) {
                            if (getMatrixValue(5) + tempOffsetY <= 0) {
                                offsetY = tempOffsetY;
//                            mMatrix.postTranslate(offsetX,offsetY);
                            }
                        } else {
                            if (Math.abs(getMatrixValue(5) + tempOffsetY) < height_limit) {
                                offsetY = tempOffsetY;
                            }
                        }

                        if (tempOffsetX > 0) {
                            if (getMatrixValue(2) + tempOffsetX <= 0) {
                                offsetX = tempOffsetX;
//                            mMatrix.postTranslate(offsetX,offsetY);
                            }
                        } else {
                            if (Math.abs(getMatrixValue(2) + tempOffsetX) < left_limit) {
                                offsetX = tempOffsetX;
                            }
                        }
                        mMatrix.postTranslate(offsetX, offsetY);

                    }
                } else if (event.getPointerCount() == 2) {
                    float diffX = event.getX(0) - event.getX(1);
                    float diffY = event.getY(0) - event.getY(1);
                    float diff = (float) Math.sqrt(diffX * diffX + diffY * diffY);
                    if (originPointDis == 0) {
                        originPointDis = diff;
                    }
                    float scale = Math.abs(diff - originPointDis) / originPointDis;
                    mMatrix.postScale(scale, scale);

                    Logg.i("point diff " + diff + " originPointDis " + originPointDis + " scale " + scale + " mMatrix " + mMatrix);
                }

                lastMoveValueY = event.getY();
                lastMoveValueX = event.getX();
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                Logg.i("onTouchEvent ACTION_POINTER_DOWN");
                if (event.getPointerCount() == 2) {
                    float diffX = event.getX(0) - event.getX(1);
                    float diffY = event.getY(0) - event.getY(1);
                    originPointDis = (float) Math.sqrt(diffX * diffX + diffY * diffY);
                    Logg.i("point diff " + originPointDis);
                }
                break;

            case MotionEvent.ACTION_POINTER_UP:
                originPointDis = 0;
                Logg.i("onTouchEvent ACTION_POINTER_UP");
                break;

        }
        invalidate();
        lastMotionType = event.getAction();
        return super.onTouchEvent(event);
    }

    /**
     * 0 scale 1 transtion 2
     */
    private float getMatrixValue(int type) {
        float[] valus = new float[9];
        mMatrix.getValues(valus);

//        setScaleType();
        return valus[type];
    }

    public void performMatrx(){
        float[] valus = new float[9];
        mMatrix.getValues(valus);
        float scaleX = valus[Matrix.MSCALE_X];
        float scaleY = valus[Matrix.MSCALE_Y];
        float transX = valus[Matrix.MTRANS_X];
        float transY = valus[Matrix.MTRANS_Y];



    }
}
