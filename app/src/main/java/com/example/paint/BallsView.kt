package com.example.paint

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import java.util.*

class BallsView : View {

    var touchX = 0F
    var touchY = 0F
    var listBalls : MutableList<Ball> = arrayListOf()
    var setOnValueChange : ((value : Float)->Unit)? = null
    var counter = 0
    var path : Path = Path()

    constructor(context: Context?) : super(context) {
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr){
        init()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?,
                attrs: AttributeSet?,
                defStyleAttr: Int,
                defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes){
        init()
    }

    fun init(){
    }

    private fun setRandomColor(paint: Paint) {
        paint.color = getRandomColor()
    }

    private fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        var paint = Paint()
        paint.color = Color.YELLOW
        paint.strokeWidth = 5F

        val rect = Rect(10, 10, width-10, height-10)
        paint.style = Paint.Style.STROKE
        canvas?.drawRect(rect, paint)

          // draw balls on click

        /*for (ball in listBalls) {

            paint = ball.paint!!
            canvas?.drawCircle(ball.x?:0F, ball.y?:0F, 10F, paint)
        }*/

           val paint2 = Paint()
           paint2.isAntiAlias
           paint2.color = Color.RED
           paint2.style = Paint.Style.STROKE
           paint2.strokeJoin = Paint.Join.ROUND
           paint2.strokeWidth = 8F
           canvas?.drawPath(path, paint2)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val x = event?.x
        val y = event?.y
        val paint = Paint()

        when (event?.action) {

            MotionEvent.ACTION_DOWN -> {

                touchX = x?:0F
                touchY = y?:0F
                paint.style = Paint.Style.FILL
                setRandomColor(paint)
                val ball = Ball(touchX, touchY, paint)

                listBalls.add(ball)
                path.moveTo(touchX, touchY)
                invalidate()

                setOnValueChange?.let {
                    counter ++
                    it.invoke(counter.toFloat())
                }
            }
            MotionEvent.ACTION_MOVE -> {

                path.lineTo(x?:0F, y?:0F)
                invalidate()
            }
        }
        return true
    }
}

class Ball(var x: Float?, var y: Float?, var paint : Paint?)
