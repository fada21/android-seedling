package com.fada21.android.seedling

import android.annotation.SuppressLint
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

    var onUpdateMeasure: (
        onDrawDurationNano: Long,
        onDrawDurationAverage: Float
    ) -> Unit = { _, _ -> }

    private var keepRedrawing = false
    private val avgQueue = ArrayList<Long>()
    private val averageNano get() = avgQueue.average()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setOnClickListener { invalidate() }
        setOnLongClickListener {
            keepRedrawing = !keepRedrawing
            invalidate()
            true
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        val nanoTime = measureNanoTime {
            super.onDraw(canvas)
        }
        avgQueue += nanoTime
        onUpdateMeasure(nanoTime, averageNano.toLong() / 1_000L / 1000.0F)
        if (keepRedrawing) invalidate()
    }

}
