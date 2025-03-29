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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    //Creamos la referencia del obbjeto FirebaseAuth
    private lateinit var auth: FirebaseAuth

    //Referencia a componentes de nuestro layout
    private lateinit var btnRegister:Button
    private lateinit var textViewLogin:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Inicializamos el objeto FirebaseAuth
        auth = FirebaseAuth.getInstance()

        btnRegister=findViewById(R.id.btnRegister)
        btnRegister.setOnClickListener{
            val email= findViewById<EditText>(R.id.txtEmail).text.toString()
            val password=findViewById<EditText>(R.id.txtPass).text.toString()
            this.register(email, password)
        }

        textViewLogin=findViewById(R.id.textViewLogin)
        textViewLogin.setOnClickListener{
            this.goToLogin()
        }
    }

    private fun register(email:String, password: String){
        //validacion de los campos
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Los campos no pueden estar vacios",Toast.LENGTH_SHORT).show()
            return
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Ingrese una dirección de correo electrónico válido",Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    Toast.makeText(this, "Registro exitoso!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,MainActivity::class.java)
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

    private fun goToLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}