package com.polly.myapplication

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.util.DisplayMetrics
import android.util.Log
import android.view.View


/**
 * Created by polly on 01/10/17.
 */
class CustomLayoutManager(val context: Context) : RecyclerView.LayoutManager() {

    private val TAG = "CustomLayoutManager"
    var horizontalScrollOffset = 0

    val viewWidth = dipToPixels(context, 120)
    val viewSpacing = dipToPixels(context, 0)

    fun dipToPixels(context: Context, dipValue: Int): Int{
        val displayMetrics = context.getResources().getDisplayMetrics()
        val floatPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue.toFloat(), displayMetrics)
        return floatPx.toInt()
    }

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

        //todo find first visible position
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
            val top = viewSpacing
            val bottom = top + viewWidth

            measureChildWithMargins(view, viewWidth, viewWidth)

            layoutDecorated(view, left, top, right, bottom)

//            logBounds(view, "$i")
        }
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