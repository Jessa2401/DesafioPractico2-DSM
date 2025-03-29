package com.example.desafiopractico2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrarNotas : AppCompatActivity() {

    private lateinit var db:DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_notas)

        auth=FirebaseAuth.getInstance()
        db=FirebaseDatabase.getInstance().reference

        //Confifurando spinners
        val grados= arrayOf("Septimo","Octavo","Noveno","1er año Bachillerato","2do año Bachillerato")
        val materias= arrayOf("Matemáticas","Química", "Biologia", "Física", "Lenguaje", "Sociales", "Inglés", "Orientación para la vida","Moral y Cívica")

        val gradoAdapter=ArrayAdapter(this,android.R.layout.simple_spinner_item, grados)
        val materiaAdapter=ArrayAdapter(this, android.R.layout.simple_spinner_item, materias)

        findViewById<Spinner>(R.id.spinnerGrado).adapter=gradoAdapter
        findViewById<Spinner>(R.id.spinnerMateria).adapter=materiaAdapter

        findViewById<Button>(R.id.btnGuardarEstudiante).setOnClickListener {
            guardarEstudiante()
        }
    }

    private fun guardarEstudiante(){
        val nombre=findViewById<EditText>(R.id.etNombre).text.toString()
        val apellido=findViewById<EditText>(R.id.etApellido).text.toString()
        val grado=findViewById<Spinner>(R.id.spinnerGrado).selectedItem.toString()
        val materia=findViewById<Spinner>(R.id.spinnerMateria).selectedItem.toString()
        val NotaFinal=findViewById<EditText>(R.id.etNotaFinal).text.toString()

        //Validaciones
        if(nombre.isEmpty() || apellido.isEmpty() || NotaFinal.isEmpty()){
            Toast.makeText(this,"Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val finalGrade = try {
            NotaFinal.toDouble()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "La nota debe ser un número válido", Toast.LENGTH_SHORT).show()
            return
        }

        if(finalGrade < 0 || finalGrade > 10){
            Toast.makeText(this,"La nota debe estar entre 0 y 10", Toast.LENGTH_SHORT ).show()
            return
        }

        //Obtener ID del usuario actual
        val userID=auth.currentUser?.uid?:run {
            Toast.makeText(this, "No autenticado",Toast.LENGTH_SHORT).show()
            return
        }

        //Creando un objeto estudiante
        val estudiante= hashMapOf(
            "nombre" to nombre,
            "apellido" to apellido,
            "grado" to grado,
            "materia" to materia,
            "notafinal" to NotaFinal
        )
        //Guardar en Firebase
        db.child("notas_estudiantes").child(userID).push().setValue(estudiante)
            .addOnCompleteListener {
                Toast.makeText(this, "Nota registrada exitosamente",Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Error al guardar: ${it.message}",Toast.LENGTH_SHORT).show()
            }
    }
}