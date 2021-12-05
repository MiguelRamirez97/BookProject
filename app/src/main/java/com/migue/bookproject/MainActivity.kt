package com.migue.bookproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameBookEditText : EditText = findViewById(R.id.name_book_edit_test)
        val authorBookEditText : EditText = findViewById(R.id.name_author_edit_text)
        val pagesBookEditText : EditText = findViewById(R.id.pages_edit_text)
        val abstractBookEditText : EditText = findViewById(R.id.abstract_edit_text)
        val saveButton : Button = findViewById(R.id.save_button)
        val infoTextView : TextView = findViewById(R.id.info_text_view)

        saveButton.setOnClickListener{
            val nameBook : String = nameBookEditText.text.toString()
            val authorBook : String = authorBookEditText.text.toString()
            val pagesBook : String = pagesBookEditText.text.toString()
            val abstractBook : String = abstractBookEditText.text.toString()
            infoTextView.text = "El nombre del libro es: "+nameBook+"\nEl autor es: "+authorBook+"\nNumero de paginas es: "+pagesBook+"\nEl resumen es: "+abstractBook
        }
    }
}