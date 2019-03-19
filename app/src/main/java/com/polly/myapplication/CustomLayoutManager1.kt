package com.polly.myapplication

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutParams
import androidx.recyclerview.widget.RecyclerView.LayoutParams.WRAP_CONTENT

class CustomLayoutManager1 : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): LayoutParams
            = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

}

