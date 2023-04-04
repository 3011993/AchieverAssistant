package com.example.achieverassistant.quotes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.achieverassistant.R
import com.example.achieverassistant.databinding.ActivityAddeditquoteBinding
import com.example.achieverassistant.quotes.adapters.SelectedItemAdapter
import com.example.achieverassistant.quotes.data.SelectedItem


class ADDEDITQuote : AppCompatActivity() {


    private lateinit var binding: ActivityAddeditquoteBinding
    private lateinit var selectedItems: ArrayList<SelectedItem>

    private lateinit var memberSpinner : String
    private var memberImage : Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_addeditquote)

        initList()


        val adapter = SelectedItemAdapter(this,selectedItems)

        binding.spinner.adapter = adapter


        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val clickedItem: SelectedItem = parent.getItemAtPosition(position) as SelectedItem
                memberSpinner= clickedItem.member
                memberImage = clickedItem.avatar

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.buttonRecordQuote.setOnClickListener { recordQuote() }

        if (intent.hasExtra(EXTRA_DATA_ID_QUOTE)) {
            title = "Edit Quote"
            val selectedMember = intent.getStringExtra(EXTRA_DATA_MEMBERQUOTE).toString()
            val quoteText = intent.getStringExtra(EXTRA_DATA_QUOTE).toString()

            setSelectionSpinner(selectedMember)

            binding.edittextQuote.setText(quoteText)


        } else {
            title = "New Quote"
        }


    }

    private fun recordQuote() {
        val quote = binding.edittextQuote.text.toString()

        if (memberSpinner.isEmpty() && quote.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Please Select the Member or write the quote",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val saveQuote = Intent()
        saveQuote.putExtra(EXTRA_DATA_MEMBERQUOTE, memberSpinner)
        saveQuote.putExtra(EXTRA_DATA_IMAGE,memberImage)
        saveQuote.putExtra(EXTRA_DATA_QUOTE, quote)
        val id = intent.getIntExtra(EXTRA_DATA_ID_QUOTE, -1)
        if (id != -1) {
            saveQuote.putExtra(EXTRA_DATA_ID_QUOTE, id)
        }
        setResult(RESULT_OK, saveQuote)
        finish()
    }


    private fun initList(){
        selectedItems = ArrayList()
        selectedItems.add(SelectedItem("select one",0))
        selectedItems.add(SelectedItem("Family",R.drawable.family))
        selectedItems.add(SelectedItem("Friends",R.drawable.friends))
        selectedItems.add(SelectedItem("Lover",R.drawable.lover))
        selectedItems.add(SelectedItem("Anonymous",R.drawable.anon))

    }

    private fun setSelectionSpinner(value : String){
        when(value){
            getString(R.string.family_item) -> binding.spinner.setSelection(1)
            getString(R.string.friends_item) -> binding.spinner.setSelection(2)
            getString(R.string.lover_item) -> binding.spinner.setSelection(3)
            getString(R.string.anon_item) -> binding.spinner.setSelection(4)
            else -> binding.spinner.setSelection(0)

        }


    }

    companion object {
        const val EXTRA_DATA_ID_QUOTE = "com.mooth.achieverassistant.quotes.idquote"
        const val EXTRA_DATA_MEMBERQUOTE = "com.mooth.achieverassistant.quotes.memberquote"
        const val EXTRA_DATA_QUOTE = "com.mooth.achieverassistant.quotes.quote"
        const val EXTRA_DATA_IMAGE = "com.mooth.achieverassistant.quotes.image"
    }
}