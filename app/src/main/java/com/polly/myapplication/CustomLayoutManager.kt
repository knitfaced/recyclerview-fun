package com.polly.myapplication

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.View


class CustomLayoutManager(context: Context, private val screenWidth: Int) : androidx.recyclerview.widget.RecyclerView.LayoutManager() {

    private val TAG = "CustomLayoutManager"
    private var horizontalScrollOffset = 0

    private val viewWidth = context.resources.getDimensionPixelSize(R.dimen.item_width)
    private val recyclerViewHeight = (context.resources.getDimensionPixelSize(R.dimen.recyclerview_height)).toDouble()

    override fun generateDefaultLayoutParams(): androidx.recyclerview.widget.RecyclerView.LayoutParams {
        return androidx.recyclerview.widget.RecyclerView.LayoutParams(androidx.recyclerview.widget.RecyclerView.LayoutParams.WRAP_CONTENT, androidx.recyclerview.widget.RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: androidx.recyclerview.widget.RecyclerView.Recycler, state: androidx.recyclerview.widget.RecyclerView.State) {
        fill(recycler, state)
    }

    private fun fill(recycler: androidx.recyclerview.widget.RecyclerView.Recycler, state: androidx.recyclerview.widget.RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)

        val firstVisiblePosition = Math.floor(horizontalScrollOffset.toDouble() / viewWidth.toDouble()).toInt()
        val lastVisiblePosition = (horizontalScrollOffset + screenWidth) / viewWidth

        for (index in firstVisiblePosition..lastVisiblePosition) {
            var recyclerIndex = index % itemCount
            if (recyclerIndex < 0) {
                recyclerIndex += itemCount
            }
            val view = recycler.getViewForPosition(recyclerIndex)
            addView(view)

            layoutChildView(index, viewWidth, view)
        }
        val scrapListCopy = recycler.scrapList.toList()
        scrapListCopy.forEach {
            recycler.recycleView(it.itemView)
        }
    }

    private fun layoutChildView(i: Int, viewWidthWithSpacing: Int, view: View) {
        val left = i * viewWidthWithSpacing - horizontalScrollOffset
        val right = left + viewWidth
        val top = getTopOffsetForView(left + viewWidth/2)
        val bottom = top + viewWidth

        measureChild(view, viewWidth, viewWidth)

        layoutDecorated(view, left, top, right, bottom)
    }

    private fun getTopOffsetForView(viewCentreX: Int): Int {
        val s: Double = screenWidth.toDouble() / 2
        val h: Double = recyclerViewHeight - viewWidth.toDouble()
        val radius: Double = ( h*h + s*s ) / (h*2)

        val xScreenFraction = viewCentreX.toDouble() / screenWidth.toDouble()
        val beta = Math.acos(s / radius)

        val alpha = beta + (xScreenFraction * (Math.PI - (2 * beta)))
        val yComponent = radius - (radius * Math.sin(alpha))
        return yComponent.toInt()
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: androidx.recyclerview.widget.RecyclerView.Recycler, state: androidx.recyclerview.widget.RecyclerView.State): Int {
        horizontalScrollOffset += dx
        fill(recycler, state)
        return dx
    }
}