package com.unan.apianuncios

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NoticiasAdapter(private val noticias: List<Noticias>, private val onItemClick: (Noticias) -> Unit) :
        RecyclerView.Adapter<NoticiasAdapter.NoticasViewHolder>() {

        inner class NoticasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                private val txtTitulo = itemView.findViewById<TextView>(R.id.txtTitulo)
                private val srcimagen = itemView.findViewById<ImageView>(R.id.imagen)

                fun bind(noticias: Noticias) {
                        txtTitulo.text = noticias.title
                        Glide.with(itemView.context)
                                .load(noticias.image_url)
                                .into(srcimagen)

                        itemView.setOnClickListener {
                                onItemClick(noticias)
                        }
                }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticasViewHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_lista_anuncios, parent, false)
                return NoticasViewHolder(view)
        }

        override fun getItemCount(): Int {
                return noticias.size
        }

        override fun onBindViewHolder(holder: NoticasViewHolder, position: Int) {
                val noticia = noticias[position]
                holder.bind(noticia)
        }
}
