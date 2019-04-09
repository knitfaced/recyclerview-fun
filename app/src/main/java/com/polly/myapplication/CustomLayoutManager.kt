package com.polly.myapplication

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.math.MathUtils
import androidx.recyclerview.widget.RecyclerView.*


class CustomLayoutManager(resources: Resources, private val screenWidth: Int) : LayoutManager() {

    private val TAG = "CustomLayoutManager"
    private var horizontalScrollOffset = 0

    private val viewWidth = resources.getDimensionPixelSize(R.dimen.item_width)
    private val recyclerViewHeight = (resources.getDimensionPixelSize(R.dimen.recyclerview_height)).toDouble()

    override fun generateDefaultLayoutParams(): LayoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

    override fun onLayoutChildren(recycler: Recycler, state: State) {
        fill(recycler, state)
    }

    private fun fill(recycler: Recycler, state: State) {
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

        val cosAlpha = (s - viewCentreX) / radius
        val alpha = Math.acos(MathUtils.clamp(cosAlpha, -1.0, 1.0))

        val yComponent = radius - (radius * Math.sin(alpha))
        return yComponent.toInt()
    }

    override fun canScrollHorizontally(): Boolean = true

    override fun scrollHorizontallyBy(dx: Int, recycler: Recycler, state: State): Int {
        horizontalScrollOffset += dx
        fill(recycler, state)
        return dx
    }
}