package com.artnest.networkingsample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.artnest.networkingsample.data.response.CatResponse

class CatsAdapter : RecyclerView.Adapter<CatsAdapter.CatViewHolder>() {

    private val cats: MutableList<CatResponse> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_cat, parent, false)
        return CatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(cat = cats[position], position = position)
    }

    override fun getItemCount() = cats.size

    fun addCats(newCats: List<CatResponse>) {
        cats += newCats
        notifyDataSetChanged() // used here for simplicity, you must never use it
    }

    fun submitList(newCats: List<CatResponse>) {
        cats.clear()
        cats += newCats
        notifyDataSetChanged() // used here for simplicity, you must never use it
    }

    fun clearCats() {
        cats.clear()
        notifyDataSetChanged() // used here for simplicity, you must never use it
    }

    class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val label: TextView = itemView.findViewById(R.id.label_name)
        private val image: ImageView = itemView.findViewById(R.id.image_cat)

        fun bind(cat: CatResponse, position: Int) {
            label.text = "Cat $position"
            image.load(cat.url) {
                crossfade(true)
                placeholder(R.mipmap.ic_launcher)
                transformations(CircleCropTransformation())
            }
        }
    }
}
