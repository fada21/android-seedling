package com.fada21.android.seedling

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.system.measureTimeMillis

class DebugImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    var onDrawTime: Long = 0
    var onUpdateMeasure: (Long) -> Unit = {}

    override fun onDraw(canvas: Canvas?) {
        onDrawTime = measureTimeMillis {
            super.onDraw(canvas)
        }
        onUpdateMeasure(onDrawTime)
    }

}
