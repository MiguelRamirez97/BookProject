package com.migue.bookproject.ui.newbook

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.migue.bookproject.R
import com.migue.bookproject.databinding.FragmentNewBookBinding
import com.migue.bookproject.models.Book
import com.migue.bookproject.ui.list.ListFragmentDirections
import java.text.SimpleDateFormat
import java.util.*

class NewBookFragment : Fragment() {

    private lateinit var newBookBinding: FragmentNewBookBinding
    private lateinit var viewModel: NewBookViewModel
    private var cal = Calendar.getInstance()
    private var publicationDate = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        newBookBinding = FragmentNewBookBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(NewBookViewModel::class.java)
        return  newBookBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dateSetListener = DatePickerDialog.OnDateSetListener{ _, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val format = "dd-MM-yyyy"
            val simpleDateFormat = SimpleDateFormat(format, Locale.US)
            publicationDate = simpleDateFormat.format(cal.time).toString()
            newBookBinding.publicationDateButton.text = publicationDate
        }

        with(newBookBinding) {

            publicationDateButton.setOnClickListener{
                DatePickerDialog(
                    requireContext(),
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            saveButton.setOnClickListener{
                if(nameBookEditTest.text.isEmpty() || nameAuthorEditText.text.isEmpty() || pagesEditText.text.isNullOrEmpty()){
                    Toast.makeText(requireContext(),"El nombre del libro, el autor y el numero de paginas tiene que existir", Toast.LENGTH_SHORT).show()
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

                    val book = Book(name = nameBook,author = authorBook,pages = pagesBook,abstract = abstractBook,genre = genre,score = score,publicationDate = publicationDate)
                    findNavController().navigate(NewBookFragmentDirections.actionNewBookFragmentToDetailFragment(book))
                }
            }
        }

    }

}