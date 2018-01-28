package com.ofx.show.View

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView
import com.github.chrisbanes.photoview.PhotoView
import com.ofx.show.tool.Logg

/**
 * Created by Ofx on 2017/12/14.
 */
class ZImageView(context: Context?, attbut: AttributeSet?) : ImageView(context, attbut) {

    var lastPt: PointF? = null
    var mMatrix: Matrix? = null
    var mMaxScale = 2f
    /**0 down, 1 up, 2 move, 3 point down ,4 point up*/
    var currentMode: Int = -1;

    init {
        scaleType = ScaleType.MATRIX
        mMatrix = Matrix()
//        PhotoView
        isDrawingCacheEnabled = true
        isClickable = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    var lastUpTime: Long? = 0;
    var lastDownTime: Long? = 0;
    var lastMoveValueX: Float? = 0f;
    var lastMoveValueY: Float? = 0f;
    var lastMotionType: Int? = -1;


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if (changed) {
            updateMatrix()
        }
    }

    override fun setFrame(l: Int, t: Int, r: Int, b: Int): Boolean {
        updateMatrix()
        return super.setFrame(l, t, r, b)
    }

    private fun updateMatrix() {
        if (drawable != null) {
            var viewWidth = right - left;
            var height = bottom - top;
            var drawable_width = drawable.intrinsicWidth;
            var drawable_height = drawable.intrinsicHeight
            mMatrix!!.setRectToRect(RectF(0f, 0f, drawable_width.toFloat(), drawable_height.toFloat()), RectF(0f, 0f, viewWidth.toFloat(), height.toFloat()), Matrix.ScaleToFit.CENTER)
            imageMatrix = mMatrix
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Logg.i("onTouchEvent " + event?.action)
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                Logg.i("onTouchEvent ACTION_DOWN")
                lastPt?.x = event.x
                lastPt?.y = event.y
                currentMode = 1
                if (System.currentTimeMillis() - lastDownTime!! < 500) {
//                    matrix.postScale(10f,10f)
                    var scaleNum = getScaleNum()

                    if (scaleNum < mMaxScale) {
                        var scale = (scaleNum + 0.2f)/scaleNum
                        Logg.i("before " + mMatrix+"  scale "+scale)
                        mMatrix?.postScale(scale, scale)
                        var rect:Rect = Rect()
                        getDrawingRect(rect)
                        Logg.i("getDrawingRect "+rect)
                        getGlobalVisibleRect(rect)
                        Logg.i("getGlobalVisibleRect "+rect)
                        Logg.i("after " + mMatrix)
                    } else {
                        mMatrix?.setScale(1f, 1f)
                    }
                    imageMatrix = mMatrix
                    //move to center
                    Logg.i("widht " + width + " img width " + getDrawingCache(true).width)

                }
                Logg.i("onTouchEvent scale" + mMatrix + " time diff " + (System.currentTimeMillis() - lastDownTime!!))
                lastDownTime = System.currentTimeMillis()
            }
            MotionEvent.ACTION_UP -> {
                currentMode = 2
                Logg.i("onTouchEvent ACTION_UP")
                var now = System.currentTimeMillis()
//                if (now - lastUpTime!! < 1000){
//                    imageMatrix.postScale(10f,10f)
//                }
                lastUpTime = System.currentTimeMillis();

            }
            MotionEvent.ACTION_MOVE -> {
                currentMode = 3
                //

                Logg.i("onTouchEvent ACTION_MOVE")
                var value = getMatrixValue(1)
                var offsetX = event.x
                var offsetY = event.y
                if ((0f != lastMoveValueX || 0f != lastMoveValueY) && lastMotionType == MotionEvent.ACTION_MOVE) {
                    offsetY = event.y.minus(lastMoveValueY!!)
                    var left_limit = (width * getMatrixValue(0) - width)
                    offsetX = event.x.minus(lastMoveValueX!!)
                    // translate to right litmit the boader
                    if (offsetX > 0) {
                        if (getMatrixValue(2) + offsetX <= 0) {
                            mMatrix?.postTranslate(offsetX, offsetY)
                        }
                    } else {
                        if (Math.abs(getMatrixValue(2) + offsetX) < left_limit) {
                            mMatrix?.postTranslate(offsetX, offsetY)
                        }
                    }

                    imageMatrix = mMatrix

                    Logg.i("onTouchEvent setTranslate" + mMatrix + " left_limit " + left_limit + " offsetX " + offsetX)
                }
                lastMoveValueY = event.y
                lastMoveValueX = event.x

            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                Logg.i("onTouchEvent ACTION_POINTER_DOWN")
                currentMode = 4
            }
            MotionEvent.ACTION_POINTER_UP -> {
                Logg.i("onTouchEvent ACTION_POINTER_UP ")
                currentMode = 5
            }
        }

        lastMotionType = event?.action
        return super.onTouchEvent(event)
    }

    private fun getScaleNum(): Float {
        var valus = FloatArray(9)
        mMatrix?.getValues(valus)
        return valus[0]
    }

    /** 0 scale 1 transtion 2*/
    private fun getMatrixValue(type: Int): Float {
        var valus = FloatArray(9)
        mMatrix?.getValues(valus)
        return valus[type]
    }

    fun drag() {

    }
}