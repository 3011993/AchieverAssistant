package com.example.achieverassistant.quotes

import android.app.Application
import androidx.lifecycle.*
import com.example.achieverassistant.quotes.data.Quote
import com.example.achieverassistant.quotes.data.QuoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class MemberStatus(val filter : String){
    FRIENDS("Friends"),FAMILY("Family"),ANONYMOUS("Anonymous"),LOVER("Lover")
}

class QuoteViewModel(private val database: QuoteDatabase, application: Application) : AndroidViewModel(application) {

    private val _allQuotes = MutableLiveData<List<Quote>>()
    val allQuotes: LiveData<List<Quote>>
    get() = _allQuotes

    init {
        getAllQuotes()
    }

    fun insertQuote(quote: Quote) {
        viewModelScope.launch (Dispatchers.IO){
            database.quoteDAO().insertQuote(quote)
        }
    }

    fun deleteQuote(quote: Quote) {
        viewModelScope.launch(Dispatchers.IO) {
            database.quoteDAO().deleteQuote(quote)
        }
    }

    fun updateQuote(quote: Quote) {
        viewModelScope.launch(Dispatchers.IO) {
            database.quoteDAO().updateQuote(quote)
        }
    }



     fun deleteAllQuotes() {
        viewModelScope.launch(Dispatchers.IO) {
            database.quoteDAO().deleteAllQuotes()
        }
    }
     fun getAllQuotes (){
        viewModelScope.launch(Dispatchers.IO) {
            database.quoteDAO().allQuotes().collect(){
                _allQuotes.postValue(it)
            }
        }
    }

    fun filter (memberStatus: MemberStatus){
        viewModelScope.launch(Dispatchers.IO){
            database.quoteDAO().filterAllQuotes(memberStatus.filter).collect(){
                _allQuotes.postValue(it)
            }
        }
    }




    class QuoteViewModelFactory(val database: QuoteDatabase, val app: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(QuoteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return QuoteViewModel(database,app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }
}
