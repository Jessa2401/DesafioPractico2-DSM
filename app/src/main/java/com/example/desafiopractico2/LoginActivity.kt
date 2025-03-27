package com.example.desafiopractico2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    //Se crea la referencia del objeto FirebaseAuth
    private lateinit var auth: FirebaseAuth

    //Referencia a componentes del layout
    private lateinit var btnLogin:Button
    private lateinit var textViewRegister: TextView

    //Listener de FirebaseAuth
    private lateinit var authStateListener:FirebaseAuth.AuthStateListener

    override fun onResume(){
        super.onResume()
        auth.addAuthStateListener(authStateListener)
    }

    override fun onPause(){
        super.onPause()
        auth.removeAuthStateListener(authStateListener)
    }

    private fun checkUser(){
        //Verificacion del usuario
        authStateListener=FirebaseAuth.AuthStateListener { auth ->
            if(auth.currentUser != null){
                //Cambiando la vista
                val intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Se inicializa el objeto FirebaseAuth
        auth=FirebaseAuth.getInstance()

        btnLogin=findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val email = findViewById<EditText>(R.id.txtEmail).text.toString()
            val password = findViewById<EditText>(R.id.txtPass).text.toString()
            this.login(email,password)
        }

        textViewRegister =findViewById(R.id.textViewRegister)
        textViewRegister.setOnClickListener {
            this.gotoRegister()
        }

        //validar si existe un usuario activo
        this.checkUser()
    }

    private fun login(email:String,password:String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener{exception ->
                Toast.makeText(
                    applicationContext,
                    exception.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun gotoRegister(){
        val intent=Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}