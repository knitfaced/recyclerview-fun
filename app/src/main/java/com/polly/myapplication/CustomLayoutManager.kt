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
    val recyclerViewHeight = context.resources.getDimensionPixelSize(R.dimen.recyclerview_height)

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
//        Log.d(TAG, "viewWidth = $viewWidth")
//        Log.d(TAG, "viewSpacing = $viewSpacing")

        fill(recycler, state)
    }

    private fun fill(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)

        val viewWidthWithSpacing = viewSpacing + viewWidth
        var firstVisiblePosition = horizontalScrollOffset / viewWidthWithSpacing
        var lastVisiblePosition = (horizontalScrollOffset + screenWidth) / viewWidthWithSpacing
        if (firstVisiblePosition < 0) {
            firstVisiblePosition = 0
        }
        if (lastVisiblePosition >= itemCount) {
            lastVisiblePosition = itemCount -1
        }

        if (horizontalScrollOffset < 0) {
            //fill from left
            val view = recycler.getViewForPosition(itemCount - 1)
            addView(view)
            layoutChildView(-1, viewWidthWithSpacing, view)
        }

        for (i in firstVisiblePosition..lastVisiblePosition) {
            val view = recycler.getViewForPosition(i)
            addView(view)

            layoutChildView(i, viewWidthWithSpacing, view)

//            logBounds(view, "$i")

        }
        recycler.scrapList.forEach {
            recycler.recycleView(it.itemView)
        }
    }

    private fun layoutChildView(i: Int, viewWidthWithSpacing: Int, view: View?) {
        val left = i * viewWidthWithSpacing - horizontalScrollOffset
        val right = left + viewWidth
        val bottom = recyclerViewHeight - (getRadialOffsetForView((left + right) / 2))
        val top = bottom - viewWidth

        measureChildWithMargins(view, viewWidth, viewWidth)

        layoutDecorated(view, left, top, right, bottom)
    }

    private fun getRadialOffsetForView(viewCentreX: Int): Int {
        val xScreenFraction = viewCentreX.toFloat() / screenWidth.toFloat()
        val alpha = (xScreenFraction * Math.PI)
        val yComponent = Math.abs(recyclerViewHeight - viewWidth) * Math.sin(alpha)
        return yComponent.toInt()
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        horizontalScrollOffset += dx
        fill(recycler, state)
        return dx
    }

    private fun logBounds(childView: View, msg: String) {
        Log.d(TAG, "$msg view top ${getDecoratedTop(childView)}")
        Log.d(TAG, "$msg view bottom ${getDecoratedBottom(childView)}")
        Log.d(TAG, "$msg view left ${getDecoratedLeft(childView)}")
        Log.d(TAG, "$msg view right ${getDecoratedRight(childView)}")
    }
}