package com.smog.courseproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.smog.courseproject.Utils.getDrawableFromName
import com.smog.courseproject.data.Actor

class ActorListAdapter(private var actors:List<Actor> = listOf()): RecyclerView.Adapter<ActorViewHolder>() {

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

    fun bindActors(newActors: List<Actor>) {
        actors = newActors
        notifyDataSetChanged()
    }
}

class ActorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val name:TextView = itemView.findViewById(R.id.activity_movie_details_tv_actor_name)
    val img:ImageView = itemView.findViewById(R.id.activity_movie_details_img_actor)

    fun onBind(actor:Actor) {
        name.text = actor.name
        val imgPreview = getDrawableFromName(context, actor.imgLink)
        img.setImageResource(imgPreview)
    }
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context