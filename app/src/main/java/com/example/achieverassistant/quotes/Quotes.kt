package com.example.achieverassistant.quotes

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.achieverassistant.R
import com.example.achieverassistant.databinding.ActivityQuotesBinding
import com.example.achieverassistant.quotes.adapters.RecyclerAdapterForQuotes
import com.example.achieverassistant.quotes.adapters.RecyclerAdapterForQuotes.OnQuoteListener
import com.example.achieverassistant.quotes.data.Quote
import com.example.achieverassistant.quotes.data.getQuoteDatabase

class Quotes : AppCompatActivity() {

    private lateinit var binding: ActivityQuotesBinding
    private lateinit var adapterForQuotes: RecyclerAdapterForQuotes
    private lateinit var quoteViewModel: QuoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quotes)

        val application = this.application as Application
        val database = getQuoteDatabase(application)
        val factory = QuoteViewModel.QuoteViewModelFactory(database, application)

        quoteViewModel = ViewModelProvider(this, factory)[QuoteViewModel::class.java]



        adapterForQuotes = RecyclerAdapterForQuotes(OnQuoteListener {  quote ->
            val data = Intent(this,ADDEDITQuote::class.java)
            data.putExtra(ADDEDITQuote.EXTRA_DATA_QUOTE,quote.quote)
            data.putExtra(ADDEDITQuote.EXTRA_DATA_IMAGE,quote.avatar)
            data.putExtra(ADDEDITQuote.EXTRA_DATA_MEMBERQUOTE,quote.quoteMember)
            data.putExtra(ADDEDITQuote.EXTRA_DATA_ID_QUOTE,quote.quoteID)
            editLauncher.launch(data)

             })


        binding.recyclerQuotes.adapter = adapterForQuotes
        binding.lifecycleOwner = this
        quoteViewModel.allQuotes.observe(this) { quotes ->
            adapterForQuotes.submitList(quotes)
        }

        binding.buttonAddquote.setOnClickListener {
            val intent = Intent(this, ADDEDITQuote::class.java)
            addLauncher.launch(intent)
        }



    }

    private val addLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){  result ->
        if(result.data != null){
            if(result.resultCode == Activity.RESULT_OK){
                val quoteMember = result.data!!.getStringExtra(ADDEDITQuote.EXTRA_DATA_MEMBERQUOTE)
                val quoteText = result.data!!.getStringExtra(ADDEDITQuote.EXTRA_DATA_QUOTE)
                val quoteImage = result.data!!.getIntExtra(ADDEDITQuote.EXTRA_DATA_IMAGE,0)
                val quoteData = Quote(quoteMember, quoteText,quoteImage)
                quoteViewModel.insertQuote(quoteData)
                Toast.makeText(this, "Don't Forget their Words", Toast.LENGTH_SHORT).show()

            }  else {
                Toast.makeText(this, "there's no Quote!!", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private val editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.data != null){
            if (result.resultCode == Activity.RESULT_OK){
                val id = result.data!!.getIntExtra(ADDEDITQuote.EXTRA_DATA_ID_QUOTE,-1)
                if (id == -1){
                    Toast.makeText(this,"Quote not edited!!",Toast.LENGTH_SHORT).show()
                }
                val quoteText = result.data!!.getStringExtra(ADDEDITQuote.EXTRA_DATA_QUOTE)
                val quoteMember = result.data!!.getStringExtra(ADDEDITQuote.EXTRA_DATA_MEMBERQUOTE)
                val quoteImage = result.data!!.getIntExtra(ADDEDITQuote.EXTRA_DATA_IMAGE,0)


                val quote = Quote(quoteMember,quoteText,quoteImage)
                quote.quoteID = id
                quoteViewModel.updateQuote(quote)

                Toast.makeText(this,"Quote edited",Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this,"Quote not edited!!",Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.quotes_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete_all_quotes -> quoteViewModel.deleteAllQuotes()
            R.id.filter_family -> quoteViewModel.filter(MemberStatus.FAMILY)
            R.id.filter_lover -> quoteViewModel.filter(MemberStatus.LOVER)
            R.id.filter_anonymous-> quoteViewModel.filter(MemberStatus.ANONYMOUS)
            R.id.filter_friends -> quoteViewModel.filter(MemberStatus.FRIENDS)
            R.id.show_all_quotes -> quoteViewModel.getAllQuotes()

        }
        return super.onOptionsItemSelected(item)
    }
}