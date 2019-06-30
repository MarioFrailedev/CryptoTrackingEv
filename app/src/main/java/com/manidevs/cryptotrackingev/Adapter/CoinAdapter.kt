package com.manidevs.cryptotrackingev.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.manidevs.cryptotrackingev.R
import com.manidevs.cryptotrackingev.model.CryptoCoin
import com.manidevs.cryptotrackingev.utilities.Constants
import com.manidevs.cryptotrackingev.utilities.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.crypto_layout.view.*
import java.util.*

class CoinAdapter : RecyclerView.Adapter<CoinAdapter.CoinViewHolder>(){
    private var cryptoCoins: List<CryptoCoin> = Collections.emptyList()

        override fun getItemCount(): Int = cryptoCoins.count()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder = CoinViewHolder(parent.inflate(R.layout.crypto_layout))

        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {

            val coin = cryptoCoins.get(position)

            holder.apply {
                coinName.text = coin.name
                coinSymbol.text = coin.symbol
                coinPrice.text = coin.price_usd
                oneHourChange.text = coin.percent_change_1h + "%"
                twentyFourHourChange.text = coin.percent_change_24h + "%"
                sevenDayChange.text = coin.percent_change_7d + "%"


                //Picasso for async image loading
                Picasso.with(itemView.context).load(Constants.imageUrl + coin.symbol.toLowerCase() + ".png").into(coinIcon)

                //Set color of percentage change textview to reflect if the percentage change was negative or positive
                oneHourChange.setTextColor(
                    Color.parseColor(when {
                        coin.percent_change_1h.contains("-") -> "#ff0000"
                        else -> "#32CD32"
                    }))

                twentyFourHourChange.setTextColor(
                    Color.parseColor(when {
                        coin.percent_change_24h.contains("-") -> "#ff0000"
                        else -> "#32CD32"
                    }))

                sevenDayChange.setTextColor(
                    Color.parseColor(when {
                        coin.percent_change_7d.contains("-") -> "#ff0000"
                        else -> "#32CD32"
                    }))
            }
    }

    class CoinViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        var coinName = view.coinName
        var coinSymbol = view.coinSymbol
        var coinPrice = view.priceUsdText
        val oneHourChange = view.percentChange1hText
        var twentyFourHourChange = view.percentChange24hText
        var sevenDayChange = view.percentChange7dayText
        var coinIcon = view.coinIcon

    }

    fun updateData(cryptoCoins: List<CryptoCoin>) {
        this.cryptoCoins = cryptoCoins
        notifyDataSetChanged()
    }

}

