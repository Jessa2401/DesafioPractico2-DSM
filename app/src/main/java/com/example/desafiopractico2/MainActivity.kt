package com.example.desafiopractico2

import android.app.DownloadManager.Query
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {



    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        // Configuración inicial
        setSupportActionBar(findViewById(R.id.toolbar))

        // Botón para agregar notas
        findViewById<Button>(R.id.btnAddGrades).setOnClickListener {
            startActivity(Intent(this, RegistrarNotas::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_sing_out -> {
                auth.signOut().also {
                    Toast.makeText(this, "Sesión Cerrada", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, RegisterActivity::class.java))
                    finish()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}