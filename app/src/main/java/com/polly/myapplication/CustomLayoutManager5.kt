package com.polly.myapplication

import android.content.res.Resources
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView.*
import java.lang.Math.PI

class CustomLayoutManager5(resources: Resources, private val screenWidth: Int) : LayoutManager() {

    private var horizontalScrollOffset: Int = 0
    private val viewWidth = resources.getDimensionPixelSize(R.dimen.item_width)

    override fun generateDefaultLayoutParams(): LayoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

    override fun canScrollHorizontally(): Boolean = true

    override fun scrollHorizontallyBy(dx: Int, recycler: Recycler, state: State): Int {
        horizontalScrollOffset += dx
        fill(recycler)
        return dx
    }

    override fun onLayoutChildren(recycler: Recycler, state: State) {
        fill(recycler)
    }

    private fun fill(recycler: Recycler) {
        detachAndScrapAttachedViews(recycler)

        for (i in 0 until itemCount) {
            val left = i * viewWidth - horizontalScrollOffset
            val right = left + viewWidth
            val top = getTopOffsetForView(left.toDouble() + (viewWidth.toDouble() / 2))
            val bottom = top + viewWidth

            val view = recycler.getViewForPosition(i)
            addView(view)

            measureChild(view, viewWidth, viewWidth)

            layoutDecorated(view, left, top, right, bottom)
        }
        val scrapListCopy = recycler.scrapList.toList()
        scrapListCopy.forEach {
            recycler.recycleView(it.itemView)
        }
    }

    private fun getTopOffsetForView(viewCentre: Double): Int {
        val radius = screenWidth / 2
        val alpha = (viewCentre / screenWidth) * PI
        val y = radius - (radius * Math.sin(alpha))
        return y.toInt() - (viewWidth / 2)
    }
}