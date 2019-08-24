package com.fada21.android.seedling

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.system.measureNanoTime

// https://upday.github.io/blog/vector_drawables_optimisation/
class DebugImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    var onUpdateMeasure: (Long) -> Unit = {}

    override fun onDraw(canvas: Canvas?) {
        onUpdateMeasure(measureNanoTime {
            super.onDraw(canvas)
        })
    }

}
