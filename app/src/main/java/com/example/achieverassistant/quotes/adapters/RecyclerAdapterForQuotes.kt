package com.example.achieverassistant.quotes.adapters


import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.achieverassistant.databinding.CardviewQuotesBinding
import com.example.achieverassistant.quotes.adapters.RecyclerAdapterForQuotes.QuoteViewHolder.Companion.from
import com.example.achieverassistant.quotes.data.Quote

class RecyclerAdapterForQuotes(val clickListener : OnQuoteListener) : ListAdapter<Quote, RecyclerAdapterForQuotes.QuoteViewHolder>(
    diffCallBack
) {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): QuoteViewHolder {
        return from(viewGroup)
    }


    override fun onBindViewHolder(quoteViewHolder: QuoteViewHolder, position: Int) {
        val quote = getItem(position)
        quoteViewHolder.itemView.setOnClickListener {
            clickListener.onClick(quote)
        }
        quoteViewHolder.bind(clickListener,quote)


    }

     class QuoteViewHolder(private val binding: CardviewQuotesBinding) :
         RecyclerView.ViewHolder(binding.root) {

         fun bind(clickListener: OnQuoteListener, quote: Quote){
             binding.textviewQuote.text = quote.quote
             binding.textviewMember.text = quote.quoteMember
             binding.memberImageView.setImageResource(quote.avatar)

             binding.quote = quote
             binding.clickListener = clickListener
             binding.executePendingBindings()



         }

         companion object {
             fun from(viewGroup: ViewGroup) : QuoteViewHolder {
                 val layoutInflater = LayoutInflater.from(viewGroup.context)
                 val binding = CardviewQuotesBinding.inflate(layoutInflater, viewGroup, false)
                 return QuoteViewHolder(binding)

             }
         }
     }

    fun getItemAt(position: Int): Quote? {
        return getItem(position)
    }

    class OnQuoteListener(val clickListener: (quote : Quote) -> Unit) {
        fun onClick(quote: Quote) = clickListener(quote)
    }


    companion object {
        private val diffCallBack: DiffUtil.ItemCallback<Quote> =
            object : DiffUtil.ItemCallback<Quote>() {
                override fun areItemsTheSame(oldquote: Quote, newquote: Quote): Boolean {
                    return oldquote.quoteID == newquote.quoteID
                }

                override fun areContentsTheSame(oldquote: Quote, newquote: Quote): Boolean {
                    return oldquote.quote == newquote.quote && oldquote.quoteMember == newquote.quoteMember
                }
            }
    }
}