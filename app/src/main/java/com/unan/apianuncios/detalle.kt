package com.unan.apianuncios

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.unan.apianuncios.databinding.ActivityDetalleBinding

class detalle : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recibir los datos de la noticia desde MainActivity
        val titulo = intent.getStringExtra("title")
        val creador = intent.getStringArrayListExtra("creator")
        val descripcion = intent.getStringExtra("description")
        val fechapub = intent.getStringExtra("pubDate")
        val imagensrc = intent.getStringExtra("image_url")
        val fuente = intent.getStringExtra("source_id")
        val categoria = intent.getStringArrayListExtra("category")

        // Establecer los datos en las vistas correspondientes
        binding.txtfuente.text = fuente
        binding.txtTitulo.text = titulo
        binding.txtautor.text = creador?.joinToString(", ") ?: "Autor desconocido"
        binding.txtDescripcion.text = descripcion
        binding.txtFecha.text = fechapub
        binding.txtCategoria.text = categoria?.joinToString(", ") ?: "Categoría desconocida"
        Glide.with(this).load(imagensrc).into(binding.imagen)
    }
}