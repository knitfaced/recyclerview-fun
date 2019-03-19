package com.polly.myapplication

import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val thingsList : List<String> = listOf("banana", "apple", "pear", "strawberry", "cherry", "plum", "orange", "kiwi", "kumquat", "wolfberry", "dragonfruit")

        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        recyclerView.adapter = ThingAdapter(thingsList)

        val size = Point()
        windowManager.defaultDisplay.getSize(size)
        val screenWidth = size.x

        recyclerView.layoutParams.height = screenWidth / 2

        //This is how you would usually set up a LinearLayoutManager
//        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        //This is using our CustomLayoutManager.
        //Try changing between CustomLayoutManager1, CustomLayoutManager2 etc. to see the differences
        recyclerView.layoutManager = CustomLayoutManager(resources, screenWidth)
    }
}

class ThingAdapter(private val thingsList: List<String>) : Adapter<ThingHolder>() {
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

class ThingHolder(itemView: View) : ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.text)
}
