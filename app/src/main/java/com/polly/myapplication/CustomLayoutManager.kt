package com.polly.myapplication

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View


/**
 * Created by polly on 01/10/17.
 */
class CustomLayoutManager(val context: Context, val screenWidth: Int) : RecyclerView.LayoutManager() {

    private val TAG = "CustomLayoutManager"
    var horizontalScrollOffset = 0

    val viewWidth = context.resources.getDimensionPixelSize(R.dimen.item_width)
    val viewSpacing = context.resources.getDimensionPixelSize(R.dimen.item_spacing)
    val recyclerViewHeight = (context.resources.getDimensionPixelSize(R.dimen.recyclerview_height)).toDouble()

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        fill(recycler, state)
    }

    private fun fill(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)

        val viewWidthWithSpacing = viewSpacing + viewWidth
        var firstVisiblePosition = Math.floor(horizontalScrollOffset.toDouble() / viewWidthWithSpacing.toDouble()).toInt()
        var lastVisiblePosition = (horizontalScrollOffset + screenWidth) / viewWidthWithSpacing

        for (index in firstVisiblePosition..lastVisiblePosition) {
            var recyclerIndex = index % itemCount
            if (recyclerIndex < 0) {
                recyclerIndex += itemCount
            }
            val view = recycler.getViewForPosition(recyclerIndex)
            addView(view)

            layoutChildView(index, viewWidthWithSpacing, view)
        }
        recycler.scrapList.forEach {
            recycler.recycleView(it.itemView)
        }
    }

    private fun layoutChildView(i: Int, viewWidthWithSpacing: Int, view: View) {
        val left = i * viewWidthWithSpacing - horizontalScrollOffset
        val right = left + viewWidth
        val bottom = getRadialOffsetForView((left + right) / 2)
        val top = bottom - viewWidth

        measureChildWithMargins(view, viewWidth, viewWidth)

        layoutDecorated(view, left, top, right, bottom)
    }

    private fun getRadialOffsetForView(viewCentreX: Int): Int {
        val s: Double = screenWidth.toDouble() / 2
        val h = recyclerViewHeight - viewWidth.toDouble()
        val radius: Double = ((h * h) + (s * s)) / (h * 2)

        val xScreenFraction = viewCentreX.toDouble() / screenWidth.toDouble()
        val beta = Math.acos(s / radius)

        val alpha = beta + (xScreenFraction * (Math.PI - (2 * beta)))
        val yComponent = radius - (radius * Math.sin(alpha))
        return yComponent.toInt() + viewWidth
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        horizontalScrollOffset += dx
        fill(recycler, state)
        return dx
    }
}