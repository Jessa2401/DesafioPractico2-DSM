package com.example.desafiopractico2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Asegúrate de tener este layout

        // Configuración inicial de la actividad
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_sing_out -> {
                FirebaseAuth.getInstance()
                    .signOut()
                    .also {
                        Toast.makeText(
                            this,
                            "Sesión Cerrada",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent=Intent(this,RegisterActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}