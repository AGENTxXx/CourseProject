package com.smog.courseproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.smog.courseproject.data.Actor
import com.smog.courseproject.data.CastItem

class ActorListAdapter(private var actors:List<CastItem> = listOf()): RecyclerView.Adapter<ActorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor,parent,false)
        return ActorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.onBind(actors[position])
    }

    override fun getItemCount(): Int {
        return actors.size
    }

    fun bindActors(newActors: List<CastItem>) {
        actors = newActors
        notifyDataSetChanged()
    }
}

class ActorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val name:TextView = itemView.findViewById(R.id.activity_movie_details_tv_actor_name)
    val img:ImageView = itemView.findViewById(R.id.activity_movie_details_img_actor)

    fun onBind(actor:CastItem) {
        name.text = actor.name
        Glide.with(itemView.context)
            .load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/" + actor.profilePath)
            .placeholder(R.drawable.actor_dummy)
            .transform(CenterCrop(), RoundedCorners(16))
            .error(R.drawable.actor_dummy)
            .into(img)
    }
}