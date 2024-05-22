package com.unan.apianuncios

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.unan.apianuncios.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Realizar la solicitud GET al API web con Fuel
        try {
            Fuel.get("https://newsdata.io/api/1/news?apikey=pub_447144b0dfedfbaa84849ce481a4cecd5fb9f&country=cr,ni&language=es")
                .response { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val error = result.getException()
                            runOnUiThread {
                                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                        is Result.Success -> {
                            val cuerpoJson = String(response.data)
                            val convertidor = Gson()
                            val apiResponse = convertidor.fromJson(cuerpoJson, ApiResponse::class.java)
                            runOnUiThread {
                                // Actualiza el TextView con el número total de elementos
                                binding.txtTotal.text = "Total Elementos: ${apiResponse.totalResults}"

                                // Configura el RecyclerView
                                binding.recycler.layoutManager = LinearLayoutManager(this)
                                binding.recycler.adapter = NoticiasAdapter(apiResponse.results) { noticia ->
                                     //Manejar el clic en el elemento
                                    val intent = Intent(this@MainActivity, detalle::class.java).apply {
                                        putExtra("title", noticia.title)
                                        putStringArrayListExtra("creator", ArrayList(noticia.creator))
                                        putExtra("description", noticia.description)
                                        putExtra("pubDate", noticia.pubDate)
                                        putExtra("image_url", noticia.image_url)
                                        putExtra("source_id", noticia.source_id)
                                        putStringArrayListExtra("category", ArrayList(noticia.category))
                                    }
                                    startActivity(intent)
                                }
                            }
                        }
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(this, "Exception: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
