package com.polly.myapplication

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.LayoutParams
import android.support.v7.widget.RecyclerView.LayoutParams.WRAP_CONTENT

class CustomLayoutManager1 : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    }

}

