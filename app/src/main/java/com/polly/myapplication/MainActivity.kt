package com.polly.myapplication

import android.graphics.Point
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val thingsList : List<String> = listOf("banana", "apple", "pear", "strawberry", "cherry", "plum", "orange", "kiwi", "kumquat", "wolfberry", "dragonfruit")

        val recyclerview: RecyclerView = findViewById(R.id.recyclerview) as RecyclerView
        recyclerview.adapter = ThingAdapter(thingsList)

        val display = getWindowManager().getDefaultDisplay()
        val size = Point()
        display.getSize(size)
        val screenWidth = size.x

        val viewWidth = resources.getDimensionPixelSize(R.dimen.item_width)
        recyclerview.layoutParams.height = screenWidth / 2

//        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerview.layoutManager = CustomLayoutManager(this, screenWidth)
    }
}

class ThingAdapter(private val thingsList: List<String>) : RecyclerView.Adapter<ThingHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThingHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ThingHolder(itemView)
    }

    override fun onBindViewHolder(holder: ThingHolder, position: Int) {
        holder.textView.text = thingsList[position]
    }

    override fun getItemCount(): Int {
        return thingsList.size
    }

}

class ThingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.text) as TextView

}
