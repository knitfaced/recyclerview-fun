package com.polly.myapplication

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.R.attr.y
import android.R.attr.x
import android.view.Display




/**
 * Created by polly on 01/10/17.
 */
class CustomLayoutManager(val context: Context, val screenWidth: Int) : RecyclerView.LayoutManager() {

    private val TAG = "CustomLayoutManager"
    var horizontalScrollOffset = 0

    val viewWidth = context.resources.getDimensionPixelSize(R.dimen.item_width)
    val viewSpacing = context.resources.getDimensionPixelSize(R.dimen.item_spacing)

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

        var firstVisiblePosition = horizontalScrollOffset / (viewSpacing + viewWidth)
        var lastVisiblePosition = firstVisiblePosition + 3
        if (firstVisiblePosition < 0) {
            firstVisiblePosition = 0
        }
        if (lastVisiblePosition >= itemCount) {
            lastVisiblePosition = itemCount -1
        }

        for (i in firstVisiblePosition..lastVisiblePosition) {
            val view = recycler.getViewForPosition(i)
            addView(view)

            val left = i * (viewSpacing + viewWidth) - horizontalScrollOffset
            val right = left + viewWidth
            val top = viewSpacing + getTopOffsetForView((left + right) /2)
            val bottom = top + viewWidth

            measureChildWithMargins(view, viewWidth, viewWidth)

            layoutDecorated(view, left, top, right, bottom)

//            logBounds(view, "$i")
        }
    }

    private fun getTopOffsetForView(viewCentreX: Int): Int {
        return Math.abs(screenWidth/2 - viewCentreX)
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