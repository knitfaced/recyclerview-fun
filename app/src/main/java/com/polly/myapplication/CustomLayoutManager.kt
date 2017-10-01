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

    fun dipToPixels(context: Context, dipValue: Int): Int{
        val displayMetrics = context.getResources().getDisplayMetrics()
        val floatPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue.toFloat(), displayMetrics)
        return floatPx.toInt()
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        fill(recycler, state)

    }

    private fun fill(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
//        detachAndScrapAttachedViews(recycler)
//
//        Log.d(TAG, "itemCount $itemCount")
//
//        val childView = recycler.getViewForPosition(0)
//
//        logBounds(childView)
//
//        Log.d(TAG, "itemCount $itemCount")
//        addView(childView)
//
//        logBounds(childView)
//
//        measureChild(childView, 300, 300)
//
//        logBounds(childView)
//
//        layoutDecorated(childView, 200, 200, 500, 500)
//
//        logBounds(childView)

        for (i in 0..3) {
            val view = recycler.getViewForPosition(i)
            addView(view)

            val viewWidth = dipToPixels(context, 120)
            val viewSpacing = dipToPixels(context, 0)

            Log.d(TAG, "viewWidth = $viewWidth")
            Log.d(TAG, "viewSpacing = $viewSpacing")
            val left = i * (viewSpacing + viewWidth)
            val right = left + viewWidth
            val top = viewSpacing
            val bottom = top + viewWidth


            measureChild(view, viewWidth, viewWidth)

            layoutDecorated(view, left, top, right, bottom)

            logBounds(view, "$i")
        }
    }

    private fun logBounds(childView: View, msg: String) {
        Log.d(TAG, "$msg view top ${getDecoratedTop(childView)}")
        Log.d(TAG, "$msg view bottom ${getDecoratedBottom(childView)}")
        Log.d(TAG, "$msg view left ${getDecoratedLeft(childView)}")
        Log.d(TAG, "$msg view right ${getDecoratedRight(childView)}")
    }
}