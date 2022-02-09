package com.migue.bookproject

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.migue.bookproject.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding : ActivityMainBinding
    private var cal = Calendar.getInstance()
    private var publicationDate = ""

    var emailReceived: String? = ""
    var passwordReceived: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val credentials = intent.extras
        emailReceived = credentials?.getString("email")
        passwordReceived = credentials?.getString("password")

        //val nameBookEditText : EditText = findViewById(R.id.name_book_edit_test)
        val dateSetListener = DatePickerDialog.OnDateSetListener{ _, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val format = "dd-MM-yyyy"
            val simpleDateFormat = SimpleDateFormat(format, Locale.US)
            publicationDate = simpleDateFormat.format(cal.time).toString()
            mainBinding.publicationDateButton.text = publicationDate
        }

        with(mainBinding) {

            publicationDateButton.setOnClickListener{
                DatePickerDialog(
                    this@MainActivity,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            saveButton.setOnClickListener{
                if(nameBookEditTest.text.isEmpty() || nameAuthorEditText.text.isEmpty() || pagesEditText.text.isNullOrEmpty()){
                    Toast.makeText(applicationContext,"El nombre del libro, el autor y el numero de paginas tiene que existir", Toast.LENGTH_SHORT).show()
                }else {
                    val nameBook : String = nameBookEditTest.text.toString()
                    val authorBook : String = nameAuthorEditText.text.toString()
                    val pagesBook = pagesEditText.text.toString().toInt()
                    val abstractBook : String = abstractEditText.text.toString()

                    var genre = ""
                    if(suspenseCheckBox.isChecked) genre = "suspenso"
                    if(terrorCheckBox.isChecked) genre = genre + " terror"
                    if(infantileCheckBox.isChecked) genre = genre + " infantil"
                    if(fictionCheckBox.isChecked) genre = genre + " ficcion"

                    var score = when {
                        oneRadioButton.isChecked -> 1
                        twoRadioButton.isChecked -> 2
                        threeRadioButton.isChecked -> 3
                        fourRadioButton.isChecked -> 4
                        else -> 5
                    }

                    infoTextView.text = getString(R.string.info, nameBook, authorBook, pagesBook, abstractBook, genre, score, publicationDate)
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_overflow, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_sing_out -> goToLoginActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToLoginActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("email", emailReceived)
        intent.putExtra("password", passwordReceived)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}