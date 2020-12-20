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
import com.bumptech.glide.request.RequestOptions
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

    val requestOptions: RequestOptions = RequestOptions()
        .placeholder(R.drawable.actor_dummy)
        .transform(CenterCrop(), RoundedCorners(16))

    fun onBind(actor:Actor) {
        name.text = actor.name
        Glide.with(context)
            .load(actor.picture)
            .apply(requestOptions)
            .error(R.drawable.actor_dummy)
            .into(img)
    }
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context