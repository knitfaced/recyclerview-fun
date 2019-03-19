package com.polly.myapplication

import androidx.recyclerview.widget.RecyclerView.LayoutParams
import androidx.recyclerview.widget.RecyclerView.LayoutParams.WRAP_CONTENT

class CustomLayoutManager1 : androidx.recyclerview.widget.RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    }

}

